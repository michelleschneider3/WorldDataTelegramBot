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
    private HashMap<String, HashMap<String, Integer>> totalRequestByDayAndTime;
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
        this.totalRequestByDayAndTime = new HashMap<>();

        ManageActivitiesPanel manageActivitiesPanel = new ManageActivitiesPanel(this);
        this.add(manageActivitiesPanel);

        Font updateButtonFont = new Font("Comic Sans MS", Font.BOLD, Constants.DONE_BUTTON_SIZE);

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

        JButton activityHistoryButton = new JButton("Activity History");
        activityHistoryButton.setFocusable(false);
        activityHistoryButton.setBounds((Constants.BOT_INTERFACE_WINDOW_WIDTH/2-Constants.UPDATE_BUTTON_WIDTH)/2, Constants.BOT_INTERFACE_WINDOW_HEIGHT/2 + 9*Constants.MARGIN_FROM_TOP, Constants.UPDATE_BUTTON_WIDTH, Constants.UPDATE_BUTTON_HEIGHT);
        activityHistoryButton.setFont(updateButtonFont);
        this.add(activityHistoryButton);
        activityHistoryButton.addActionListener(e -> {
            new ActivityHistoryWindow(activityHistory);
        });

        JButton activityGraphButton = new JButton("Daily Activity Graph");
        activityGraphButton.setFocusable(false);
        activityGraphButton.setBounds(Constants.BOT_INTERFACE_WINDOW_WIDTH/2+(Constants.BOT_INTERFACE_WINDOW_WIDTH/2-Constants.UPDATE_BUTTON_WIDTH)/2, Constants.BOT_INTERFACE_WINDOW_HEIGHT/2 + 9*Constants.MARGIN_FROM_TOP, Constants.UPDATE_BUTTON_WIDTH, Constants.UPDATE_BUTTON_HEIGHT);
        activityGraphButton.setFont(updateButtonFont);
        this.add(activityGraphButton);
        activityGraphButton.addActionListener(e -> {
            String currentDate = currentDate();
            new RequestsGraphWindow(this.totalRequestByDayAndTime.get(currentDate));
        });

        this.setVisible(true);
    }

    private String currentDate () {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
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
        boolean userExists = this.users.stream()
                .anyMatch(user -> newUser.getUserName().equals(user.getUserName()));

        if (!userExists) {
            this.users.add(newUser);
        }
    }


    public void findUserAndUpdateTheRequests (String activity, long chatId) {
        this.users.stream()
                .filter(user -> user.equalsUserByChatId(chatId))
                .forEach(user -> user.updateRequests(activity));
    }

    public String getUserNameByChatId (long chatId) {
        return this.users.stream()
                .filter(user -> user.getChatId() == chatId)
                .map(User::getUserName)
                .findFirst()
                .orElse("");
    }

    public void addRequestToTotalRequestByDayAndTimeMap (String hour, String date) { //(HH:mm:ss,yyyy-MM-dd)
        int requestHour = Integer.parseInt(hour.substring(0,2));
        if (this.totalRequestByDayAndTime.containsKey(date)) {
            addToRequestByTime(requestHour, date);
        } else {
            this.totalRequestByDayAndTime.put(date,initializeRequestsByTime(requestHour));
        }

    }

    private HashMap<String, Integer> initializeRequestsByTime (int requestHour) {
        HashMap<String, Integer> requestsByTime = new HashMap<>();
        for (int i = 0; i < Constants.TIME_RANGES_STRING.length; i++) {
            if (requestHour>=Constants.TIME_RANGES_INTEGER[i][0] && requestHour<=Constants.TIME_RANGES_INTEGER[i][1]) {
                requestsByTime.put(Constants.TIME_RANGES_STRING[i], 1);
            } else {
                requestsByTime.put(Constants.TIME_RANGES_STRING[i], 0);
            }
        }
        return requestsByTime;
    }

    private void addToRequestByTime (int requestHour, String date) {
        HashMap<String, Integer> currentRequestByTime = this.totalRequestByDayAndTime.get(date);
        for (int i = 0; i < Constants.TIME_RANGES_STRING.length; i++) {
            if (requestHour>=Constants.TIME_RANGES_INTEGER[i][0] && requestHour<=Constants.TIME_RANGES_INTEGER[i][1]) {
                int value = currentRequestByTime.get(Constants.TIME_RANGES_STRING[i]);
                value++;
                currentRequestByTime.put(Constants.TIME_RANGES_STRING[i], value);
            }
        }
        this.totalRequestByDayAndTime.put(date, currentRequestByTime);
    }
}
