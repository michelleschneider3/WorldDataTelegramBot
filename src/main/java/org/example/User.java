package org.example;
import java.util.HashMap;
import java.util.Objects;

public class User {
    private String userName;
    private long chatId;
    private HashMap <String, Integer> requests;
    public User (String userName, long chatId) {
        this.userName = userName;
        this.chatId = chatId;
        this.requests = new HashMap<>();
        for (int i = 0; i < Constants.ACTIVITIES.length; i++) {
            this.requests.put(Constants.ACTIVITIES[i], 0);
        }
    }

    public void updateRequests(String activity) {
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

    public boolean equalsUserByChatId(long chatId) {
        return Objects.equals(this.chatId, chatId);
    }

    public long getChatId() {
        return chatId;
    }
}
