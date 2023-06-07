package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WorldDataBot extends TelegramLongPollingBot {

    private BotAdminInterface botAdminInterface;
    ArrayList<String> availableActivities;
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
        String username = update.getMessage().getChat().getUserName();

        long unixTimestamp = update.getMessage().getDate();
        Date messageDate = new Date(unixTimestamp * 1000L); // Convert from Unix timestamp to Java Date

        // Format the date as desired using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
        String formattedDate = dateFormat.format(messageDate);

        // Print the formatted date
        System.out.println("Message Date: " + formattedDate);



        SendMessage message = new SendMessage();;
        message.setChatId(update.getMessage().getChatId());
        message.setText("Hello, @" + username + "!");

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

//        SendMessage message = new SendMessage();
//        message.setChatId(update.getMessage().getChatId());
//        message.setText("hi, this is message from the bot");
//        try {
//            execute(message);
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void setAvailableActivities(ArrayList<String> availableActivities) {
        this.availableActivities = availableActivities;
    }
}
