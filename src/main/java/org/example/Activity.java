package org.example;
public enum Activity {
    PUBLIC_HOLIDAYS("Public holidays"),
    QUOTES("Random Quote"),
    NASA("NASA"),
    COUNTRIES_INFORMATION("Countries information"),
    UNIVERSITIES("Universities");

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
