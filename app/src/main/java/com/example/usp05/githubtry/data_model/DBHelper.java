package com.example.usp05.githubtry.data_model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by nathan on 4/19/18.
 */

public class DBHelper {

    private DatabaseHelper helper;
    private String username;
    private SQLiteDatabase inventoryDatabase;

    public DBHelper(Context context) {
        try {
//            helper = new DatabaseHelper(context, this.username, null, DatabaseHelper.DB_VERSION);
            inventoryDatabase = SQLiteDatabase.openOrCreateDatabase(username + "_db",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void selectDatabase(String username){

    }
}
