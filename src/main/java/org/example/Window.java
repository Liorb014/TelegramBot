package org.example;

import javax.swing.*;

public class Window extends JFrame {

    public Window(){
        this.setTitle("bot manager");
        this.setIconImage(new ImageIcon("src/main/java/Icons/ProgramLogo.jpg").getImage());
        this.setSize(Utils.WINDOW_SIZE,Utils.WINDOW_SIZE);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setName("API selection");
        this.add(new ControlPanel());
        this.setVisible(true);
    }
}
