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
    private final int TITLE_SIZE = 20;
    private final int TITLE_X = 20;
    private final int TITLE_Y = 20;
    private final String TITLE_TEXT = "The last 10 requests: ";

    private final int REQUESTS_SIZE = 13;
    private final int REQUESTS_X = 152;
    private final int REQUESTS_Y = 62;

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
        g.setFont(new Font(Panel.ALL_TEXTS_STILE, Font.BOLD, TITLE_SIZE));
        drawString(g, TITLE_TEXT, TITLE_X, TITLE_Y);
        g.setFont(new Font(Panel.ALL_TEXTS_STILE, Font.BOLD, REQUESTS_SIZE));
        drawString(g, super.getBot().getHistoryData(), REQUESTS_X, REQUESTS_Y);
    }

}
