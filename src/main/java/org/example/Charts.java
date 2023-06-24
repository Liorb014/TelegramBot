package org.example;

import io.quickchart.QuickChart;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Charts extends Panel {
    private ImageIcon icon;
    private String timeData;
    private String requestData;
    private int totalAmountOfRequest;
    private final int WIDTH_MARGIN = 10;
    private final int HEIGHT_MARGIN = 40;

    public Charts(MessagesBot bot) {
        super(bot);
        this.totalAmountOfRequest = 0;
        QuickChart chart = new QuickChart();
        chart.setWidth(WINDOW_SIZE - WIDTH_MARGIN);
        chart.setHeight(WINDOW_SIZE - HEIGHT_MARGIN);
        this.timeData = "labels: []";
        this.requestData = "data: []";
        chart.setBackgroundColor("white");
        new Thread(() -> {
            while (true) {
                System.out.print("");
                throughTime();
                requestAmount();
                setConfig(chart);
                String path = chart.getUrl();
                try {
                    icon = new ImageIcon(new URL(path));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                repaint();
                Utils.sleep(60);
            }
        }).start();
        exitButton(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(icon.getImage(), 0, 0, this);
    }

    private void throughTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        Date currentDate = new Date();
        this.timeData = this.timeData.replace("]", ",'" + formatter.format(currentDate) + "']");
        this.timeData = this.timeData.replace(timeData.charAt(9), ' ');
    }

    private void requestAmount() {
        int temp = getAmountOfRequests();
        this.requestData = this.requestData.replace("]", ",'" + (getAmountOfRequests() - totalAmountOfRequest) + "']");
        this.requestData = this.requestData.replace(this.requestData.charAt(7), ' ');
        totalAmountOfRequest = temp;
    }

    public int getAmountOfRequests() {
        return super.getBot().getUpdateList().size();
    }

    private void setConfig(QuickChart chart) {
        chart.setConfig("      {\n" +
                "                  type: 'line',\n" +
                "                  data: {\n" +
                this.timeData + ",\n" +
                "                                \n" +
                "                    datasets: [\n" +
                "                      {\n" +
                "                        type: 'line',\n" +
                "                        label: 'activities',\n" +
                "                        borderColor: 'rgb(54, 162, 235)',\n" +
                "                        borderWidth: 2,\n" +
                "                        fill: true,\n" +
                "                       " + this.requestData + ",\n" +
                "                      },\n" +
                "                                 ],\n" +
                "                  },\n" +
                "                  options: {\n" +
                "                    title: {\n" +
                "                      display: true,\n" +
                "                      text: 'Activities over Time',\n" +
                "                    },\n" +
                "                  },\n" +
                "                }      ");
    }
}