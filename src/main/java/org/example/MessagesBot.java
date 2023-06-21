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
    private Map<Long, String> jokeUserChoiceNumber;
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

    public MessagesBot() {
        this.activeApiButtons = new ArrayList<>();
        this.updateList = new ArrayList<>();
        this.universitiesUserChoiceNumberList = new HashMap<>();
        this.apiButtons = new ArrayList<>();
        this.buttonMap = new HashMap<>();
        this.jokeUserChoiceNumber = new HashMap<>();
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
            if (update.getMessage().isCommand()) {
                switch (update.getMessage().getText()) {
                    case "/start" -> System.out.println("start");
                    //      case "/help" -> System.out.println("help");
                    //     case "/bye" -> System.out.println("bye");
                    //         case "/joke" -> System.out.println("joke");
                    default -> message.setText("Unknown command");
                }
            }
            message.setChatId(update.getMessage().getChatId());
            if (update.getMessage().getText().equals("/start") || update.getMessage().getText().toLowerCase().equals("hi")) {
                List<List<InlineKeyboardButton>> keyBoard = Arrays.asList(activeApiButtons);
                message.setText("Choose a service");
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                inlineKeyboardMarkup.setKeyboard(keyBoard);
                message.setReplyMarkup(inlineKeyboardMarkup);
            } else {
                message.setText("say \" hi\" to start");
            }
        } else if (update.hasCallbackQuery()) {
            //  addHistory(update);
            long chatId = update.getCallbackQuery().getFrom().getId();
            message.setChatId(chatId);
            String s = update.getCallbackQuery().getData();

            if (s.contains("universities")) {
                if (universitiesApi(update, message, chatId, s)) return;
            } else if (s.contains("numbers")) {
                if (numbersApi(update, message, s)) return;
            } else if (s.contains("cats facts")) {
                message.setText(new JsonHandler<>(Cat.class).readJson(Cat.PATH).getS());
                addHistory(update);
            } else if (s.contains("quotes")) {
           //     message.setText(QuotesAPI.getQuotes());
                System.out.println("Aaa");
                message.setText(new JsonHandler<>(QuotesAPI.class).readJson("https://rest-quotes-api.onrender.com/api/quotes/random").toString());
                System.out.println("bb");
                addHistory(update);
            } else if (s.contains("joke")) {
                if (jokeApi(update, message, chatId, s)) return;
            }
        }
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean numbersApi(Update update, SendMessage message, String s) {
        if (s.equals("numbers")) {
            editQueryMessage(update, "choose type of fact  : ", makeKeyboard(NUMBERS_API_TYPE_LIST, "numbers-type-"));
            return true;
        } else if (s.contains("numbers-type-")) {
            String type = s.replace("numbers-type-", "");
            message.setText(NumberInfoAPI.getNumber(type));
            addHistory(update);
        }
        return false;
    }

    private boolean universitiesApi(Update update, SendMessage message, long chatId, String s) {
        if (s.equals("universities")) {
            editQueryMessage(update, "choose number of institutions : ", makeKeyboard(UNIVERSITIES_API_NUMBER_LIST, "universities-number-"));
            return true;
        } else if (s.contains("universities-number-")) {
            universitiesUserChoiceNumberList.put(chatId, Integer.valueOf(s.replace("universities-number-", "")));
            editQueryMessage(update, "choose country institutions origin  : ", makeKeyboard(UNIVERSITIES_API_COUNTRIES_LIST, "universities-country-"));
            return true;
        } else if (s.contains("universities-country-")) {
            String country = s.replace("universities-country-", "");
            country = country.replace(" ", "+");
            message.setText(UniversitiesAPI.getUniversities(universitiesUserChoiceNumberList.get(chatId), country));
            addHistory(update);
        }
        return false;
    }

    private boolean jokeApi(Update update, SendMessage message, long chatId, String s) {
        if (s.equals("joke")) {
            editQueryMessage(update, "choose type of joke  : ", makeKeyboard(JOKES_CATEGORIES, "joke-categories-"));
            return true;
        } else if (s.contains("joke-categories-")) {
            jokeUserChoiceCategories.put(chatId, s.replace("joke-categories-", ""));
            editQueryMessage(update, "choose language of joke  : ", makeKeyboard(JOKES_LANGUAGE, "joke-language-"));
            return true;
        } else if (s.contains("joke-language-")) {
            jokeUserChoiceLanguage.put(chatId, s.replace("joke-language-", ""));
            editQueryMessage(update, "choose amount of jokes  : ", makeKeyboard(JOKES_AMOUNT, "joke-amount-"));
            return true;
        } else if (s.contains("joke-amount-")) {
            message.setText(Joke.nnn(jokeUserChoiceCategories.get(chatId), jokeUserChoiceLanguage.get(chatId), s.replace("joke-amount-", "")));
            addHistory(update);
        }
        return false;
    }

    private InlineKeyboardMarkup makeKeyboard(List<String> list, String callBackData) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        int buttonsPerRow = 5;
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
        SimpleDateFormat format = new SimpleDateFormat("dd//MM/yy  hh:mm");
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

    public String getHistoryData() {
        String result = "";
        for (String data : this.historyData) {
            result += data + "\n\n";
        }
        return result;
    }
}