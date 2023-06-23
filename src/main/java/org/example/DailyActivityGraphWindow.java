//package org.example;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import javax.swing.*;
//import java.net.URL;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//
//public class DailyActivityGraphWindow extends JFrame {
//    private HashMap<LocalDateTime,HashMap<String, Integer>> requestCounts;
//    public DailyActivityGraphWindow (HashMap<LocalDateTime,HashMap<String, Integer>> requestCounts) {
//        LocalDate currentDate = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        requestCounts = new HashMap<>();
//        HashMap<String, Integer> result = new HashMap<>();
//        result.put("05:00-10:00", )
//        this.requestCounts.put(currentDate,);
//
//        this.setSize(700,500);
//        this.setLayout(null);
//        this.setLocationRelativeTo(null);
//        this.setResizable(false);
//        this.setTitle("Daily Activity Graph");
//
//        String url = new String("https://quickchart.io/chart?c={type:'bar',data:{labels:[");
//        for (int i = 0; i < Constants.TIME_RANGES_INTEGER.length; i++) {
//            url += "'" + (Constants.TIME_RANGES_STRING[i]) + "'";
//            if (i != Constants.TIME_RANGES_INTEGER.length-1) {
//                url += ",";
//            } else {
//                url += "],datasets:[{label:'Requests',data:[";
//            }
//        }
//
//        LocalDate currentDate = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        String formattedDate = currentDate.format(formatter);
//        HashMap<String, Integer> requestsByDay = requestCounts.get(formatter);
//
//        for (int i = 0; i < Constants.TIME_RANGES_INTEGER.length; i++) {
//            url += (requestsByDay.get(i));
//            if (i != Constants.TIME_RANGES_INTEGER.length-1) {
//                url += ",";
//            } else {
//                url += ("]}]}}");
//            }
//        }
//
//        try {
//            Document doc = Jsoup.connect(url).get();
//            Element imgElement = doc.select("img").first();
//            String imageUrl = imgElement.attr("src");
//            URL imageSrc = new URL(imageUrl);
//
//            ImageIcon imageIcon = new ImageIcon(imageSrc);
//            JLabel imageLabel = new JLabel(imageIcon);
//            imageLabel.setBounds(0,0,700,500);
//            this.getContentPane().add(imageLabel);
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        this.setVisible(true);
//    }
//
//}
