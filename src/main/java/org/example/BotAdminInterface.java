package org.example;
import javax.swing.*;
import java.util.ArrayList;

public class BotAdminInterface extends JFrame{
    private ArrayList<String> availableActivities;
    private WorldDataBot worldDataBot;
    ArrayList<User> users;
    ArrayList<String> activityHistory;

    public BotAdminInterface() {
        this.setSize(Constants.MANAGE_ACTIVITIES_WINDOW_WIDTH,Constants.MANAGE_ACTIVITIES_WINDOW_HEIGHT);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("WorldDataBot Admin Interface");

        // Manage Activities
        ManageActivitiesPanel manageActivitiesPanel = new ManageActivitiesPanel();
        this.add(manageActivitiesPanel);

        //


        this.setVisible(true);
    }

    public void setAvailableActivities(ArrayList<String> availableActivities) {
        this.availableActivities = availableActivities;
        worldDataBot.setAvailableActivities(availableActivities);
    }
}
