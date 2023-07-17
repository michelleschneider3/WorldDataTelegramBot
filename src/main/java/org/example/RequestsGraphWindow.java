package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class RequestsGraphWindow extends JFrame {

    private HashMap<String, Integer> requestsByTime;

    public RequestsGraphWindow(HashMap<String, Integer> requestsByTime) {
        this.setSize(Constants.REQUESTS_GRAPH_WINDOW_WIDTH,Constants.REQUESTS_GRAPH_WINDOW_HEIGHT);
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

        String url = Constants.INITIAL_URL_CHARTS;
        for (int i = 0; i < Constants.TIME_RANGES_STRING.length; i++) {
            if (i<Constants.TIME_RANGES_STRING.length-1) {
                url += "'" + Constants.TIME_RANGES_STRING[i] + "',";
            }
            else {
                url += "'" + Constants.TIME_RANGES_STRING[i]  + "'],datasets:[{label:'Requests',data:[";
            }
        }
        for (int i = 0; i < Constants.TIME_RANGES_STRING.length; i++) {
            if (i<Constants.TIME_RANGES_STRING.length-1) {
                url += this.requestsByTime.get(Constants.TIME_RANGES_STRING[i]) + ",";
            } else {
                url += this.requestsByTime.get(Constants.TIME_RANGES_STRING[i]) + "]}]}}";
            }
        }

        try {
            URL imageUrl = new URL(url);
            BufferedImage image = ImageIO.read(imageUrl);
            GraphImagePanel imagePanel = new GraphImagePanel(image);
            this.add(imagePanel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setVisible(true);
    }
}
