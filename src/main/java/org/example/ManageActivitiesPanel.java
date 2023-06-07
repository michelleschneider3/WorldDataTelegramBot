package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class ManageActivitiesPanel extends JPanel {
    private BotAdminInterface botAdminInterface;
    private int selectedActivities;
    private JLabel invalidChecksLabel;
    private ArrayList<String> availableActivities;
    public ManageActivitiesPanel(BotAdminInterface botAdminInterface) {
        this.setBounds(0,0,Constants.BOT_INTERFACE_WINDOW_WIDTH /2, Constants.BOT_INTERFACE_WINDOW_HEIGHT /2);
        this.setLayout(null);

        this.selectedActivities = 0;
        availableActivities = new ArrayList<>();
        this.botAdminInterface = botAdminInterface;

        JLabel directiveLabel = new JLabel("Choose at most 3 activities: ");
        directiveLabel.setBounds(Constants.MARGIN_FROM_LEFT,0,Constants.TITLE_LABEL_WIDTH, Constants.TITLE_LABEL_HEIGHT);
        Font directiveLabelFont = new Font("Comic Sans MS", Font.BOLD, Constants.TITLE_LABEL_SIZE);
        directiveLabel.setFont(directiveLabelFont);
        this.add(directiveLabel);

        Font activityFont = new Font("Comic Sans MS", Font.PLAIN, Constants.REGULAR_LABEL_SIZE);

        JCheckBox activity1CheckBox = new JCheckBox("Open Weather API");
        activity1CheckBox.setFocusable(false);
        activity1CheckBox.setBounds(Constants.MARGIN_FROM_LEFT,directiveLabel.getY() + Constants.TITLE_LABEL_HEIGHT + Constants.MARGIN_FROM_TOP,Constants.CHECK_BOX_AND_LABEL_WIDTH,Constants.CHECK_BOX_AND_LABEL_HEIGHT);
        activity1CheckBox.setFont(activityFont);
        this.add(activity1CheckBox);
        activity1CheckBox.addActionListener(a -> updateSelectedActivities(activity1CheckBox.isSelected(), activity1CheckBox));

        JCheckBox activity2CheckBox = new JCheckBox("News API");
        activity2CheckBox.setFocusable(false);
        activity2CheckBox.setBounds(Constants.MARGIN_FROM_LEFT,activity1CheckBox.getY() + Constants.CHECK_BOX_AND_LABEL_HEIGHT + Constants.MARGIN_FROM_TOP,Constants.CHECK_BOX_AND_LABEL_WIDTH,Constants.CHECK_BOX_AND_LABEL_HEIGHT);
        activity2CheckBox.setFont(activityFont);
        this.add(activity2CheckBox);
        activity2CheckBox.addActionListener(b -> updateSelectedActivities(activity2CheckBox.isSelected(), activity2CheckBox));

        JCheckBox activity3CheckBox = new JCheckBox("NASA API");
        activity3CheckBox.setFocusable(false);
        activity3CheckBox.setBounds(Constants.MARGIN_FROM_LEFT,activity2CheckBox.getY() + Constants.CHECK_BOX_AND_LABEL_HEIGHT + Constants.MARGIN_FROM_TOP,Constants.CHECK_BOX_AND_LABEL_WIDTH,Constants.CHECK_BOX_AND_LABEL_HEIGHT);
        activity3CheckBox.setFont(activityFont);
        this.add(activity3CheckBox);
        activity3CheckBox.addActionListener(c -> updateSelectedActivities(activity3CheckBox.isSelected(), activity3CheckBox));

        JCheckBox activity4CheckBox = new JCheckBox("Rest Countries API");
        activity4CheckBox.setFocusable(false);
        activity4CheckBox.setBounds(Constants.MARGIN_FROM_LEFT,activity3CheckBox.getY() + Constants.CHECK_BOX_AND_LABEL_HEIGHT + Constants.MARGIN_FROM_TOP,Constants.CHECK_BOX_AND_LABEL_WIDTH,Constants.CHECK_BOX_AND_LABEL_HEIGHT);
        activity4CheckBox.setFont(activityFont);
        this.add(activity4CheckBox);
        activity4CheckBox.addActionListener(d -> updateSelectedActivities(activity4CheckBox.isSelected(), activity4CheckBox));

        JCheckBox activity5CheckBox = new JCheckBox("Covid-19 Data API");
        activity5CheckBox.setFocusable(false);
        activity5CheckBox.setBounds(Constants.MARGIN_FROM_LEFT,activity4CheckBox.getY() + Constants.CHECK_BOX_AND_LABEL_HEIGHT + Constants.MARGIN_FROM_TOP,Constants.CHECK_BOX_AND_LABEL_WIDTH,Constants.CHECK_BOX_AND_LABEL_HEIGHT);
        activity5CheckBox.setFont(activityFont);
        this.add(activity5CheckBox);
        activity5CheckBox.addActionListener(e -> updateSelectedActivities(activity5CheckBox.isSelected(), activity5CheckBox));

        JButton updateActivitiesButton = new JButton("Update Activities");
        updateActivitiesButton.setFocusable(false);
        updateActivitiesButton.setBounds((Constants.BOT_INTERFACE_WINDOW_WIDTH /2-Constants.UPDATE_BUTTON_WIDTH)/2, Constants.BOT_INTERFACE_WINDOW_HEIGHT/2-2*Constants.UPDATE_BUTTON_HEIGHT, Constants.UPDATE_BUTTON_WIDTH, Constants.UPDATE_BUTTON_HEIGHT);
        Font updateActivitiesButtonFont = new Font("Comic Sans MS", Font.BOLD, Constants.DONE_BUTTON_SIZE);
        updateActivitiesButton.setFont(updateActivitiesButtonFont);
        this.add(updateActivitiesButton);
        updateActivitiesButton.addActionListener(e -> {
            if (checkActivitiesFinalChecks()) {
                invalidChecksLabel.setText("");
                this.botAdminInterface.setAvailableActivities(this.availableActivities);
            }
        });

        invalidChecksLabel = new JLabel("");
        invalidChecksLabel.setBounds((Constants.BOT_INTERFACE_WINDOW_WIDTH /2-Constants.INVALID_FINAL_CHECKS_LABEL_WIDTH)/2, updateActivitiesButton.getY() + Constants.UPDATE_BUTTON_HEIGHT + Constants.MARGIN_FROM_TOP, Constants.INVALID_FINAL_CHECKS_LABEL_WIDTH, Constants.INVALID_FINAL_CHECKS_LABEL_HEIGHT);
        Font invalidChecksFont = new Font("Comic Sans MS", Font.BOLD, Constants.INVALID_CHECKS_LABEL_SIZE);
        invalidChecksLabel.setFont(invalidChecksFont);
        this.add(invalidChecksLabel);

        this.setVisible(true);
    }

    private void updateSelectedActivities(boolean isChecked, JCheckBox checkBox) {
        String newActivity = checkBox.getText();
        if (isChecked) {
            this.selectedActivities++;
            if (!availableActivities.contains(newActivity)) {
                availableActivities.add(newActivity);
            }
        } else {
            this.selectedActivities--;
            availableActivities.remove(newActivity);
        }
    }

    private boolean checkActivitiesFinalChecks() {
        boolean result = false;
        if (this.selectedActivities<=3)  {
            result = true;
        } else {
            this.invalidChecksLabel.setText("You chose too much options");
        }
        return result;
    }

}
