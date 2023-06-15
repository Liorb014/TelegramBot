package org.example;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class GraphOfUses extends JPanel {
    private final URL graphUrl = new URL("https://quickchart.io/chart/render/zm-1f1ddf68-cb0a-4f49-a65a-b106e3aec721?title= number of requests over time chart&labels=d1,d2,d3,d4,d5,d6,d7,w1d1,w1d2,w1d3,w1d4,w1d5,w1d6,w1d7,Q4&data1=0,0,0,200,100,50,100,20,10,0,0,50,30,10");

    public GraphOfUses() throws MalformedURLException {
        ImageIcon icon = new ImageIcon(graphUrl);
        JLabel graphLabel = new JLabel(icon);
        graphLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        graphLabel.setVisible(true);
        this.add(graphLabel);
        JButton exitButton = new JButton("x");
        exitButton.setBounds(0, 0, 5, 5);
        exitButton.setVisible(true);
        this.add(exitButton);
        exitButton.addActionListener((event) -> {
            graphLabel.setVisible(false);
            exitButton.setVisible(false);
        });
    }

}
