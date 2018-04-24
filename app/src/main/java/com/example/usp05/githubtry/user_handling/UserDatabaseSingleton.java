package com.example.usp05.githubtry.user_handling;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.example.usp05.githubtry.AppContext.getContext;


/**
 * Created by nathan on 4/21/18.
 */

public class UserDatabaseSingleton {

    private static UserDatabaseHelper userHelper;
    private User user;

    private static UserDatabaseSingleton thisInstance = new UserDatabaseSingleton();


    private UserDatabaseSingleton() {
        userHelper = new UserDatabaseHelper(getContext(),
                UserDatabaseHelper.DATABASE_NAME, null,
                UserDatabaseHelper.DATABASE_VERSION);
    }

    public static UserDatabaseSingleton getInstance(Context context) {
        if (thisInstance == null) {
            thisInstance = new UserDatabaseSingleton();
        }
        return thisInstance;
    }

    public User getUser() {
        return user;
    }

    public User getUser(String username) {
        User user = userHelper.getUser(username);
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUser(String username) {
        user = userHelper.getUser(username);
    }

    public Boolean checkUser(String username) {
        return userHelper.searchUsername(username);
    }

    public Boolean checkUser(String username, String password) {
        return userHelper.searchUsernameAndPassword(username, password);
    }

    public Cursor getSecAnswers(String username) {
        return userHelper.getSecAnswers(username);
    }

    public void createUser(User newUser){
        userHelper.insertUser(newUser);
        user = newUser;
    }

    public void updatePassword(String username, String password) {
        userHelper.updatePassword(username, password);
    }
}
