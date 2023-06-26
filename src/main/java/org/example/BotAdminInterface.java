package org.example;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class BotAdminInterface extends JFrame{
    private ArrayList<String> availableActivities;
    private WorldDataBot worldDataBot;
    private ArrayList<User> users;
    private ArrayList<ArrayList<String>> activityHistory;
    public BotAdminInterface(WorldDataBot worldDataBot) {
        this.setSize(Constants.BOT_INTERFACE_WINDOW_WIDTH,Constants.BOT_INTERFACE_WINDOW_HEIGHT);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("WorldDataBot Admin Interface");

        this.users = new ArrayList<>();
        this.availableActivities = new ArrayList<>();
        this.activityHistory = new ArrayList<>();
        this.worldDataBot = worldDataBot;

        // Manage Activities
        ManageActivitiesPanel manageActivitiesPanel = new ManageActivitiesPanel(this);
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
            userStatisticsPanel.repaint();
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
        JButton activityGraphButton = new JButton("Daily Activity Graph");
        activityGraphButton.setFocusable(false);
        activityGraphButton.setBounds(Constants.BOT_INTERFACE_WINDOW_WIDTH/2+(Constants.BOT_INTERFACE_WINDOW_WIDTH/2-Constants.UPDATE_BUTTON_WIDTH)/2, Constants.BOT_INTERFACE_WINDOW_HEIGHT/2 + 9*Constants.MARGIN_FROM_TOP, Constants.UPDATE_BUTTON_WIDTH, Constants.UPDATE_BUTTON_HEIGHT);
        activityGraphButton.setFont(updateButtonFont);
        this.add(activityGraphButton);

        this.setVisible(true);
    }

    public void setAvailableActivities(ArrayList<String> availableActivities) {
        this.availableActivities = new ArrayList<>(availableActivities);
        this.worldDataBot.setAvailableActivities(availableActivities);
    }

    public void addActivityToHistoryList (ArrayList<String> newActivity) {
        if (this.activityHistory.size()==10) {
            this.activityHistory.remove(0);
        }
        this.activityHistory.add(newActivity);
    }

    public void addNewUser (User newUser) {
        boolean result = true;
        for (User user : this.users) {
            if (newUser.getUserName().equals(user.getUserName())) {
                result = false;
            }
        }
        if (result) {
            this.users.add(newUser);
        }
    }


    public void findUserAndUpdateTheRequests (String activity, long chatId) {
        for (User user : this.users) {
            if (user.equalsUserByChatId(chatId)) {
                user.updateRequests(activity);
            }
        }
    }

    public String getUserNameByChatId (long chatId) {
        String result = "";
        for (User user : this.users) {
            if (user.getChatId() == chatId) {
                result = user.getUserName();
            }
        }
        return result;
    }

    public HashMap<String, Integer> createRequestByDayMap () {
        HashMap<String, Integer> result = new HashMap<>();
        for (int i = 0; i < Constants.TIME_RANGES_STRING.length; i++) {
            result.put(Constants.TIME_RANGES_STRING[i], 0);
        }
        return result;
    }

}
