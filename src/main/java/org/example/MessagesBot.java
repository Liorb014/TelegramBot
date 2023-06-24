package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.*;


public class MessagesBot extends TelegramLongPollingBot {
    private List<Update> updateList;
    private Map<Long, Integer> universitiesUserChoiceNumberList;
    private Map<Long, String> jokeUserChoiceCategories;
    private Map<Long, String> jokeUserChoiceLanguage;
    private List<String> historyData;
    private List<InlineKeyboardButton> apiButtons;
    private Map<UserChoice, InlineKeyboardButton> buttonMap;
    private List<InlineKeyboardButton> activeApiButtons;

    private final List<String> API_NAMES_LIST = List.of("joke", "numbers", "cats facts", "quotes", "universities");
    private final List<String> JOKES_CATEGORIES = List.of("any", "Programming", "Miscellaneous", "Dark", "Pun", "Spooky", "Christmas");
    private final List<String> JOKES_LANGUAGE = List.of("cs", "de", "en", "es", "fr", "pt");
    private final List<String> JOKES_AMOUNT = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    private final List<String> UNIVERSITIES_API_COUNTRIES_LIST = List.of("israel", "india", "united states", "spain", "japan", "china");
    private final List<String> UNIVERSITIES_API_NUMBER_LIST = List.of("1", "2", "3", "4", "5", "10", "15", "20", "25", "30");
    private final List<String> NUMBERS_API_TYPE_LIST = List.of("trivia", "math", "date", "year");

    private final int MAX_HISTORY_DATA = 10;
    private final int MAX_BUTTONS_IN_RAW = 5;
    private final int MAX_BUTTONS_IN_SUBMENU_RAW = 3;

    private final String JOKE = "joke";
    private final String NUMBERS = "numbers";
    private final String CATS_FACTS = "cats facts";
    private final String QUOTES = "quotes";
    private final String UNIVERSITIES = "universities";

    private final String START_COMMAND = "/start";
    private final String HI_START_COMMAND = "hi";
    private final String MENU_TEXT = "Choose a service";
    private final String AUTO_BOT_REPLAY = "say \" hi\" to start";
    private final String FORMAT_TO_DISPLAY_HISTORY_DATE_AND_TIME = "dd//MM/yy  hh:mm";

    public MessagesBot() {
        this.activeApiButtons = new ArrayList<>();
        this.updateList = new ArrayList<>();
        this.universitiesUserChoiceNumberList = new HashMap<>();
        this.apiButtons = new ArrayList<>();
        this.buttonMap = new HashMap<>();
        this.jokeUserChoiceLanguage = new HashMap<>();
        this.jokeUserChoiceCategories = new HashMap<>();
        this.historyData = new ArrayList<>();

        for (String name : this.API_NAMES_LIST) {
            createKeyboardButton("", name, this.apiButtons);
        }

        int counter = 0;
        for (UserChoice choice : UserChoice.values()) {
            this.buttonMap.put(choice, this.apiButtons.get(counter));
            counter++;
        }
    }

    @Override
    public String getBotUsername() {
        return "API overall service bot";
    }

    @Override
    public String getBotToken() {
        return "6264561273:AAHrWL7V3qIHRIVafPTrQU0SKlhJC_XjsI8";
    }

    private void createKeyboardButton(String base, String name, List<InlineKeyboardButton> list) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(name);
        inlineKeyboardButton.setCallbackData(base + name);
        list.add(inlineKeyboardButton);
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateList.add(update);
        SendMessage message = new SendMessage();
        if (update.hasMessage()) {
            instructions(update, message);
            message.setChatId(update.getMessage().getChatId());
            introduceApiMenu(update, message);

        } else if (update.hasCallbackQuery()) {
            long chatId = update.getCallbackQuery().getFrom().getId();
            message.setChatId(chatId);
            String userChoice = update.getCallbackQuery().getData();

            if (userChoice.contains(UNIVERSITIES)) {
                if (universitiesApi(update, message, chatId, userChoice)) return;

            } else if (userChoice.contains(NUMBERS)) {
                if (numbersApi(update, message, userChoice)) return;

            } else if (userChoice.contains(CATS_FACTS)) {
                message.setText(new JsonHandler<>(Cat.class).readJson(Cat.PATH).toString());
                addHistory(update);

            } else if (userChoice.contains(QUOTES)) {
                message.setText(new JsonHandler<>(QuotesAPI.class).readJson(QuotesAPI.PATH).toString());
                addHistory(update);

            } else if (userChoice.contains(JOKE)) {
                if (jokeApi(update, message, chatId, userChoice)) return;

            }
        }
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void introduceApiMenu(Update update, SendMessage message) {
        if (update.getMessage().getText().equals(START_COMMAND) || update.getMessage().getText().toLowerCase().equals(HI_START_COMMAND)) {
            List<List<InlineKeyboardButton>> keyBoard = Arrays.asList(activeApiButtons);
            message.setText(MENU_TEXT);
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyBoard);
            message.setReplyMarkup(inlineKeyboardMarkup);
        } else {
            message.setText(AUTO_BOT_REPLAY);
        }
    }

    private void instructions(Update update, SendMessage message) {
        if (update.getMessage().isCommand()) {
            switch (update.getMessage().getText()) {
                case "/start" -> System.out.println(START_COMMAND);
                default -> message.setText("Unknown command");
            }
        }
    }

    private boolean numbersApi(Update update, SendMessage message, String userChoice) {
        if (userChoice.equals(NUMBERS)) {
            editQueryMessage(update, "choose type of fact  : ", makeKeyboard(NUMBERS_API_TYPE_LIST, "numbers-type-",MAX_BUTTONS_IN_SUBMENU_RAW));
            return true;
        } else if (userChoice.contains("numbers-type-")) {
            String type = userChoice.replace("numbers-type-", "");
            message.setText(new JsonHandler<>(NumberInfoAPI.class).readJson("http://numbersapi.com/random/" + type + "?json").toString());

            addHistory(update);
        }
        return false;
    }

    private boolean universitiesApi(Update update, SendMessage message, long chatId, String userChoice) {
        if (userChoice.equals(UNIVERSITIES)) {
            editQueryMessage(update, "choose number of institutions : ", makeKeyboard(UNIVERSITIES_API_NUMBER_LIST, "universities-number-",MAX_BUTTONS_IN_RAW));
            return true;
        } else if (userChoice.contains("universities-number-")) {
            universitiesUserChoiceNumberList.put(chatId, Integer.valueOf(userChoice.replace("universities-number-", "")));
            editQueryMessage(update, "choose country institutions origin  : ", makeKeyboard(UNIVERSITIES_API_COUNTRIES_LIST, "universities-country-",MAX_BUTTONS_IN_SUBMENU_RAW));
            return true;
        } else if (userChoice.contains("universities-country-")) {
            String country = userChoice.replace("universities-country-", "");
            country = country.replace(" ", "+");
            UniversitiesAPI[] array = new JsonHandler<>(UniversitiesAPI[].class).readJson("http://universities.hipolabs.com/search?country=" + country);
            List<UniversitiesAPI> lista = List.of(array);
            message.setText(lista.stream().limit(universitiesUserChoiceNumberList.get(chatId)).toList().toString());
            addHistory(update);
        }
        return false;
    }

    private boolean jokeApi(Update update, SendMessage message, long chatId, String userChoice) {
        if (userChoice.equals(JOKE)) {
            editQueryMessage(update, "choose type of joke  : ", makeKeyboard(JOKES_CATEGORIES, "joke-categories-",MAX_BUTTONS_IN_SUBMENU_RAW));
            return true;
        } else if (userChoice.contains("joke-categories-")) {
            jokeUserChoiceCategories.put(chatId, userChoice.replace("joke-categories-", ""));
            editQueryMessage(update, "choose language of joke  : ", makeKeyboard(JOKES_LANGUAGE, "joke-language-",MAX_BUTTONS_IN_SUBMENU_RAW));
            return true;
        } else if (userChoice.contains("joke-language-")) {
            jokeUserChoiceLanguage.put(chatId, userChoice.replace("joke-language-", ""));
            editQueryMessage(update, "choose amount of jokes  : ", makeKeyboard(JOKES_AMOUNT, "joke-amount-",MAX_BUTTONS_IN_SUBMENU_RAW));
            return true;
        } else if (userChoice.contains("joke-amount-")) {
            String amount = userChoice.replace("joke-amount-", "");
            String path = "https://v2.jokeapi.dev/joke/" + jokeUserChoiceCategories.get(chatId) + "?lang=" + jokeUserChoiceLanguage.get(chatId) + "&amount=" + amount;
            if (amount.equals("1")) {
                message.setText(new JsonHandler<>(Joke.class).readJson(path).toString());
            } else {
                message.setText(new JsonHandler<>(Jokes.class).readJson(path).toString());
            }
            addHistory(update);
        }
        return false;
    }

    private InlineKeyboardMarkup makeKeyboard(List<String> list, String callBackData, int amountInRaw ) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        int buttonsPerRow = amountInRaw;
        int rowCount = (int) Math.ceil((double) list.size() / buttonsPerRow);
        for (int i = 0; i < rowCount; i++) {
            int startIndex = i * buttonsPerRow;
            int endIndex = Math.min(startIndex + buttonsPerRow, list.size());
            List<String> rowOptions = list.subList(startIndex, endIndex);
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (String option : rowOptions) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(option);
                button.setCallbackData(callBackData + option);
                row.add(button);
            }
            rows.add(row);
        }
        return new InlineKeyboardMarkup(rows);

    }

    public void editQueryMessage(Update update, String text, InlineKeyboardMarkup keyboard) {
        EditMessageText message = new EditMessageText(text);
        message.setChatId(update.getCallbackQuery().getFrom().getId());
        message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        message.setReplyMarkup(keyboard);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    public synchronized void addHistory(Update update) {
        String name = update.getCallbackQuery().getFrom().getFirstName();
        String userChoose = update.getCallbackQuery().getData();
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_TO_DISPLAY_HISTORY_DATE_AND_TIME);
        Date date = new Date();
        this.historyData.add(0, name + " " + userChoose + " " + format.format(date));
        if (this.historyData.size() > MAX_HISTORY_DATA) {
            this.historyData.remove(MAX_HISTORY_DATA);
        }
    }

    public void addButton(UserChoice choice) {
        this.activeApiButtons.add(this.buttonMap.get(choice));
    }

    public void removeButton(UserChoice choice) {
        this.activeApiButtons.remove(this.buttonMap.get(choice));
    }

    public List<Update> getUpdateList() {
        return updateList;
    }

    public List<String> getHistoryData() {
      return this.historyData;
    }
}