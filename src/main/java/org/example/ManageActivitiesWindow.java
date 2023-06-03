package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class ManageActivitiesWindow extends JFrame {
    private BotAdminInterface botAdminInterface;
    private int selectedActivities;
    private JLabel invalidChecksLabel;
    private ArrayList<String> activities;
    public ManageActivitiesWindow () {
        this.setSize(Constants.MANAGE_ACTIVITIES_WINDOW_WIDTH,Constants.MANAGE_ACTIVITIES_WINDOW_HEIGHT);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("WorldDataBot Admin Interface");

        selectedActivities = 0;
        activities = new ArrayList<>();

        JLabel directiveLabel = new JLabel("Choose at most 3 activities: ");
        directiveLabel.setBounds(Constants.MARGIN_FROM_LEFT,0,Constants.DIRECTIVE_LABEL_WIDTH, Constants.DIRECTIVE_LABEL_HEIGHT);
        Font directiveLabelFont = new Font("Comic Sans MS", Font.BOLD, Constants.DIRECTIVE_LABEL_SIZE);
        directiveLabel.setFont(directiveLabelFont);
        this.add(directiveLabel);

        Font activityFont = new Font("Comic Sans MS", Font.PLAIN, Constants.ACTIVITY_LABEL_SIZE);

        JCheckBox activity1CheckBox = new JCheckBox("Open Weather API");
        activity1CheckBox.setFocusable(false);
        activity1CheckBox.setBounds(Constants.MARGIN_FROM_LEFT,directiveLabel.getY() + Constants.DIRECTIVE_LABEL_HEIGHT + Constants.MARGIN_FROM_TOP,Constants.CHECK_BOX_WIDTH,Constants.CHECK_BOX_HEIGHT);
        activity1CheckBox.setFont(activityFont);
        this.add(activity1CheckBox);
        activity1CheckBox.addActionListener(a -> updateSelectedActivities(activity1CheckBox.isSelected(), activity1CheckBox));

        JCheckBox activity2CheckBox = new JCheckBox("News API");
        activity2CheckBox.setFocusable(false);
        activity2CheckBox.setBounds(Constants.MARGIN_FROM_LEFT,activity1CheckBox.getY() + Constants.CHECK_BOX_HEIGHT + Constants.MARGIN_FROM_TOP,Constants.CHECK_BOX_WIDTH,Constants.CHECK_BOX_HEIGHT);
        activity2CheckBox.setFont(activityFont);
        this.add(activity2CheckBox);
        activity2CheckBox.addActionListener(b -> updateSelectedActivities(activity2CheckBox.isSelected(), activity2CheckBox));

        JCheckBox activity3CheckBox = new JCheckBox("NASA API");
        activity3CheckBox.setFocusable(false);
        activity3CheckBox.setBounds(Constants.MARGIN_FROM_LEFT,activity2CheckBox.getY() + Constants.CHECK_BOX_HEIGHT + Constants.MARGIN_FROM_TOP,Constants.CHECK_BOX_WIDTH,Constants.CHECK_BOX_HEIGHT);
        activity3CheckBox.setFont(activityFont);
        this.add(activity3CheckBox);
        activity3CheckBox.addActionListener(c -> updateSelectedActivities(activity3CheckBox.isSelected(), activity3CheckBox));

        JCheckBox activity4CheckBox = new JCheckBox("Rest Countries API");
        activity4CheckBox.setFocusable(false);
        activity4CheckBox.setBounds(Constants.MARGIN_FROM_LEFT,activity3CheckBox.getY() + Constants.CHECK_BOX_HEIGHT + Constants.MARGIN_FROM_TOP,Constants.CHECK_BOX_WIDTH,Constants.CHECK_BOX_HEIGHT);
        activity4CheckBox.setFont(activityFont);
        this.add(activity4CheckBox);
        activity4CheckBox.addActionListener(d -> updateSelectedActivities(activity4CheckBox.isSelected(), activity4CheckBox));

        JCheckBox activity5CheckBox = new JCheckBox("Covid-19 Data API");
        activity5CheckBox.setFocusable(false);
        activity5CheckBox.setBounds(Constants.MARGIN_FROM_LEFT,activity4CheckBox.getY() + Constants.CHECK_BOX_HEIGHT + Constants.MARGIN_FROM_TOP,Constants.CHECK_BOX_WIDTH,Constants.CHECK_BOX_HEIGHT);
        activity5CheckBox.setFont(activityFont);
        this.add(activity5CheckBox);
        activity5CheckBox.addActionListener(e -> updateSelectedActivities(activity5CheckBox.isSelected(), activity5CheckBox));

        JButton doneButton = new JButton("Done");
        doneButton.setFocusable(false);
        doneButton.setBounds((Constants.MANAGE_ACTIVITIES_WINDOW_WIDTH-Constants.DONE_BUTTON_WIDTH)/2, activity5CheckBox.getY() + Constants.CHECK_BOX_HEIGHT + Constants.MARGIN_FROM_TOP, Constants.DONE_BUTTON_WIDTH, Constants.DONE_BUTTON_HEIGHT);
        Font doneButtonFont = new Font("Comic Sans MS", Font.BOLD, 14);
        doneButton.setFont(doneButtonFont);
        this.add(doneButton);
        doneButton.addActionListener(e -> {
            if (checkActivitiesFinalChecks()) {
                invalidChecksLabel.setText("");
                this.botAdminInterface = new BotAdminInterface();
                this.botAdminInterface.setActivities(this.activities);
            }
        });

        invalidChecksLabel = new JLabel("");
        invalidChecksLabel.setBounds((Constants.MANAGE_ACTIVITIES_WINDOW_WIDTH-Constants.INVALID_FINAL_CHECKS_LABEL_WIDTH)/2, doneButton.getY() + Constants.DONE_BUTTON_HEIGHT + Constants.MARGIN_FROM_TOP, Constants.INVALID_FINAL_CHECKS_LABEL_WIDTH, Constants.INVALID_FINAL_CHECKS_LABEL_HEIGHT);
        Font invalidChecksFont = new Font("Comic Sans MS", Font.BOLD, Constants.INVALID_CHECKS_LABEL_SIZE);
        invalidChecksLabel.setFont(invalidChecksFont);
        this.add(invalidChecksLabel);

        this.setVisible(true);
    }

    private void updateSelectedActivities(boolean isChecked, JCheckBox checkBox) {
        String newActivity = checkBox.getText();
        if (isChecked) {
            this.selectedActivities++;
            if (!activities.contains(newActivity)) {
                activities.add(newActivity);
            }
        } else {
            this.selectedActivities--;
            activities.remove(newActivity);
        }
    }

    private boolean checkActivitiesFinalChecks() {
        boolean result = false;
        if (this.selectedActivities<=3 && this.selectedActivities!=0)  {
            result = true;
        } else {
            if (selectedActivities==0) {
                this.invalidChecksLabel.setText("You did not chose any options");
            } else {
                this.invalidChecksLabel.setText("You chose too much options");
            }
        }
        return result;
    }

}
