package org.example;

import javax.swing.*;

public class Window extends JFrame {
    private MessagesBot bot;

    public Window(){
        this.bot = Main.activateTelegramBot();
        this.setTitle("bot manager");
        this.setIconImage(new ImageIcon("src/main/java/Icons/ProgramLogo.jpg").getImage());
        this.setSize(Utils.WINDOW_SIZE,Utils.WINDOW_SIZE);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setName("API selection");
        this.add(new ControlPanel(bot));
        this.setVisible(true);
    }

    public static void changePanel(JPanel newPanel, JPanel oldPanel) {
        try {
            newPanel.setVisible(true);
            newPanel.requestFocusInWindow();
            oldPanel.setVisible(false);
        } catch (NullPointerException e) {
        }

    }
}
