package org.example;
public class Constants {
    public static final String BOT_USER_NAME = "WorldDataBot";
    public static final String BOT_TOKEN = "5916599638:AAFcHKKGlF6cjqF0iYujl6-7nZmQHEKw1SI";
    public static final int CHECK_BOX_AND_LABEL_WIDTH = 400;
    public static final int CHECK_BOX_AND_LABEL_HEIGHT = 40;
    public static final int MARGIN_FROM_TOP = 5;
    public static final int MARGIN_FROM_LEFT = 10;
    public static final int BOT_INTERFACE_WINDOW_WIDTH = 800;
    public static final int BOT_INTERFACE_WINDOW_HEIGHT = 700;
    public static final int UPDATE_BUTTON_WIDTH = 180;
    public static final int UPDATE_BUTTON_HEIGHT = 30;
    public static final int INVALID_FINAL_CHECKS_LABEL_WIDTH = 200;
    public static final int INVALID_FINAL_CHECKS_LABEL_HEIGHT = 30;
    public static final int TITLE_LABEL_WIDTH = 300;
    public static final int TITLE_LABEL_HEIGHT = 40;
    public static final int TITLE_LABEL_SIZE = 18;
    public static final int REGULAR_LABEL_SIZE = 15;
    public static final int INVALID_CHECKS_LABEL_SIZE = 13;
    public static final int DONE_BUTTON_SIZE = 14;
    public static final String[] ACTIVITIES = {"Public holidays", "Random Quote", "NASA", "Countries information", "Makeup"};

    public static final String[] TIME_RANGES_STRING = {"06:00-11:00" , "12:00-17:00", "18:00-23:00", "00:00-05:00"};
    public static final int[][] TIME_RANGES_INTEGER = {{6,11}, {12,17}, {18,23}, {0,5}};

    public static final int REQUESTS_GRAPH_WINDOW_WIDTH = 750;
    public static final int REQUESTS_GRAPH_WINDOW_HEIGHT = 550;

    public static final String URL_QUOTE = "https://api.quotable.io/quotes/random";
    public static final String URL_MAKEUP = "http://makeup-api.herokuapp.com/api/v1/products.json?brand=";
    public static final String URL_NASA = "https://api.nasa.gov/planetary/apod?api_key=mglHbNmXB8rG1E7GcXUqKFlorMXcL3qgirT7DLGZ";
    public static final String URL_HOLIDAYS = "https://date.nager.at/api/v2/publicholidays/2023/";
    public static final String URL_COUNTRIES = "https://restcountries.com/v2/name/";
    public static final String INITIAL_URL_CHARTS = "https://quickchart.io/chart?c={type:'bar',data:{labels:[";
}
