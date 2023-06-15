package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class MessagesBot extends TelegramLongPollingBot {
    public static List<Long> chatIds;
    public static List<InlineKeyboardButton> menuButtons = new ArrayList<>();
    public List<InlineKeyboardButton> universitiesButtons = new ArrayList<>();
    private Map<UserChoice, InlineKeyboardButton> buttonMap;
    private static List<InlineKeyboardButton> activeApiButtons = new ArrayList<>();
    private HashSet<User> users;

    private final List<String> universitiesCountries = List.of("israel", "india", "usa", "spain", "japan","china");

    public MessagesBot() {
        chatIds = new ArrayList<>();
        users = new HashSet<>();
        InlineKeyboardButton jokeButton = new InlineKeyboardButton("joke");
        jokeButton.setCallbackData("joke");
        menuButtons.add(jokeButton);

        InlineKeyboardButton catsFactsButton = new InlineKeyboardButton("cats facts");
        catsFactsButton.setCallbackData("cats facts");
        menuButtons.add(catsFactsButton);

        InlineKeyboardButton numbersInfoButton = new InlineKeyboardButton("numbers");
        numbersInfoButton.setCallbackData("numbers");
        menuButtons.add(numbersInfoButton);

        InlineKeyboardButton quotesButton = new InlineKeyboardButton("quotes");
        quotesButton.setCallbackData("quotes");
        menuButtons.add(quotesButton);

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

    private void createUniversityButton(String country) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(country);
        inlineKeyboardButton.setCallbackData(country + "-universities");
        universitiesButtons.add(inlineKeyboardButton);
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
        if (update.hasMessage()) {
            users.add(update.getMessage().getFrom());
            message.setChatId(update.getMessage().getChatId());
            if (update.getMessage().getText().equals("/start") || update.getMessage().getText().equals("hi")) {
                List<List<InlineKeyboardButton>> keyBoard = Arrays.asList(activeApiButtons);
                message.setText("Choose a service");
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                inlineKeyboardMarkup.setKeyboard(keyBoard);
                message.setReplyMarkup(inlineKeyboardMarkup);
            } else {
                message.setText("say \" hi\" to start");

            }
        } else if (update.hasCallbackQuery()) {
            message.setChatId(update.getCallbackQuery().getFrom().getId());
            switch (update.getCallbackQuery().getData()) {
                case "joke" -> message.setText(Joke.getJokeText());
                case "cats facts" -> message.setText(Cat.getCatFact());
                case "numbers" -> message.setText(NumberInfoAPI.getNumber());
                case "quotes" -> message.setText(QuotesAPI.getQuotes());
                case "universities" -> {
                    List<List<InlineKeyboardButton>> keyBoard = Arrays.asList(universitiesButtons);
                    message.setText("Choose a country");
                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                    inlineKeyboardMarkup.setKeyboard(keyBoard);
                    message.setReplyMarkup(inlineKeyboardMarkup);
                }
                case "israel-universities" -> message.setText(UniversitiesAPI.getUniversities(5, "israel"));
                case "india-universities" -> message.setText(UniversitiesAPI.getUniversities(5, "india"));
                case "usa-universities" -> message.setText(UniversitiesAPI.getUniversities(5, "United+States"));
                case "spain-universities" -> message.setText(UniversitiesAPI.getUniversities(5, "spain"));
                case "japan-universities" -> message.setText(UniversitiesAPI.getUniversities(5, "japan"));
                case "china-universities" -> message.setText(UniversitiesAPI.getUniversities(5, "china"));
            }
        }
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void send(SendMessage sendMessage, String text) {
        try {
            sendMessage.setText(text);
            execute((sendMessage));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void addButton(UserChoice choice) {
        this.activeApiButtons.add(this.buttonMap.get(choice));
    }

    public void removeButton(UserChoice choice) {
        this.activeApiButtons.remove(this.buttonMap.get(choice));
    }
}