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

    public Charts(MessagesBot bot) {
        super(bot);
        this.totalAmountOfRequest=0;
        QuickChart chart = new QuickChart();
        chart.setWidth(490);
        chart.setHeight(460);
        this.timeData = "labels: []";
        this.requestData = "data: []";
        chart.setBackgroundColor("white");
        new Thread(() -> {
            while (true) {
                System.out.print("");
                throughTime();
                requestAmount();

//                while (super.isOpened()) {

                chart.setConfig("      {\n" +
                        "                  type: 'line',\n" +
                        "                  data: {\n" +
                        this.timeData +",\n" +
                        "                                \n" +
                        "                    datasets: [\n" +
                        "                      {\n" +
                        "                        type: 'line',\n" +
                        "                        label: 'activities',\n" +
                        "                        borderColor: 'rgb(54, 162, 235)',\n" +
                        "                        borderWidth: 2,\n" +
                        "                        fill: true,\n" +
                        "                       "+this.requestData+ ",\n" +
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
        if (icon!= null){

            g.drawImage(icon.getImage(), 0, 0, this);
        }else System.out.println("null");
    }

    private void throughTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        Date currentDate = new Date();
        this.timeData=this.timeData.replace("]", ",'"+formatter.format(currentDate)+"']");
        this.timeData=  this.timeData.replace(timeData.charAt(9),' ');

        System.out.println(this.timeData);
    }
    private void requestAmount(){
        int temp = getAmountOfRequests();
        this.requestData = this.requestData.replace("]",",'"+(getAmountOfRequests()-totalAmountOfRequest)+"']");
        this.requestData =  this.requestData.replace(this.requestData.charAt(7),' ');
        totalAmountOfRequest=temp;
        System.out.println(requestData);
    }
    public int getAmountOfRequests() {
        return super.getBot().getUpdateList().size();
    }
}