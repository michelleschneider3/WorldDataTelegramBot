package org.example;
import javax.swing.*;
import java.util.ArrayList;

public class BotAdminInterface extends JFrame{
    private ArrayList<String> activities;
    public BotAdminInterface() {
        this.setSize(700,700);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("WorldDataBot Admin Interface");
        this.setVisible(true);
    }

    public void setActivities(ArrayList<String> activities) {
        this.activities = activities;
    }
}
