package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.*;

public class MessagesBot extends TelegramLongPollingBot {
    private List<Long> chatIds;
    public static List<InlineKeyboardButton> buttons = new ArrayList<>();
    public static List<InlineKeyboardButton> activeButtons = new ArrayList<>();
    private Map<UserChoice, InlineKeyboardButton> buttonMap;
    private static List<InlineKeyboardButton> activeButtons1 = new ArrayList<>();

    public MessagesBot() {
        //  Window window = new Window();
        this.chatIds = new ArrayList<>();
//
//        createButton("joke");
//        createButton("cat_facts");
//        createButton("numbers information");
//        createButton("numbers");
//        createButton("quotes");
        InlineKeyboardButton jokeButton = new InlineKeyboardButton("joke");
        jokeButton.setCallbackData("joke");
        buttons.add(jokeButton);

        InlineKeyboardButton catsFactsButton = new InlineKeyboardButton("cats facts");
        catsFactsButton.setCallbackData("cats facts");
        buttons.add(catsFactsButton);

        InlineKeyboardButton numbersInfoButton = new InlineKeyboardButton("numbers");
        numbersInfoButton.setCallbackData("numbers");
        buttons.add(numbersInfoButton);

        InlineKeyboardButton quotesButton = new InlineKeyboardButton("quotes");
        quotesButton.setCallbackData("quotes");
        buttons.add(quotesButton);

        InlineKeyboardButton currencyExchangeButton = new InlineKeyboardButton("currency exchange");
        currencyExchangeButton.setCallbackData("currency exchange");
        buttons.add(currencyExchangeButton);

        this.buttonMap = new HashMap<>();
        this.buttonMap.put(UserChoice.JOKE, jokeButton);
        this.buttonMap.put(UserChoice.CATS_FACTS, catsFactsButton);
        this.buttonMap.put(UserChoice.NUMBER, numbersInfoButton);
        this.buttonMap.put(UserChoice.QUOTES, quotesButton);
        this.buttonMap.put(UserChoice.EXCHANGE_RATE, currencyExchangeButton);
    }

    private void createButton(String name) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(name);
        inlineKeyboardButton.setCallbackData(name);
        buttons.add(inlineKeyboardButton);
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
//        System.out.println(update.getMessage().getText());
        SendMessage message = new SendMessage();
//        if (!this.chatIds.contains(update.getMessage().getChatId())) {
//            this.chatIds.add(chatId);
//            message.setText("hi hi hi");
//            send(message);
//        } else
        if (update.hasMessage()) {
            long chatId = update.getMessage().getChatId();
            message.setChatId(chatId);
            List<List<InlineKeyboardButton>> keyBoard = Arrays.asList(activeButtons1);
            message.setText("Choose a service");
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyBoard);
            message.setReplyMarkup(inlineKeyboardMarkup);
            try {
//                    System.out.println(message.getText());
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }else if  (update.hasCallbackQuery()) {

          //  message = new SendMessage();
            message.setChatId(update.getCallbackQuery().getFrom().getId());

            switch (update.getCallbackQuery().getData()) {
                case "joke" -> message.setText(Joke.getJokee());
                case "cats facts" -> message.setText(Cat.getCat());
                case "numbers" -> message.setText(NumberInfoAPI.getNumber());
                case "quotes" -> message.setText(QuotesAPI.getQuotes());
                case "currency exchange" -> message.setText("wip");
            }
            try {
                execute(message);
            } catch (TelegramApiException e) {
//                throw new RuntimeException(e);
            }
        }
    }

//   // }

    private void send(SendMessage sendMessage) {
        try {
            execute((sendMessage));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void addButton(UserChoice choice) {
        this.activeButtons1.add(this.buttonMap.get(choice));
    }

    public void removeButton(UserChoice choice) {
        this.activeButtons1.remove(this.buttonMap.get(choice));
    }
}