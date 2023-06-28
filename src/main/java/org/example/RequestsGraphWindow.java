package org.example;

import javax.swing.*;
import java.util.HashMap;

public class RequestsGraphWindow extends JFrame {

    private HashMap<String, Integer> requestsByTime;

    public RequestsGraphWindow(HashMap<String, Integer> requestsByTime) {
        this.setSize(750,550);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Daily Requests Graph");

        if (requestsByTime != null) {
            this.requestsByTime = requestsByTime;
        } else {
            this.requestsByTime = new HashMap<>();
            for (int i = 0; i < Constants.TIME_RANGES_STRING.length; i++) {
                this.requestsByTime.put(Constants.TIME_RANGES_STRING[i], 0);
            }
        }

        System.out.println(this.requestsByTime);

        String url = "https://quickchart.io/chart?c={type:'bar',data:{labels:[";
        for (int i = 0; i < Constants.TIME_RANGES_STRING.length; i++) {
            if (i<Constants.TIME_RANGES_STRING.length-1) {
                url += "'" + Constants.TIME_RANGES_STRING[i] + "',";
            }
            else {
                url += Constants.TIME_RANGES_STRING[i]  + "'],datasets:[{label:'Requests',data:[";
            }
        }
        for (int i = 0; i < Constants.TIME_RANGES_STRING.length; i++) {
            if (i<Constants.TIME_RANGES_STRING.length-1) {
                url += this.requestsByTime.get(Constants.TIME_RANGES_STRING[i]) + ",";
            } else {
                url += this.requestsByTime.get(Constants.TIME_RANGES_STRING[i]) + "]}]}}";
            }
        }

        this.setVisible(true);
    }

}
