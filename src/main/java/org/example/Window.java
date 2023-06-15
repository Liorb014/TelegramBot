package org.example;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private MessagesBot bot;
    private static ControlPanel controlPanel;
    private static Charts charts;
    private static HistoryPanel usersHistory;

    public Window() {

        this.bot = Main.activateTelegramBot();
        this.setTitle("bot manager");
        this.setIconImage(new ImageIcon("src/main/java/Icons/ProgramLogo.jpg").getImage());
        this.setSize(Utils.WINDOW_SIZE,Utils.WINDOW_SIZE);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setName("API selection");
        controlPanel = new ControlPanel(this.bot);
        this.getContentPane().add(controlPanel);
        usersHistory = new HistoryPanel(this.bot);
        this.getContentPane().add(usersHistory);
        charts = new Charts();
        this.getContentPane().add(charts);
      //  this.getContentPane().setBackground(Color.DARK_GRAY);
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

    public static ControlPanel getControlPanel() {
        return controlPanel;
    }

    public static Charts getChars() {
        return charts;
    }

    public static HistoryPanel getUsersHistory() {
        return usersHistory;
    }
}
