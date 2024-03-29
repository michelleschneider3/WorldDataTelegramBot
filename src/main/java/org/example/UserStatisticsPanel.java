package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class UserStatisticsPanel extends JPanel {
    private int totalRequests;
    private int totalUsers;
    private String mostActiveUser;
    private String mostPopularActivity;
    ArrayList<User> users;
    public UserStatisticsPanel (ArrayList<User> users) {
        this.users = users;

        if (users != null) {
            this.totalRequests = calculateTotalRequests();
            this.totalUsers = calculateTotalUsers();
            this.mostActiveUser = findMostActiveUser();
            this.mostPopularActivity = findMostPopularActivity();
        } else {
            this.totalRequests = 0;
            this.totalUsers = 0;
            this.mostActiveUser = "";
            this.mostPopularActivity = "";
        }

        this.setBounds(Constants.BOT_INTERFACE_WINDOW_WIDTH/2,0,Constants.BOT_INTERFACE_WINDOW_WIDTH/2, Constants.BOT_INTERFACE_WINDOW_HEIGHT/2-Constants.UPDATE_BUTTON_HEIGHT*2);
        this.setLayout(null);

        Font labelFont = new Font("Comic Sans MS", Font.PLAIN, Constants.REGULAR_LABEL_SIZE);
        Font titleLabelFont = new Font("Comic Sans MS", Font.BOLD, Constants.TITLE_LABEL_SIZE);

        JLabel statisticsTitleLabel = new JLabel("User Statistics:");
        statisticsTitleLabel.setBounds(Constants.MARGIN_FROM_LEFT,Constants.MARGIN_FROM_TOP,Constants.TITLE_LABEL_WIDTH, Constants.TITLE_LABEL_HEIGHT);
        statisticsTitleLabel.setFont(titleLabelFont);
        this.add(statisticsTitleLabel);

        JLabel totalRequestsLabel = new JLabel("Total Requests: " + this.totalRequests);
        totalRequestsLabel.setBounds(Constants.MARGIN_FROM_LEFT,statisticsTitleLabel.getY() + Constants.TITLE_LABEL_HEIGHT + Constants.MARGIN_FROM_TOP,Constants.CHECK_BOX_AND_LABEL_WIDTH,Constants.CHECK_BOX_AND_LABEL_HEIGHT);
        totalRequestsLabel.setFont(labelFont);
        this.add(totalRequestsLabel);

        JLabel totalUsersLabel = new JLabel("Total Users: " + this.totalUsers);
        totalUsersLabel.setBounds(Constants.MARGIN_FROM_LEFT,totalRequestsLabel.getY() + Constants.CHECK_BOX_AND_LABEL_HEIGHT + Constants.MARGIN_FROM_TOP,Constants.CHECK_BOX_AND_LABEL_WIDTH,Constants.CHECK_BOX_AND_LABEL_HEIGHT);
        totalUsersLabel.setFont(labelFont);
        this.add(totalUsersLabel);

        JLabel mostActiveUserLabel = new JLabel("Most Active User: " + this.mostActiveUser);
        mostActiveUserLabel.setBounds(Constants.MARGIN_FROM_LEFT,totalUsersLabel.getY() + Constants.CHECK_BOX_AND_LABEL_HEIGHT + Constants.MARGIN_FROM_TOP,Constants.CHECK_BOX_AND_LABEL_WIDTH,Constants.CHECK_BOX_AND_LABEL_HEIGHT);
        mostActiveUserLabel.setFont(labelFont);
        this.add(mostActiveUserLabel);

        JLabel mostPopularActivityLabel = new JLabel("Most Popular Activity: " + this.mostPopularActivity);
        mostPopularActivityLabel.setBounds(Constants.MARGIN_FROM_LEFT,mostActiveUserLabel.getY() + Constants.CHECK_BOX_AND_LABEL_HEIGHT + Constants.MARGIN_FROM_TOP,Constants.CHECK_BOX_AND_LABEL_WIDTH,Constants.CHECK_BOX_AND_LABEL_HEIGHT);
        mostPopularActivityLabel.setFont(labelFont);
        this.add(mostPopularActivityLabel);

        this.setVisible(true);
    }

    private int calculateTotalRequests () {
        return this.users.stream()
                .flatMap(user -> user.getRequests().entrySet().stream())
                .filter(entry -> Arrays.asList(Constants.ACTIVITIES).contains(entry.getKey()))
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    private int calculateTotalUsers () {
        return this.users.size();
    }

    private String findMostActiveUser () {
        return users.stream()
                .max(Comparator.comparingInt(user -> calculateTotalRequestsForUser(user)))
                .map(User::getUserName)
                .orElse("");
    }

    private int calculateTotalRequestsForUser(User user) {
        return Arrays.stream(Constants.ACTIVITIES)
                .mapToInt(activity -> user.getRequests().getOrDefault(activity, 0))
                .sum();
    }

    private String findMostPopularActivity () {
        if (users.isEmpty()) {
            return "";
        }

        return Arrays.stream(Constants.ACTIVITIES)
                .max(Comparator.comparingInt(activity -> calculateTotalRequestsForActivity(activity)))
                .orElse("");
    }
    private int calculateTotalRequestsForActivity(String activity) {
        return users.stream()
                .mapToInt(user -> user.getRequests().getOrDefault(activity, 0))
                .sum();
    }

}
