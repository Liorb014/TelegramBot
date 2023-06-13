package org.example;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends JPanel {

    private List<JCheckBox> checkboxList;
    private Integer selectedCheckBox;
    private JButton updateSelection;
    private JButton userHistory;
    private JButton statistics;
    private JButton showGraph;
    private MessagesBot bot;

    private final int BG_RED_AMOUNT = 111;
    private final int BG_GREEN_AMOUNT = 211;
    private final int BG_BLUE_AMOUNT = 255;

    private final int MAX_ALLOWED_CHOICES = 3;

    private final int CHECK_BOXES_MARGIN = 60;
    private final int CHECK_BOXES_LINE_X = 100;
    private final int CHECKBOX_WIDTH = 150;
    private final int CHECKBOX_HEIGHT = 20;

    private final int OPTIONS_START_POINT_Y = 120;
    private final int OTHER_OPTION_BOXES_MARGIN = 100;
    private final int OTHER_OPTION_X = 280;
    private final int OTHER_OPTION_Y = 95;

    private final int SUBTEXT_SIZE = 12;
    private final int INSTRUCTION_X = 68;
    private final int INSTRUCTION_Y = 78;

    private final int TITLE_SIZE = 12;
    private final int TITLE_X = 50;
    private final int TITLE_Y = 20;

    private final int BUTTONS_WIDTH = 110;
    private final int BUTTONS_HEIGHT = 30;

    private final String TITLE = "welcome to telegram bot management";
    private final String INSTRUCTIONS = "please choose your desire \n   API to use in your bot: ";
    private final String OTHER_OPTIONS = "other options: ";
    private final String JOKES = "jokes";
    private final String EXCHANGE_RATE = "exchange rate";
    private final String NUMBERS = "numbers";
    private final String QUOTES = "quotes";
    private final String WEATHER = "weather";

    public ControlPanel() {
        this.selectedCheckBox = 0;
        this.setSize(Utils.WINDOW_SIZE, Utils.WINDOW_SIZE);
        this.setBackground(new Color(BG_RED_AMOUNT, BG_GREEN_AMOUNT, BG_BLUE_AMOUNT));
        this.setLayout(null);

        this.bot = new MessagesBot();

        this.checkboxList = new ArrayList<>();
        this.checkboxList.add(createRadioButton(CHECK_BOXES_LINE_X, keepCheckBoxInSpace(0), this.JOKES, UserChoice.JOKE));
        this.checkboxList.add(createRadioButton(CHECK_BOXES_LINE_X, keepCheckBoxInSpace(1), this.NUMBERS, UserChoice.NUMBER));
        this.checkboxList.add(createRadioButton(CHECK_BOXES_LINE_X, keepCheckBoxInSpace(2), this.WEATHER, UserChoice.WEATHER));
        this.checkboxList.add(createRadioButton(CHECK_BOXES_LINE_X, keepCheckBoxInSpace(3), this.QUOTES, UserChoice.QUOTES));
        this.checkboxList.add(createRadioButton(CHECK_BOXES_LINE_X, keepCheckBoxInSpace(4), this.EXCHANGE_RATE, UserChoice.EXCHANGE_RATE));
        for (JCheckBox checkBox : this.checkboxList) {
            add(checkBox);
        }
        /*
        this.updateSelection = new JButton("update selection");
        this.updateSelection.setBounds(CHECK_BOXES_LINE_X,keepCheckBoxInSpace(5),110,20);
        this.updateSelection.addActionListener((e -> {
                checkboxList
                        .stream()
                        .filter(box -> box.isSelected())
                        .forEach(box -> MessagesBot.activeButtons.add(MessagesBot.getButtons(box.getText())));
        }));
        this.add(updateSelection);
*/
        this.userHistory = createOptionButton(this.OTHER_OPTION_X, keepOtherButtonsInSpace(0), this.BUTTONS_WIDTH, this.BUTTONS_HEIGHT, "user history");
        this.statistics = createOptionButton(this.OTHER_OPTION_X, keepOtherButtonsInSpace(1), this.BUTTONS_WIDTH, this.BUTTONS_HEIGHT, "statistics");
        this.showGraph = createOptionButton(this.OTHER_OPTION_X, keepOtherButtonsInSpace(2), this.BUTTONS_WIDTH, this.BUTTONS_HEIGHT, "graph");
        this.userHistory.addActionListener((e) -> {

        });
        this.statistics.addActionListener((e) -> {

        });
        this.showGraph.addActionListener((e) -> {

        });

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
            if (jCheckBox.isSelected()) {
                this.bot.addButton(userSelection);
                if (this.selectedCheckBox < MAX_ALLOWED_CHOICES) {
                    this.selectedCheckBox++;
                } else {
                    this.bot.removeButton(userSelection);
                    jCheckBox.setSelected(false);
                }
            } else {
                this.bot.removeButton(userSelection);
                this.selectedCheckBox--;
            }
        });
        return jCheckBox;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, this.TITLE_SIZE));
        drawString(g, this.TITLE, this.TITLE_X, this.TITLE_Y);

        g.setFont(new Font("Arial", Font.BOLD, this.SUBTEXT_SIZE));
        drawString(g, this.INSTRUCTIONS, this.INSTRUCTION_X, this.INSTRUCTION_Y);

        g.setFont(new Font("Arial", Font.BOLD, this.SUBTEXT_SIZE));
        drawString(g, this.OTHER_OPTIONS, this.OTHER_OPTION_X, this.OTHER_OPTION_Y);
    }

    private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    private int keepCheckBoxInSpace(int index) {
        return this.OPTIONS_START_POINT_Y + this.CHECK_BOXES_MARGIN * index;
    }

    private int keepOtherButtonsInSpace(int index) {
        return this.OPTIONS_START_POINT_Y + this.OTHER_OPTION_BOXES_MARGIN * index;
    }
//    public void addButton(UserChoice name){
//        MessagesBot.userChoice.stream().forEach(choice ->{
//
//        } );
//        if(name.equals(UserChoice.JOKE)){
//            InlineKeyboardButton jokeButton = new InlineKeyboardButton("joke");
//            jokeButton.setCallbackData("joke");
//            MessagesBot.buttons.add(jokeButton);
//        }
//    }
}
