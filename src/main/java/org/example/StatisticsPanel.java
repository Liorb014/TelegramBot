package org.example;

import java.awt.*;

public class StatisticsPanel extends Panel {
    private int amountOfRequests;
    private int amountOfUniqUsers;
    private String theMostActivateUser;
    private String theMostPopularActivity;
  //  private MessagesBot bot;

    private final int X_LINE_OF_TEXT = 80;
    private final int SPACE_BETWEEN_LINES = 100;

    public StatisticsPanel(MessagesBot bot) {
        super(bot);
//        this.bot = bot;
//        this.setSize(Utils.WINDOW_SIZE, Utils.WINDOW_SIZE);
//        this.setLayout(null);
//        this.setOpaque(true);
//        this.setBackground(Color.cyan);
//        this.setDoubleBuffered(true);
        new Thread(() -> {
            while (true) {
                while (super.isOpened()) {
                    this.amountOfRequests = bot.getAmountOfRequests();
                    this.amountOfUniqUsers = bot.getAmountOfUniqUsers();
                    this.theMostActivateUser = bot.GetTheMostActivateUser();
                    this.theMostPopularActivity = bot.getHistoryData();
                    repaint();
                    Utils.sleep(1);
                }
            }
        }).start();
        exitButton(this);
//        this.setVisible(false);
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
}
