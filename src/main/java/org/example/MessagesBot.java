package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessagesBot extends TelegramLongPollingBot {
    private List<Long> chatIds ;
    private List<Integer>userChoice;

    public MessagesBot(){
        this.chatIds = new ArrayList<>();
    }

    @Override
    public String getBotUsername() {
        return "API overall service bot";
    }

    @Override
    public String getBotToken() {
        return "6264561273:AAHrWL7V3qIHRIVafPTrQU0SKlhJC_XjsI8";
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        long chatId = update.getMessage().getChatId();
        System.out.println(update.getMessage().getText());
        message.setChatId(chatId);
        if (this.chatIds.contains(chatId)){
            this.chatIds.add(chatId);
        } else {
            InlineKeyboardButton jokeButton = new InlineKeyboardButton("joke");
            jokeButton.setCallbackData("joke");
            InlineKeyboardButton weatherButton = new InlineKeyboardButton("weather");
            weatherButton.setCallbackData("weather");
            InlineKeyboardButton numbersInfoButton = new InlineKeyboardButton("numbers information");
            numbersInfoButton.setCallbackData("numbers");
            InlineKeyboardButton quotesButton = new InlineKeyboardButton("quotes");
            quotesButton.setCallbackData("quotes");
            InlineKeyboardButton currencyExchangeButton = new InlineKeyboardButton("currency exchange");
            currencyExchangeButton.setCallbackData("currency exchange");

            List<InlineKeyboardButton> secondRow =Arrays.asList(quotesButton,currencyExchangeButton);
            List<InlineKeyboardButton> topRow  = Arrays.asList(jokeButton,weatherButton,numbersInfoButton);
            List<List<InlineKeyboardButton>> keyboard = Arrays.asList(topRow,secondRow);
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);
            message.setReplyMarkup(inlineKeyboardMarkup);
            message.setText("Choose a service");
        }
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}