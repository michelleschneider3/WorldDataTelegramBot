package org.example;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
public class Main {
    public static void main(String[] args) {
        WorldDataBot worldDataBot = new WorldDataBot();
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(worldDataBot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}