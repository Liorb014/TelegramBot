package org.example;

import javax.swing.*;

public class Window extends JFrame {
    private MessagesBot bot;
    private static ControlPanel controlPanel;
    private static HistoryPanel usersHistory;
    private static StatisticsPanel statistics;
    private static Charts charts;
    private final String LOGO_IMAGE_PATH="src/main/java/Icons/ProgramLogo.jpg";


    public Window() {
        this.bot = Main.activateTelegramBot();
        this.setTitle("bot manager");
        this.setIconImage(new ImageIcon(LOGO_IMAGE_PATH).getImage());
        this.setSize(Panel.WINDOW_SIZE, Panel.WINDOW_SIZE);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setName("API selection");
        controlPanel = new ControlPanel(this.bot);
        this.getContentPane().add(controlPanel);
        usersHistory = new HistoryPanel(this.bot);
        this.getContentPane().add(usersHistory);
        statistics = new StatisticsPanel(this.bot);
        this.getContentPane().add(statistics);
        charts = new Charts(this.bot);
        this.getContentPane().add(charts);
        this.setVisible(true);
    }

    public static void changePanel(Panel newPanel, Panel oldPanel) {
        try {
            newPanel.setVisible(true);
            newPanel.requestFocusInWindow();
            newPanel.setOpened(true);
            oldPanel.setOpened(false);
            oldPanel.setVisible(false);
        } catch (NullPointerException e) {
        }

    }

    public static ControlPanel getControlPanel() {
        return controlPanel;
    }

    public static HistoryPanel getUsersHistory() {
        return usersHistory;
    }

    public static  StatisticsPanel getStatistics(){
        return statistics;
    }

    public static Charts getCharts() {
        return charts;
    }


}
