package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class WorldDataBot extends TelegramLongPollingBot {
    private BotAdminInterface botAdminInterface;
    private ArrayList<String> availableActivities;
    private HashMap<Long, Boolean> chatIds;
    public WorldDataBot () {
        this.botAdminInterface = new BotAdminInterface(this);
        this.chatIds = new HashMap<>();
        this.availableActivities = new ArrayList<>();
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
        if (this.chatIds.containsKey(newChatId) && this.chatIds.get(newChatId)) {
            this.chatIds.put(newChatId, false);
            applyRequests(update.getCallbackQuery().getData(), newChatId);
        } else {
            String username = update.getMessage().getChat().getUserName();
            sendInitialMessages(newChatId, username);
        }
        // message date
        long unixTimestamp = update.getMessage().getDate();
        String currentMessageDate = currentMessageDate(unixTimestamp);
    }

    private void applyRequests (String request, long chatId) {
        Activity activity = Activity.getActivityFromRequest(request);

        switch (activity) {
            case WEATHER -> {
                System.out.println("0");
            }
            case NEWS -> {
                System.out.println("1");
            }
            case NASA -> {
                System.out.println("2");
            }
            case COVID_19_DATA -> {
                System.out.println("3");
            }
            case COUNTRIES_INFORMATION -> {
                System.out.println("4");
            }
        }
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
        if (isNewChatIdAdded) {
            User newUser = new User(username);
            botAdminInterface.addNewUser(newUser);
        }
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

        try {
            if (isNewChatIdAdded) {
                execute(welcomingMessage);
                execute(listMessage);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        this.chatIds.put(chatId, true);
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
            this.chatIds.put(chatId, false);
            result = true;
        }
        return result;
    }
}
