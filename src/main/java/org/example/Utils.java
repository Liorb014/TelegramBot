package org.example;

import javax.swing.*;
import java.awt.*;

public class Utils {
    private static final int SECOND = 1000;

    public static void sleep(int seconds) {
        try {
            Thread.sleep(SECOND * seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
//
//    public static final int WINDOW_SIZE = 500;
//
//    public static void drawString(Graphics g, String text, int x, int y) {
//        for (String line : text.split("\n"))
//            g.drawString(line, x, y += g.getFontMetrics().getHeight());
//    }
//
//    public static JButton exitButton(Panel panel) {
//        JButton exitButton = new JButton("X");
//        exitButton.setFont(new Font("Ariel", Font.BOLD, 12));
//        exitButton.setBounds(0, 0, 50, 50);
//        exitButton.setVisible(true);
//        panel.add(exitButton);
//        exitButton.addActionListener((event) -> {
//            Window.changePanel(Window.getControlPanel(), panel);
//        });
//        return exitButton;
//    }

}
