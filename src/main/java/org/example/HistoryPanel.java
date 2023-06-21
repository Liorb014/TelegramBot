package org.example;

import org.telegram.telegrambots.meta.api.objects.Update;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryPanel extends Panel {
    private JLabel historyText;

    public HistoryPanel(MessagesBot bot) {
        super(bot);
        new Thread(() -> {
            while (true) {
                //dont delete the sout , Daniel i am looking at you
                System.out.print("");
                while (super.isOpened()) {
                    repaint();
                    Utils.sleep(1);
                }
            }
        }).start();
        exitButton(this);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Ariel" , Font.BOLD ,20));
        drawString(g, "the last 10 requests: ", 150, 15);
        g.setFont(new Font("Ariel" , Font.BOLD,13));
        drawString(g, super.getBot().getHistoryData(), 152, 65);
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
