package com.example.usp05.githubtry.data_model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.usp05.githubtry.user_handling.User;

/**
 * Created by nathan on 4/19/18.
 */

public class DB_Singleton {

    private SQLiteDatabase userDatabase;
    private SQLiteDatabase inventoryDatabase;
    private DatabaseHelper DB_Helper;
    private static UserDatabaseHelper userHelper;
    private User user;

    private static DB_Singleton thisInstance = new DB_Singleton();

    public static DB_Singleton getInstance(Context context) {
        if(thisInstance == null){
            thisInstance = new DB_Singleton();
            userHelper = new UserDatabaseHelper(context,
                    UserDatabaseHelper.DATABASE_NAME, null,
                    UserDatabaseHelper.DATABASE_VERSION);
        }
        return thisInstance;
    }

    private DB_Singleton() {}

    public DatabaseHelper getDB_Helper() {
        return DB_Helper;
    }

    public void setDB_Helper(DatabaseHelper DB_Helper) {
        this.DB_Helper = DB_Helper;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUser(String username) {

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
