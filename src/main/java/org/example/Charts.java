package org.example;

import io.quickchart.QuickChart;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Charts extends JPanel {
    private ImageIcon icon;
    public Charts()  {
        QuickChart chart = new QuickChart();
        chart.setWidth(490);
        chart.setHeight(460);
        chart.setBackgroundColor("white");

        chart.setConfig("""
                             
                                               {
                  type: 'bar',
                  data: {
                    labels: ['Day 1', 'Day 14', 'Day 1', 'Day 1', 'Day 1', 'Day 1', 'Day 1'],
                                
                    datasets: [
                      {
                        type: 'line',
                        label: 'messages',
                        borderColor: 'rgb(54, 162, 235)',
                        borderWidth: 2,
                        fill: true,
                        data: [33, 26, 29, 89, 41, 70, 84],
                      },
                                 ],
                  },
                  options: {
                    title: {
                      display: true,
                      text: 'Reuest over Time',
                    },
                  },
                }
                """);

        String path =chart.getUrl();
        try {
             icon = new ImageIcon(new URL(path));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        this.setSize(Utils.WINDOW_SIZE, Utils.WINDOW_SIZE);
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.setOpaque(true);
        this.setDoubleBuffered(true);
        JLabel graphLabel = new JLabel(icon);
        graphLabel.setBounds(0, 0, Utils.WINDOW_SIZE, Utils.WINDOW_SIZE);
        graphLabel.setVisible(true);
      //  this.add(graphLabel);
        JButton exitButton = new JButton("X");
        exitButton.setFont(new Font("Ariel", Font.BOLD , 12));
        exitButton.setBounds(0, 0, 50, 50);
        exitButton.setVisible(true);
        exitButton.setBackground(Color.white);
        this.add(exitButton);
        exitButton.addActionListener((event) -> {
           Window.changePanel(Window.getControlPanel(),this);
        });
        this.setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(icon.getImage(),0,0,this);
    }
}
