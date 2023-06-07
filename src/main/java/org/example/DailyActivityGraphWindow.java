package org.example;
import javax.swing.*;
import javax.swing.text.Document;
import java.util.HashMap;
public class DailyActivityGraphWindow extends JFrame {
    private HashMap<String, Integer> requestCounts;
    public DailyActivityGraphWindow (HashMap<String, Integer> requestCounts) {
        this.requestCounts = requestCounts;

        this.setSize(700,500);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Daily Activity Graph");

        StringBuilder url = new StringBuilder("https://quickchart.io/chart?c={type:'bar',data:{labels:[");
        for (int i = 0; i < Constants.TIME_RANGES_INTEGER.length; i++) {
            url.append("'").append(Constants.TIME_RANGES_STRING[i]).append("'");
            if (i != Constants.TIME_RANGES_INTEGER.length-1) {
                url.append(",");
            } else {
                url.append("],datasets:[{label:'Requests',data:[");
            }
        }

        for (int i = 0; i < Constants.TIME_RANGES_INTEGER.length; i++) {
            url.append(requestCounts.get(i));
            if (i != Constants.TIME_RANGES_INTEGER.length-1) {
                url.append(",");
            } else {
                url.append("]}]}}");
            }
        }


        try {
            Document document = Jsoup.connect(url).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        this.setVisible(true);
    }

}
