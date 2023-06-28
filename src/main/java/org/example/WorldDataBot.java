package org.example;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorldDataBot extends TelegramLongPollingBot {
    private BotAdminInterface botAdminInterface;
    private ArrayList<String> availableActivities;
    private HashMap<Long, String> chatIds;
    private ExecutorService executorService;
    public WorldDataBot () {
        this.botAdminInterface = new BotAdminInterface(this);
        this.chatIds = new HashMap<>();
        this.availableActivities = new ArrayList<>();
        executorService = Executors.newFixedThreadPool(10);
    }
    @Override
    public String getBotUsername() {
        return Constants.BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return Constants.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        long newChatId = getChatId(update);
        if (this.chatIds.containsKey(newChatId) && this.chatIds.get(newChatId).equals("")) {
            long unixTimestamp = update.getCallbackQuery().getMessage().getDate();
            String currentMessageDate = currentMessageDate(unixTimestamp);
            String request = update.getCallbackQuery().getData();
            this.chatIds.put(newChatId, request);
            fistPartOfRequests(request, newChatId, currentMessageDate, update);
        } else if (this.chatIds.containsKey(newChatId) && !this.chatIds.get(newChatId).equals("")) {
            secondPartOfRequests(this.chatIds.get(newChatId), newChatId, update.getMessage().getText());
        } else {
            String username = update.getMessage().getChat().getUserName();
            sendInitialMessages(newChatId, username);
        }
    }

    private void fistPartOfRequests(String request, long chatId, String currentMessageDate, Update update) {
        Activity activity = Activity.getActivityFromRequest(request);
        this.botAdminInterface.findUserAndUpdateTheRequests(activity.getActivityName(), chatId);
        ArrayList<String> newActivity = makeNewActivityForHistory(this.botAdminInterface.getUserNameByChatId(chatId), activity.getActivityName(), currentMessageDate);
        this.botAdminInterface.addActivityToHistoryList(newActivity);
        switch (activity) {
            case PUBLIC_HOLIDAYS -> {
                SendMessage weatherMessage = createMessage("Write the ISO 3166 code of the country (for example: russia=RU): ", chatId);
                send(weatherMessage);
            }
            case QUOTES, NASA -> {
                secondPartOfRequests(this.chatIds.get(chatId), chatId, null);
            }
            case MAKEUP -> {
                SendMessage makeupMessage = createMessage("write the name of the brand and type product: \n\nfor example: dior,foundation" , chatId);
                send(makeupMessage);
            }
            case COUNTRIES_INFORMATION -> {
                SendMessage countriesMessage = createMessage("write the name of the country: ", chatId);
                send(countriesMessage);
            }
        }
    }

    private SendMessage createMessage (String text, long chatId) {
        SendMessage newMessage = new SendMessage();
        newMessage.setChatId(chatId);
        newMessage.setText(text);
        return newMessage;
    }


    private void secondPartOfRequests(String request, long chatId, String text) {
        Activity activity = Activity.getActivityFromRequest(request);

        switch (Objects.requireNonNull(activity)) {
            case PUBLIC_HOLIDAYS -> {
                this.executorService.submit(() -> handlePublicHolidaysInfoRequest(chatId, text));
            }
            case QUOTES -> {
                this.executorService.submit(() -> handleRandomQuoteRequest(chatId));
            }
            case NASA -> {
                this.executorService.submit(() -> handleNasaPictureOfTheDayRequest(chatId));
            }
            case MAKEUP -> {
                this.executorService.submit(() -> handleMakeupInfoRequest(chatId, text));
            }
            case COUNTRIES_INFORMATION -> {
                this.executorService.submit(() -> handleCountriesInfoRequest(chatId, text));
            }
        }
    }

    private void  handleRandomQuoteRequest (long chatId) {
        GetRequest getRequest = Unirest.get("https://api.quotable.io/quotes/random");
        try {
            HttpResponse<String> response = getRequest.asString();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                List<QuoteModel> quoteModels = objectMapper.readValue(response.getBody(), new TypeReference<>(){});
                quoteModels.forEach(quoteModel -> {
                    String textMessage = "content: " + quoteModel.getContent() +
                            "\n\nauthor: " + quoteModel.getAuthor();
                    SendMessage quoteMessage = createMessage(textMessage, chatId);
                    send(quoteMessage);
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        this.chatIds.remove(chatId);
    }

    private void handleMakeupInfoRequest(long chatId, String text) {
        String[] brandAndTypeProduct = text.split(",");
        GetRequest getRequest = Unirest.get("http://makeup-api.herokuapp.com/api/v1/products.json?brand=" + brandAndTypeProduct[0].trim() + "&product_type=" + brandAndTypeProduct[1].trim());
        try {
            HttpResponse<String> response = getRequest.asString();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                List<MakeupProductModel> makeupProductModelList = objectMapper.readValue(response.getBody(), new TypeReference<List<MakeupProductModel>>() {});
                makeupProductModelList.forEach(makeupProductModel -> {
                    String textMessage = "brand: " + makeupProductModel.getBrand() +
                            "\nprice: " + makeupProductModel.getPrice() +
                            "\nName: " + makeupProductModel.getName() +
                            "\n\ncategory: " + makeupProductModel.getCategory() +
                            "\n\nproduct_type: " + makeupProductModel.getProduct_type() +
                            "\n\nproduct_link: " + makeupProductModel.getProduct_link();
                    SendMessage makeupInfoMessage = createMessage(textMessage, chatId);
                    send(makeupInfoMessage);
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        this.chatIds.remove(chatId);
    }

    private void handleNasaPictureOfTheDayRequest (long chatId) {
        GetRequest getRequest = Unirest.get("https://api.nasa.gov/planetary/apod?api_key=mglHbNmXB8rG1E7GcXUqKFlorMXcL3qgirT7DLGZ");
        try {
            HttpResponse<String> response = getRequest.asString();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                NasaPictureOfTheDay nasaPictureOfTheDay = objectMapper.readValue(response.getBody(), new TypeReference<>(){});
                String textMessage = "date: " + nasaPictureOfTheDay.getDate() +
                        "\n\nexplanation: " + nasaPictureOfTheDay.getExplanation() +
                        "\n\nhdurl: " + nasaPictureOfTheDay.getHdurl() +
                        "\n\ntitle: " + nasaPictureOfTheDay.getTitle();
                SendMessage nasaPictureMessage = createMessage(textMessage, chatId);
                send(nasaPictureMessage);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        this.chatIds.remove(chatId);
    }
    private void handlePublicHolidaysInfoRequest(long chatId, String ISOCode) {
        GetRequest getRequest = Unirest.get("https://date.nager.at/api/v2/publicholidays/2023/" + ISOCode);
        try {
            HttpResponse<String> response = getRequest.asString();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                List<PublicHolidaysByCountry> publicHolidaysList = objectMapper.readValue(response.getBody(), new TypeReference<List<PublicHolidaysByCountry>>() {});
                publicHolidaysList.forEach(publicHoliday -> {
                    String textMessage = "Date: " + publicHoliday.getDate() +
                            "\nLocal Name: " + publicHoliday.getLocalName() +
                            "\nName: " + publicHoliday.getName() +
                            "\nCountry Code: " + publicHoliday.getCountryCode();
                    SendMessage holidaysInfoMessage = createMessage(textMessage, chatId);
                    send(holidaysInfoMessage);
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        this.chatIds.remove(chatId);
    }
    private void handleCountriesInfoRequest(long chatId, String country) {
        GetRequest getRequest = Unirest.get("https://restcountries.com/v2/name/" + country);
        try {
            HttpResponse<String> response = getRequest.asString();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                List<CountryModel> countryModel = objectMapper.readValue(response.getBody(), new TypeReference<>(){});
                countryModel.forEach(countryModel1 -> {
                    String textMessage = "name: " + countryModel1.getName() +
                            "\n\nalpha2Code: " + countryModel1.getAlpha2Code() +
                            "\n\nalpha3Code: " + countryModel1.getAlpha3Code() +
                            "\n\ncallingCodes: " + Arrays.toString(countryModel1.getCallingCodes()) +
                            "\n\nsubregion: " + countryModel1.getSubregion() +
                            "\n\nregion: " + countryModel1.getRegion() +
                            "\n\npopulation : " + countryModel1.getPopulation() +
                            "\n\ndemonym :" + countryModel1.getDemonym() +
                            "\n\narea: " + countryModel1.getArea() +
                            "\n\ntimezones: " + Arrays.toString(countryModel1.getTimezones()) +
                            "\n\nnativeName: " + countryModel1.getNativeName() +
                            "\n\nnumericCode: " + countryModel1.getNumericCode() +
                            "\n\nflag: " + countryModel1.getFlag();
                    SendMessage countryInfoMessage = createMessage(textMessage, chatId);
                    send(countryInfoMessage);
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        this.chatIds.remove(chatId);
    }

    private ArrayList<String> makeNewActivityForHistory (String userName, String activityName, String currentMessageDate) {
        ArrayList<String> newActivity = new ArrayList<>();
        newActivity.add(userName);
        newActivity.add(activityName);
        newActivity.add(currentMessageDate);
        return newActivity;
    }

    private long getChatId (Update update) {
        long chatId = 0;
        if (update.getMessage() != null) {
            chatId = update.getMessage().getChatId();
        } else {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }
        return chatId;
    }

    private void sendInitialMessages (long chatId, String username) {
        boolean isNewChatIdAdded = addChatId(chatId);
        botAdminInterface.addNewUser(new User(username, chatId));

        SendMessage welcomingMessage = new SendMessage();
        welcomingMessage.setChatId(chatId);
        welcomingMessage.setText("Hello @" + username + ", Welcome to world data bot!");

        SendMessage listMessage = new SendMessage();
        listMessage.setChatId(chatId);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (String availableActivity : this.availableActivities) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton newButton = new InlineKeyboardButton(availableActivity);
            newButton.setCallbackData(availableActivity);
            row.add(newButton);
            keyboard.add(row);
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        listMessage.setReplyMarkup(inlineKeyboardMarkup);
        listMessage.setText("Choose the API from the list below:");

        if (isNewChatIdAdded) {
            send(welcomingMessage);
            send(listMessage);
        }
    }

    private String currentMessageDate (long unixTimestamp) {
        Date messageDate = new Date(unixTimestamp * 1000L); // Convert from Unix timestamp to Java Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
        String formattedDate = dateFormat.format(messageDate);
        return formattedDate;
    }

    public void setAvailableActivities(ArrayList<String> availableActivities) {
        this.availableActivities = new ArrayList<>(availableActivities);
    }

    private boolean addChatId (Long chatId) {
        boolean result = false;
        if (!(this.chatIds.containsKey(chatId))) {
            this.chatIds.put(chatId, "");
            result = true;
        }
        return result;
    }

    private void send (SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
