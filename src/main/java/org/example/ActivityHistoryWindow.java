package org.example;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ActivityHistoryWindow extends JFrame {
    private DefaultTableModel tableModel;
    public ActivityHistoryWindow () {
        this.setSize(600,700);
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("Activity History");

        Object[][] data = {
                { "John", 25, "USA" },
                { "Alice", 32, "Canada" },
                { "Bob", 19, "UK" },
                // Add more rows as needed
        };


        Object[] columnNames = { "Name", "Age", "Country" };

        JTable table = new JTable(data, columnNames);
        table.setFocusable(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 580, 680);
        this.add(scrollPane);

        this.setVisible(true);

    }

}
