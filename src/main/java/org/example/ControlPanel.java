package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends Panel {

    private List<JCheckBox> checkboxList;
    private Integer selectedCheckBox;
    private JButton userHistory;
    private JButton statistics;
    private JButton showGraph;

    private final int MAX_ALLOWED_CHOICES = 3;

    private final int CHECK_BOXES_MARGIN = 60;
    private final int CHECK_BOXES_LINE_X = 100;
    private final int CHECKBOX_WIDTH = 150;
    private final int CHECKBOX_HEIGHT = 20;

    private final int OPTIONS_START_POINT_Y = 120;
    private final int OTHER_OPTION_BOXES_MARGIN = 100;
    private final int OTHER_OPTION_X = 280;
    private final int OTHER_OPTION_Y = 85;

    private final int SUBTEXT_SIZE = 12;
    private final int INSTRUCTION_X = 68;
    private final int INSTRUCTION_Y = 78;

    private final int TITLE_SIZE = 22;
    private final int TITLE_X = 50;
    private final int TITLE_Y = 20;

    private final int BUTTONS_WIDTH = 130;
    private final int BUTTONS_HEIGHT = 30;

    private final String TITLE = "welcome to telegram bot management";
    private final String INSTRUCTIONS = "please choose your desire \n   API to use in your bot: ";
    private final String OTHER_OPTIONS = "other options: ";
    private final String JOKES = "jokes";
    private final String UNIVERSITIES = "universities";
    private final String NUMBERS = "numbers";
    private final String QUOTES = "quotes";
    private final String CATS_FACTS = "cats facts";

    private final String USER_HISTORY = "Users History";
    private final String STATISTICS = "Statistics";
    private final String GRAPH = "Activity Graph ";

    public ControlPanel(MessagesBot bot) {
        super(bot);
        this.selectedCheckBox = 0;
        this.checkboxList = new ArrayList<>();
        this.checkboxList.add(createRadioButton(CHECK_BOXES_LINE_X, keepCheckBoxInSpace(0), this.JOKES, UserChoice.JOKE));
        this.checkboxList.add(createRadioButton(CHECK_BOXES_LINE_X, keepCheckBoxInSpace(1), this.NUMBERS, UserChoice.NUMBER));
        this.checkboxList.add(createRadioButton(CHECK_BOXES_LINE_X, keepCheckBoxInSpace(2), this.CATS_FACTS, UserChoice.CATS_FACTS));
        this.checkboxList.add(createRadioButton(CHECK_BOXES_LINE_X, keepCheckBoxInSpace(3), this.QUOTES, UserChoice.QUOTES));
        this.checkboxList.add(createRadioButton(CHECK_BOXES_LINE_X, keepCheckBoxInSpace(4), this.UNIVERSITIES, UserChoice.UNIVERSITIES));
        for (JCheckBox checkBox : this.checkboxList) {
            add(checkBox);
        }

        this.userHistory = createOptionButton(this.OTHER_OPTION_X, keepOtherButtonsInSpace(0), this.BUTTONS_WIDTH, this.BUTTONS_HEIGHT, USER_HISTORY);
        this.userHistory.addActionListener((e) -> {Window.changePanel(Window.getUsersHistory(), this);});

        this.statistics = createOptionButton(this.OTHER_OPTION_X, keepOtherButtonsInSpace(1), this.BUTTONS_WIDTH, this.BUTTONS_HEIGHT, STATISTICS);
        this.statistics.addActionListener((e) -> {Window.changePanel(Window.getStatistics(), this);});

        this.showGraph = createOptionButton(this.OTHER_OPTION_X, keepOtherButtonsInSpace(2), this.BUTTONS_WIDTH, this.BUTTONS_HEIGHT, GRAPH);
        this.showGraph.addActionListener((e) -> {Window.changePanel(Window.getChars(), this);});
        super.setOpened(true);
        this.setVisible(true);
    }

    private JButton createOptionButton(int x, int y, int width, int height, String text) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        this.add(button);
        return button;
    }

    private JCheckBox createRadioButton(int x, int y, String name, UserChoice userSelection) {
        JCheckBox jCheckBox = new JCheckBox();
        jCheckBox.setText(name);
        jCheckBox.setFocusable(false);
        jCheckBox.setBorderPainted(false);
        jCheckBox.setBounds(x, y, CHECKBOX_WIDTH, CHECKBOX_HEIGHT);
        jCheckBox.setBackground(null);
        jCheckBox.addActionListener((event) -> {
            checkIfManagerOptionsIsUpToThree(jCheckBox,userSelection);
        });
        return jCheckBox;
    }

    private void checkIfManagerOptionsIsUpToThree(JCheckBox checkBox,UserChoice userSelection){
        if (checkBox.isSelected()) {
            super.getBot().addButton(userSelection);
            if (this.selectedCheckBox < MAX_ALLOWED_CHOICES) {
                this.selectedCheckBox++;
            } else {
                super.getBot().removeButton(userSelection);
                checkBox.setSelected(false);
            }
        } else {
            super.getBot().removeButton(userSelection);
            this.selectedCheckBox--;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font(Panel.ALL_TEXTS_STILE, Font.BOLD, this.TITLE_SIZE));
        drawString(g, this.TITLE, this.TITLE_X, this.TITLE_Y);

        g.setFont(new Font(Panel.ALL_TEXTS_STILE, Font.BOLD, this.SUBTEXT_SIZE));
        drawString(g, this.INSTRUCTIONS, this.INSTRUCTION_X, this.INSTRUCTION_Y);

        g.setFont(new Font(Panel.ALL_TEXTS_STILE, Font.BOLD, this.SUBTEXT_SIZE));
        drawString(g, this.OTHER_OPTIONS, this.OTHER_OPTION_X, this.OTHER_OPTION_Y);
    }

    private int keepCheckBoxInSpace(int index) {
        return this.OPTIONS_START_POINT_Y + this.CHECK_BOXES_MARGIN * index;
    }

    private int keepOtherButtonsInSpace(int index) {
        return this.OPTIONS_START_POINT_Y + this.OTHER_OPTION_BOXES_MARGIN * index;
    }


}
