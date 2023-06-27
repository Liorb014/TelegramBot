package org.example;

import java.awt.*;

public class HistoryPanel extends Panel {

    public HistoryPanel(MessagesBot bot) {
        super(bot);
        new Thread(() -> {
            while (true) {
                synchronized (this) {
                while (super.isOpened()) {
             //           while (this.isVisible()) {
                        repaint();
                        Utils.sleep(1);
                    }
                }
            }
        }).start();
        exitButton(this);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Ariel", Font.BOLD, 20));
        drawString(g, "the last 10 requests: ", 150, 15);
        g.setFont(new Font("Ariel", Font.BOLD, 13));
        drawString(g, this.getHistoryData(), 152, 65);
    }

    private String getHistoryData() {
        StringBuilder result = new StringBuilder();
        for (String data : super.getBot().getHistoryData()) {
            result.append(data).append("\n\n");
        }
        return result.toString();
    }

}
