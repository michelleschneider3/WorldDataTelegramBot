package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class BotAdminInterface extends JFrame{
    private ArrayList<String> availableActivities;
    private WorldDataBot worldDataBot;
    private ArrayList<User> users;
    private ArrayList<ArrayList<String>> activityHistory;
    private HashMap<Integer, Integer> requestCounts;

    public BotAdminInterface() {
        this.setSize(Constants.BOT_INTERFACE_WINDOW_WIDTH,Constants.BOT_INTERFACE_WINDOW_HEIGHT);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("WorldDataBot Admin Interface");

        this.users = new ArrayList<>();
        this.activityHistory = new ArrayList<>();
        this.requestCounts = new HashMap<>();

        // Manage Activities
        ManageActivitiesPanel manageActivitiesPanel = new ManageActivitiesPanel();
        this.add(manageActivitiesPanel);

        Font updateButtonFont = new Font("Comic Sans MS", Font.BOLD, Constants.DONE_BUTTON_SIZE);

        // User Statistics
        JButton updateStatisticsButton = new JButton("Update Statistics");
        updateStatisticsButton.setFocusable(false);
        updateStatisticsButton.setBounds(Constants.BOT_INTERFACE_WINDOW_WIDTH /2+(Constants.BOT_INTERFACE_WINDOW_WIDTH /2-Constants.UPDATE_BUTTON_WIDTH)/2,Constants.BOT_INTERFACE_WINDOW_HEIGHT /2-2*Constants.UPDATE_BUTTON_HEIGHT, Constants.UPDATE_BUTTON_WIDTH, Constants.UPDATE_BUTTON_HEIGHT);
        updateStatisticsButton.setFont(updateButtonFont);
        this.add(updateStatisticsButton);
        updateStatisticsButton.addActionListener(e -> {
            UserStatisticsPanel userStatisticsPanel = new UserStatisticsPanel(users);
            this.add(userStatisticsPanel);
            this.repaint();
        });

        // Activity History
        JButton activityHistoryButton = new JButton("Activity History");
        activityHistoryButton.setFocusable(false);
        activityHistoryButton.setBounds((Constants.BOT_INTERFACE_WINDOW_WIDTH/2-Constants.UPDATE_BUTTON_WIDTH)/2, Constants.BOT_INTERFACE_WINDOW_HEIGHT/2 + 9*Constants.MARGIN_FROM_TOP, Constants.UPDATE_BUTTON_WIDTH, Constants.UPDATE_BUTTON_HEIGHT);
        activityHistoryButton.setFont(updateButtonFont);
        this.add(activityHistoryButton);
        activityHistoryButton.addActionListener(e -> {
            new ActivityHistoryWindow(activityHistory);
        });


        // Requests Graph



        this.setVisible(true);
    }

    public void setAvailableActivities(ArrayList<String> availableActivities) {
        this.availableActivities = availableActivities;
        worldDataBot.setAvailableActivities(availableActivities);
    }

    public void addActivityToHistoryList (ArrayList<String> newActivity) {
        if (this.activityHistory.size()==10) {
            this.activityHistory.remove(0);
        }
        this.activityHistory.add(newActivity);
    }

//    public void addRequestToRequestsCount (String ) {
//
//
//    }
}
