package org.example;
public enum Activity {
    WEATHER("Weather"),
    NEWS("News"),
    NASA("NASA"),
    COUNTRIES_INFORMATION("Countries information"),
    COVID_19_DATA("Covid-19 Data");

    private final String activityName;

    Activity(String activityName) {
        this.activityName = activityName;
    }

    public static Activity getActivityFromRequest(String request) {
        for (Activity activity : Activity.values()) {
            if (activity.getActivityName().equals(request)) {
                return activity;
            }
        }
        return null;
    }

    public String getActivityName() {
        return this.activityName;
    }
}
