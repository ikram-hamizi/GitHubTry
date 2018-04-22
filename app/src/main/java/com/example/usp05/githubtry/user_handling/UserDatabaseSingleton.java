package com.example.usp05.githubtry.user_handling;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import static com.example.usp05.githubtry.AppContext.getContext;


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
        }
        return thisInstance;
    }

    private UserDatabaseSingleton(){
        userHelper = new UserDatabaseHelper(getContext(),
                UserDatabaseHelper.DATABASE_NAME, null,
                UserDatabaseHelper.DATABASE_VERSION);
    };

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
