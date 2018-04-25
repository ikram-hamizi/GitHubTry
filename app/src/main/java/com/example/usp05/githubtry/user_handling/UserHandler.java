package com.example.usp05.githubtry.user_handling;

/**
 * Created by nathan on 4/23/18.
 */

public class UserHandler {
    private static UserHandler ourInstance = new UserHandler();
    private static User user;

    public static UserHandler getInstance() {
        if (ourInstance == null) {
            ourInstance = new UserHandler();
        }
        return ourInstance;
    }

    public static UserHandler getInstance(User u) {
        if (user == null) {
            user = u;
            ourInstance = new UserHandler();
        } else {
            if(!user.equals(u)) {
                user = u;
            }
        }
        return ourInstance;
    }

    private UserHandler() {
    }

    public String getUsername(){
        return user.getUsername();
    }
}
