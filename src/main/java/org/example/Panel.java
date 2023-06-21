package org.example;

import javax.swing.*;
import java.awt.*;

public abstract class Panel extends JPanel {
    private MessagesBot bot;
    private Boolean isOpened;

    public static final int WINDOW_SIZE = 500;

    public static final int EXIT_BUTTON_SIZE = 50;
    public static final int EXIT_BUTTON_X = 0;
    public static final int EXIT_BUTTON_Y = 0;
    public static final String EXIT_BUTTON_TEXT = "X";
    public static final int EXIT_BUTTON_TEXT_SIZE = 12;
    public static final String ALL_TEXTS_STILE = "Ariel";

    private final int BG_RED_AMOUNT = 111;
    private final int BG_GREEN_AMOUNT = 211;
    private final int BG_BLUE_AMOUNT = 255;

    public Panel(MessagesBot bot){
        this.isOpened = false;
        this.bot= bot;
        this.setSize(WINDOW_SIZE, WINDOW_SIZE);
        this.setLayout(null);
        this.setOpaque(true);
        this.setBackground(new Color(BG_RED_AMOUNT,BG_GREEN_AMOUNT,BG_BLUE_AMOUNT));
        this.setDoubleBuffered(true);
        this.setVisible(false);
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean isOpened) {
                 this. isOpened = isOpened;
    }

    public MessagesBot getBot() {
        return bot;
    }

    public static void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    public static JButton exitButton(Panel panel) {
        JButton exitButton = new JButton(EXIT_BUTTON_TEXT);
        exitButton.setFont(new Font(ALL_TEXTS_STILE, Font.BOLD, EXIT_BUTTON_TEXT_SIZE));
        exitButton.setBounds(EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_SIZE, EXIT_BUTTON_SIZE);
        exitButton.setVisible(true);
        panel.add(exitButton);
        exitButton.addActionListener((event) -> {
            Window.changePanel(Window.getControlPanel(), panel);
        });
        return exitButton;
    }

}
