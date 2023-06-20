package org.example;

import javax.swing.*;
import java.awt.*;

public abstract class Panel extends JPanel {
    private MessagesBot bot;
    private Boolean isOpened;

    public static final int WINDOW_SIZE = 500;

    public Panel(MessagesBot bot){
        this.isOpened = false;
        this.bot= bot;
        this.setSize(WINDOW_SIZE, WINDOW_SIZE);
        this.setLayout(null);
        this.setOpaque(true);
        this.setBackground(Color.cyan);
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
        JButton exitButton = new JButton("X");
        exitButton.setFont(new Font("Ariel", Font.BOLD, 12));
        exitButton.setBounds(0, 0, 50, 50);
        exitButton.setVisible(true);
        panel.add(exitButton);
        exitButton.addActionListener((event) -> {
            Window.changePanel(Window.getControlPanel(), panel);
        });
        return exitButton;
    }

}
