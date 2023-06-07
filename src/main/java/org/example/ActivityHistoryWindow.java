package org.example;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
public class ActivityHistoryWindow extends JFrame {
    private ArrayList<ArrayList<String>> activityHistory;
    public ActivityHistoryWindow (ArrayList<ArrayList<String>> activityHistory) {
        this.setSize(600,700);
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("Activity History");

        this.activityHistory = activityHistory;

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("User");
        tableModel.addColumn("Activity Type");
        tableModel.addColumn("Date");
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,0,600,700);
        this.getContentPane().add(scrollPane);

        for (ArrayList<String> activity : activityHistory) {
            String userName = activity.get(0);
            String activityType = activity.get(1);
            String time = activity.get(2);
            tableModel.addRow(new Object[]{userName, activity, time});
        }
        this.setVisible(true);
    }

}
