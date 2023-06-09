package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends JPanel {
    private final int BG_RED_AMOUNT = 111;
    private final int BG_GREEN_AMOUNT = 211;
    private final int BG_BLUE_AMOUNT = 255;
    private List<JRadioButton>buttonGroup;
    private Integer selectedCheckBox;
    private JButton userHistory;
    private JButton statistics;
    private JButton showGraph;
    private JLabel title;
    private JLabel instructions;

    public ControlPanel(){
        this.selectedCheckBox=0;
        this.setSize(Utils.WINDOW_SIZE,Utils.WINDOW_SIZE);
        this.setBackground(new Color(BG_RED_AMOUNT, BG_GREEN_AMOUNT, BG_BLUE_AMOUNT));
        this.setLayout(null);
        this.buttonGroup = new ArrayList<>();
        this.buttonGroup.add(createRadioButton(100,5,"jokes",UserChoice.JOKE));
        this.buttonGroup.add(createRadioButton(100,80,"exchange rate",UserChoice.EXCHANGE_RATE));
        this.buttonGroup.add(createRadioButton(100,155,"numbers",UserChoice.NUMBER));
        this.buttonGroup.add(createRadioButton(100,230,"quotes",UserChoice.QUOTES));
        this.buttonGroup.add(createRadioButton(100,305,"weather",UserChoice.WEATHER));
        for (JRadioButton radioButton:this.buttonGroup) {
            add(radioButton);
        }
        this.setVisible(true);
    }

    private  JRadioButton createRadioButton(int x, int y,String name,UserChoice userSelection){
        JRadioButton jRadioButton = new JRadioButton();
        jRadioButton.setLocation(x,y);
        jRadioButton.setText(name);
        jRadioButton.setFocusable(false);
        jRadioButton.setSize(150,100);
        jRadioButton.setBackground(null);
        jRadioButton.addActionListener( (event) -> {
            if(jRadioButton.isSelected()){
                if (this.selectedCheckBox<3){
                    this.selectedCheckBox++;
                }else {
                    jRadioButton.setSelected(false);
                }
            }else {
                this.selectedCheckBox-=1;
            }
        });
        return jRadioButton;
    }
}
