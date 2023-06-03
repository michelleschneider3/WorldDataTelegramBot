package org.example;
import javax.swing.*;
import java.util.ArrayList;

public class BotAdminInterface extends JFrame{
    private ArrayList<String> activities;
    public BotAdminInterface() {
        this.setSize(500,500);
        this.setVisible(true);
    }

    public void setActivities(ArrayList<String> activities) {
        this.activities = activities;
    }
}
