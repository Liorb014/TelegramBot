package org.example;

import org.telegram.telegrambots.meta.api.objects.Update;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryPanel extends JPanel {
    private MessagesBot bot;
    private JLabel historyText;

    public HistoryPanel(MessagesBot bot) {
        this.bot = bot;
        this.setSize(Utils.WINDOW_SIZE, Utils.WINDOW_SIZE);
        this.setLayout(null);
        this.setOpaque(true);
        this.setBackground(Color.cyan);
        this.setDoubleBuffered(true);
        new Thread(() -> {
            while (true) {
                while (this.hasFocus()) {
                    repaint();
                    Utils.sleep(1);
                }
            }
        }).start();

        JButton exitButton = new JButton("X");
        exitButton.setFont(new Font("Ariel", Font.BOLD, 12));
        exitButton.setBounds(0, 0, 50, 50);
        exitButton.setVisible(true);
        this.add(exitButton);
        exitButton.addActionListener((event) -> {
            Window.changePanel(Window.getControlPanel(), this);
        });
        this.setVisible(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Ariel" , Font.BOLD ,20));
        drawString(g, "the last 10 requests: ", 150, 15);
        g.setFont(new Font("Ariel" , Font.BOLD,13));
        drawString(g, bot.getHistoryData(), 152, 65);
    }

    public static void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    private List<String> lastTen() {
        java.util.List<Update> temp = bot.getUpdateList();
        //   Collections.reverse(temp);
        return temp
                .stream()
                .parallel()
                .filter(update -> update.hasCallbackQuery())
                .limit(10)
                .map(update -> formatUpdate(update))
                .collect(Collectors.toList());

    }


    public String formatUpdate(Update update) {
        String name = update.getCallbackQuery().getFrom().getFirstName();
        if (update.getCallbackQuery().getFrom().getLastName() != null) {
            name += " " + update.getCallbackQuery().getFrom().getLastName();
        }
        String userChoose = update.getCallbackQuery().getData();
        SimpleDateFormat format = new SimpleDateFormat("dd//MM/yy  hh:mm");
        Date date = new Date();
        return (name + " , " + userChoose + " , " + format.format(date));
    }
}
