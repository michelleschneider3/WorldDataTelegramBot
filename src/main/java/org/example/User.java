package org.example;

import java.util.HashMap;
public class User {
    private String userName;
    private HashMap <String, Integer> requests;
    public User (String userName) {
        this.userName = userName;
        this.requests = new HashMap<>();
        for (int i = 0; i < Constants.ACTIVITIES.length; i++) {
            this.requests.put(Constants.ACTIVITIES[i], 0);
        }
    }

    public void setRequests(String activity) {
        for (int i = 0; i < Constants.ACTIVITIES.length; i++) {
           if (activity.equals(Constants.ACTIVITIES[i])) {
               int value = this.requests.get(Constants.ACTIVITIES[i]);
               value++;
               this.requests.put(Constants.ACTIVITIES[i], value);
           }
        }
    }

    public HashMap<String, Integer> getRequests() {
        return requests;
    }

    public String getUserName() {
        return userName;
    }
}
