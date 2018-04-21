package com.example.usp05.githubtry.user_handling;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by nathan on 4/21/18.
 */

public class UserDatabaseSingleton {

    private static UserDatabaseHelper userHelper;
    private User user;

    private static UserDatabaseSingleton thisInstance = new UserDatabaseSingleton();

    public static UserDatabaseSingleton getInstance(Context context) {
        if(thisInstance == null){
            thisInstance = new UserDatabaseSingleton();
            userHelper = new UserDatabaseHelper(context,
                    UserDatabaseHelper.DATABASE_NAME, null,
                    UserDatabaseHelper.DATABASE_VERSION);
        }
        return thisInstance;
    }

    private UserDatabaseSingleton() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUser(String username) {
        this.user = userHelper.getUser(username);
    }

    public Boolean checkUser(String username) {
        return userHelper.searchUsername(username);
    }

    public Boolean checkUser(String username, String password) {
        return userHelper.searchUsernameAndPassword(username, password);
    }

    public void createUser(User newUser){
        userHelper.insertUser(newUser);
        user = newUser;
    }
}
