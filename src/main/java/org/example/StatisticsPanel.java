package org.example;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StatisticsPanel extends Panel {
    private int amountOfRequests;
    private int amountOfUniqUsers;
    private String theMostActivateUser;
    private String theMostPopularActivity;
    private final int X_LINE_OF_TEXT = 80;
    private final int SPACE_BETWEEN_LINES = 100;

    public StatisticsPanel(MessagesBot bot) {
        super(bot);
        new Thread(() -> {
            while (true) {
//dont delete the sout , Daniel i am looking at you
                System.out.print("");
                if (super.isOpened()) {
                    this.amountOfRequests = getAmountOfRequests();
                    this.amountOfUniqUsers = getAmountOfUniqUsers();
                    this.theMostActivateUser = getTheMostActivateUser();
                    this.theMostPopularActivity = getTheMostPopularActivity();
                    repaint();
                    Utils.sleep(1);
                }
            }
        }).start();
        exitButton(this);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setFont(new Font("Ariel", Font.BOLD, 20));
        drawString(graphics, "Bot statistics", 50, 15);
        graphics.setFont(new Font("Ariel", Font.BOLD, 13));
        drawString(graphics, "Over all amount of requests: " + this.amountOfRequests, X_LINE_OF_TEXT, SPACE_BETWEEN_LINES);
        drawString(graphics, "The number of different distinct users: " + this.amountOfUniqUsers, X_LINE_OF_TEXT, SPACE_BETWEEN_LINES * 2);
        drawString(graphics, "The most activate user is: " + this.theMostActivateUser, X_LINE_OF_TEXT, SPACE_BETWEEN_LINES * 3);
        drawString(graphics, "The most popular activity is: " + this.theMostPopularActivity, X_LINE_OF_TEXT, SPACE_BETWEEN_LINES * 4);
    }


    private String getTheMostPopularActivity() {
        if (super.getBot().getUpdateList().stream().noneMatch(Update::hasCallbackQuery)) {
            return "null";
        } else {
            Map<String, Long> temp = super.getBot().getUpdateList().stream()
                    .filter(Update::hasCallbackQuery)
                    .map(update -> update.getCallbackQuery().getData())
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            List<String> topActivities = temp.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(1).
                    map(Map.Entry::getKey).toList();
            return topActivities.get(0);
        }
    }


    public int getAmountOfRequests() {
        return super.getBot().getUpdateList().size();
    }

    public int getAmountOfUniqUsers() {
        return (int) super.getBot().getUpdateList().stream()
                .filter(Update::hasMessage)
                .map(update -> update.getMessage().getFrom().getId())
                .distinct()
                .count();
    }

    public String getTheMostActivateUser() {
        if (super.getBot().getUpdateList().stream().noneMatch(Update::hasCallbackQuery)) {
            return "null";
        } else {
            Map<Long, Long> temp = super.getBot().getUpdateList()
                    .stream()
                    .filter(Update::hasCallbackQuery)
                    .map(update -> update.getCallbackQuery().getFrom().getId())
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            List<Long> chatId = temp.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(1).
                    map(Map.Entry::getKey).toList();
            return getUserByChatId(chatId.get(0)).getFirstName();
        }
    }

    private User getUserByChatId(long userID) {
        return super.getBot().getUpdateList().stream()
                .filter(Update::hasMessage)
                .filter(update -> update.getMessage().getFrom().getId().equals(userID))
                .map(update -> update.getMessage().getFrom())
                .findFirst()
                .orElse(null);
    }


}
