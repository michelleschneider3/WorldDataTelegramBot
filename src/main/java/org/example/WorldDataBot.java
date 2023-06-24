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

import java.sql.SQLOutput;
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
        // initial messages
        long newChatId = getChatId(update);

        if (this.chatIds.containsKey(newChatId) && this.chatIds.get(newChatId).equals("")) {
            long unixTimestamp = update.getCallbackQuery().getMessage().getDate();
            String currentMessageDate = currentMessageDate(unixTimestamp);
            String request = update.getCallbackQuery().getData();
            this.chatIds.put(newChatId, request);
            fistPartOfRequests(request, newChatId, currentMessageDate, update);
        } else if (this.chatIds.containsKey(newChatId) && !this.chatIds.get(newChatId).equals("")) {
            secondPartOfRequests(this.chatIds.get(newChatId), newChatId, update, update.getMessage().getText());
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
            case WEATHER -> {
                System.out.println("first part 0");
            }
            case NEWS -> {
                System.out.println("first part 1");
            }
            case NASA -> {
                System.out.println("first part 2");
            }
            case COVID_19_DATA -> {
                System.out.println("first part 3");
            }
            case COUNTRIES_INFORMATION -> {
                SendMessage countriesMessage = new SendMessage();
                countriesMessage.setChatId(chatId);
                countriesMessage.setText("Write the country name: ");
                send(countriesMessage);
            }
        }
    }


    private void secondPartOfRequests(String request, long chatId, Update update, String text) {
        Activity activity = Activity.getActivityFromRequest(request);

        switch (Objects.requireNonNull(activity)) {
            case WEATHER -> {
                System.out.println("second part 0");
            }
            case NEWS -> {
                System.out.println("second part 1");
            }
            case NASA -> {
                System.out.println("second part 2");
            }
            case COVID_19_DATA -> {
                System.out.println("second part 3");
            }
            case COUNTRIES_INFORMATION -> {
                this.executorService.submit(() -> handleCountriesInfoRequest(chatId, update, text));
            }
        }
    }

    private void handleCountriesInfoRequest(long chatId, Update update, String country) {
        GetRequest getRequest = Unirest.get("https://restcountries.com/v2/name/" + country);
        try {
            HttpResponse<String> response = getRequest.asString();
            String json = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                List<CountryModel> countryModel = objectMapper.readValue(json, new TypeReference<>(){});
                for (CountryModel countryModel1 : countryModel) {
                    SendMessage countryInfoMessage = new SendMessage();
                    countryInfoMessage.setChatId(chatId);
                    countryInfoMessage.setText("name: " + countryModel1.getName() +
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
                            "\n\nflag: " + countryModel1.getFlag());
                    send(countryInfoMessage);
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        this.chatIds.remove(chatId);
        // לא לשכוח שבכל כל סוף בקשה למחוק את הצאט ID של המשתמש הזה מהליסט של צאט IDS
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
