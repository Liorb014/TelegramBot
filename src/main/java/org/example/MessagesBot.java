package org.example;

import org.checkerframework.checker.units.qual.A;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MessagesBot extends TelegramLongPollingBot {
    private Map<Long, Integer> uniNumbers = new HashMap();
    private List<Update> updateList = new ArrayList<>();
    private List<Long> chatIds;
    private List<String> historyData;
    private HashSet<User> users;
    private List<InlineKeyboardButton> apiButtons;
    private List<InlineKeyboardButton> universitiesButtons = new ArrayList<>();
    private Map<UserChoice, InlineKeyboardButton> buttonMap;
    private static List<InlineKeyboardButton> activeApiButtons = new ArrayList<>();
    public static List<InlineKeyboardButton> menuButtons = new ArrayList<>();

    private final List<String> apiNames = List.of("joke", "cats facts", "numbers", "quotes", "universities");
    private final List<String> universitiesCountries = List.of("israel", "india", "usa", "spain", "japan", "china");
    private final List<String> optionsOfTheNumberOfUniToShow = List.of("1", "2", "3", "4", "5", "10", "15", "20", "25", "30");
    private final int MAX_HISTORY_DATA = 10;

    public MessagesBot() {
        this.chatIds = new ArrayList<>();
        this.users = new HashSet<>();
        this.apiButtons = new ArrayList<>();
        this.buttonMap = new HashMap<>();
        this.historyData = new ArrayList<>();

        for (String name : this.apiNames) {
            createKeyboardButton("", name, this.apiButtons);
        }

        int counter = 0;
        for (UserChoice choice : UserChoice.values()) {
            System.out.println(choice);
            this.buttonMap.put(choice, this.apiButtons.get(counter));
            counter++;
        }

        for (String countryName : universitiesCountries) {
            createKeyboardButton("universities-country-", countryName, this.universitiesButtons);
        }
    }

    private void createKeyboardButton(String base, String name, List list) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(name);
        inlineKeyboardButton.setCallbackData(base + name);
        list.add(inlineKeyboardButton);
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

            users.add(update.getMessage().getFrom());
            chatIds.add(update.getMessage().getChatId());
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
            addHistory(update);
            long chatId = update.getCallbackQuery().getFrom().getId();
            message.setChatId(chatId);
            String s = update.getCallbackQuery().getData();

            if (s.contains("universities")) {
                if (s.equals("universities")) {
                    sendSelectionBox(update);
                    return;
                }
                if (s.contains("universities-number-")) {
                    uniNumbers.put(chatId, Integer.valueOf(s.replace("universities-number-", "")));
                    List<List<InlineKeyboardButton>> keyBoard = Arrays.asList(universitiesButtons);
                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                    inlineKeyboardMarkup.setKeyboard(keyBoard);
                    editQueryMessage(update, "choose country institutions origin  : ", inlineKeyboardMarkup);
                    return;
                }

                if (s.contains("universities-country-")) {
                    String country = s.replace("universities-country-", "");
                    if (country.equals("usa")) {
                        country = "united+states";
                    }
                    message.setText(UniversitiesAPI.getUniversities(uniNumbers.get(chatId), country));
                }
            }
            switch (s) {
                case "joke" -> message.setText(Joke.getJokeText());
                case "cats facts" -> message.setText(Cat.getCatFact());
                case "numbers" -> message.setText(NumberInfoAPI.getNumber());
                case "quotes" -> message.setText(QuotesAPI.getQuotes());
            }
        }
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
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

    private void sendSelectionBox(Update update) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        int buttonsPerRow = 5;
        int rowCount = (int) Math.ceil((double) optionsOfTheNumberOfUniToShow.size() / buttonsPerRow);
        for (int i = 0; i < rowCount; i++) {
            int startIndex = i * buttonsPerRow;
            int endIndex = Math.min(startIndex + buttonsPerRow, optionsOfTheNumberOfUniToShow.size());
            List<String> rowOptions = optionsOfTheNumberOfUniToShow.subList(startIndex, endIndex);
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (String option : rowOptions) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(option);
                button.setCallbackData("universities-number-" + option);
                row.add(button);
            }
            rows.add(row);
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rows);
        editQueryMessage(update, "choose number of institutions : ", inlineKeyboardMarkup);
    }

    private void send(SendMessage sendMessage, String text) {
        try {
            sendMessage.setText(text);
            execute((sendMessage));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void addHistory(Update update) {
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
        System.out.println(choice);
        System.out.println(buttonMap.size());
        System.out.println((this.buttonMap.get(choice)));
        this.activeApiButtons.add(this.buttonMap.get(choice));
    }

    public void removeButton(UserChoice choice) {
        this.activeApiButtons.remove(this.buttonMap.get(choice));
    }

    private long getChatId(Update update) {
        long chatId = 0;
        if (update.getMessage() != null) {
            chatId = update.getMessage().getChatId();
        } else {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }
        return chatId;
    }

    public HashSet<User> getUsers() {
        return this.users;
    }

    public List<Long> getChatIds() {
        return this.chatIds;
    }


    public long getMessageCount() {
        return this.updateList
                .stream()
                .filter(update -> update.hasMessage())
                .count();
    }

    public void getMost() {
        this.updateList
                .stream()
                .filter(update -> update.hasMessage())
                .map(update -> update.getMessage().getFrom())
                .collect(Collectors.groupingBy(Function.identity()));

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

    public List<String> getHistory() {
        return this.historyData;
    }
}