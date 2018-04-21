package com.example.usp05.githubtry.data_model;

import android.content.Context;

import com.example.usp05.githubtry.user_handling.User;
import com.example.usp05.githubtry.user_handling.UserDatabaseSingleton;


/**
 * Created by nathan on 4/19/18.
 */

public class ItemDatabaseSingleton {

    private static ItemDatabaseHelper itemHelper;
    private UserDatabaseSingleton UDS;
    private static User user;

    private static ItemDatabaseSingleton thisInstance = new ItemDatabaseSingleton();

    public static ItemDatabaseSingleton getInstance(Context context) {
        if(thisInstance == null){

        }
        return thisInstance;
    }

    public static ItemDatabaseSingleton getInstance(Context c, User u) {
        if(!user.equals(u)){
            thisInstance = new ItemDatabaseSingleton();
            String DB_Name = u.getUsername() + "_db";
            itemHelper = new ItemDatabaseHelper(c, DB_Name, null, ItemDatabaseHelper.DATABASE_VERSION);
            user = u;
        }
        return thisInstance;
    }

    private ItemDatabaseSingleton() {}


    public ItemDatabaseHelper getItemHelper() {
        return itemHelper;
    }

    public User getUser() {
        return user;
    }

}
