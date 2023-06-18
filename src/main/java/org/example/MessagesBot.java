package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MessagesBot extends TelegramLongPollingBot {
    private   List<Update> updateList =  new ArrayList<>();
    public  List<Long> chatIds;
    public static List<InlineKeyboardButton> menuButtons = new ArrayList<>();
    public List<InlineKeyboardButton> universitiesButtons = new ArrayList<>();
    private Map<UserChoice, InlineKeyboardButton> buttonMap;
    private static List<InlineKeyboardButton> activeApiButtons = new ArrayList<>();
    private HashSet<User> users;
    private final List<String> universitiesCountries = List.of("israel", "india", "usa", "spain", "japan","china");
    private final List<String> optionsOfTheNumberOfUniToShow = List.of("1", "2", "3","4","5","6","7","8","9","10");

    private List<String> historyData;
    
    final int MAX_HISTORY_DATA = 10;


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

        InlineKeyboardButton universities = new InlineKeyboardButton("universities");
        universities.setCallbackData("universities");
        menuButtons.add(universities);

        for (String countryName : universitiesCountries) {
            createUniversityButton(countryName);
        }

        this.buttonMap = new HashMap<>();
        this.buttonMap.put(UserChoice.JOKE, jokeButton);
        this.buttonMap.put(UserChoice.CATS_FACTS, catsFactsButton);
        this.buttonMap.put(UserChoice.NUMBER, numbersInfoButton);
        this.buttonMap.put(UserChoice.QUOTES, quotesButton);
        this.buttonMap.put(UserChoice.UNIVERSITIES, universities);

        this.historyData = new ArrayList<>();
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
      updateList.add(update);
        SendMessage message = new SendMessage();
        if (update.hasMessage()) {

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
                    sendSelectionBox(getChatId(update));

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

    private void sendSelectionBox(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Please select an option:");

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        int buttonsPerRow = 4; // Adjust the number of buttons per row as needed
        int rowCount = (int) Math.ceil((double) optionsOfTheNumberOfUniToShow.size() / buttonsPerRow);

        for (int i = 0; i < rowCount; i++) {
            int startIndex = i * buttonsPerRow;
            int endIndex = Math.min(startIndex + buttonsPerRow, optionsOfTheNumberOfUniToShow.size());
            List<String> rowOptions = optionsOfTheNumberOfUniToShow.subList(startIndex, endIndex);

            List<InlineKeyboardButton> row = new ArrayList<>();
            for (String option : rowOptions) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(option);
                button.setCallbackData(option);
                row.add(button);
            }
            rows.add(row);
        }

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);

        message.setReplyMarkup(keyboardMarkup);


        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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

    public void addHistory(Update update){
        String name = update.getCallbackQuery().getFrom().getFirstName() ;
        String userChoose = update.getCallbackQuery().getData();
        SimpleDateFormat format = new SimpleDateFormat("dd//MM/yy  hh:mm");
        Date date = new Date();
        this.historyData.add(0,name+" "+userChoose +" "+format.format(date));
        if(this.historyData.size() > MAX_HISTORY_DATA){
            this.historyData.remove(MAX_HISTORY_DATA);
        }
    }

    public void addButton(UserChoice choice) {
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

    public  List<Long> getChatIds() {
        return this.chatIds;
    }


    public long getMessageCount(){
     return   this.updateList
               .stream()
               .filter(update -> update.hasMessage())
               .count();
    }

    public void getMost(){
        this.updateList
                .stream()
                .filter(update -> update.hasMessage())
                .map(update ->update.getMessage().getFrom())
                .collect(Collectors.groupingBy(Function.identity()));

    }

    public  List<Update> getUpdateList() {
        return updateList;
    }



    public String getHistoryData() {
        String result ="";
        for (String data:this.historyData) {
            result += data+"\n\n";
        }
        return result;
    }



    public List<String> getHistory() {

        return this.historyData;
    }}