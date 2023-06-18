package org.example;

import io.quickchart.QuickChart;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Charts extends JPanel {
    private ImageIcon icon;

    public Charts() {
        datedate();
        QuickChart chart = new QuickChart();
        chart.setWidth(490);
        chart.setHeight(460);
        chart.setBackgroundColor("white");
        chart.setConfig("""
                             
                                               {
                  type: 'bar',
                  data: {
                    labels: ['Day 1', 'Day 14', 'Day 1', 'Day 1', 'Day 1', 'Day 1', 'Day 10'],
                                
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

        chart.setConfig("      {\n" +
                "                  type: 'bar',\n" +
                "                  data: {\n" +
                                   datedate()+"\n" +
                "                                \n" +
                "                    datasets: [\n" +
                "                      {\n" +
                "                        type: 'line',\n" +
                "                        label: 'messages',\n" +
                "                        borderColor: 'rgb(54, 162, 235)',\n" +
                "                        borderWidth: 2,\n" +
                "                        fill: true,\n" +
                "                        data: [33, 26, 29, 89, 41, 70, 84],\n" +
                "                      },\n" +
                "                                 ],\n" +
                "                  },\n" +
                "                  options: {\n" +
                "                    title: {\n" +
                "                      display: true,\n" +
                "                      text: 'Request over Time',\n" +
                "                    },\n" +
                "                  },\n" +
                "                }      ");

        String path = chart.getUrl();
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
        JButton exitButton = new JButton("X");
        exitButton.setFont(new Font("Ariel", Font.BOLD, 12));
        exitButton.setBounds(0, 0, 50, 50);
        exitButton.setVisible(true);
        exitButton.setBackground(Color.white);
        this.add(exitButton);
        exitButton.addActionListener((event) -> {
            Window.changePanel(Window.getControlPanel(), this);
        });
        this.setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(icon.getImage(), 0, 0, this);
    }


    private String datedate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
        String s = "labels: [";
        Date currentDate = new Date();
        for (int i = 6; i >= 1; i--) {
            Date date = new Date(currentDate.getTime() - (1000 * 60 * 60 * 24 * i));
            s += "'" + formatter.format(date) + "',";
        }
        s += "'" +formatter.format(currentDate) +"'],";
        return s;
    }
}
