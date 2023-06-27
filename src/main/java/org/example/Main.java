package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        new Window();
    }

    public static MessagesBot activateTelegramBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            MessagesBot bot = new MessagesBot();
            botsApi.registerBot(bot);
            return bot;
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
