package com.example.usp05.githubtry.data_model;

import android.database.Cursor;

import com.example.usp05.githubtry.user_handling.User;
import com.example.usp05.githubtry.user_handling.UserDatabaseSingleton;

import java.util.Collection;
import java.util.List;

import static com.example.usp05.githubtry.AppContext.getContext;

/**
 * Created by nathan on 4/23/18.
 */

public class DatabaseConnector {

    /*
    *   To use, instantiate in a class by including the following:
    *   !!! This singleton must be first constructed in the main activity!
    *
    *   private DatabaseConnector DBC = DatabaseConnector.getInstance();
    *
    *
    *
    *   !!! Only the main activity should use:
    *
    *   DatabaseConnector DBC = DatabaseConnector.getInstance(UDS.getUser());
    */

    private DatabaseHelper dbHelper;
    private UserDatabaseSingleton UDS;
    private static User user;

    private static DatabaseConnector ourInstance = new DatabaseConnector();

    public static DatabaseConnector getInstance() {
        return ourInstance;
    }

    public static DatabaseConnector getInstance(User u) {
        if (user == null) {
            ourInstance = new DatabaseConnector(u);
        }
        else if(!user.equals(u)){
            ourInstance = new DatabaseConnector(u);
        }
        return ourInstance;
    }

    private DatabaseConnector() {}

    private DatabaseConnector(User u) {
        String DB_Name = u.getUsername() + "_db";
        dbHelper = new DatabaseHelper(getContext(), DB_Name, null, ItemDatabase.DATABASE_VERSION);
        user = u;
    }

    private DatabaseConnector(String username){
        String DB_Name = username + "_db";
        dbHelper = new DatabaseHelper(getContext(), DB_Name, null, ItemDatabase.DATABASE_VERSION);
        user = new User();
        user.setUsername(username);
    }

    public Item searchItem(int itemID) {
        return dbHelper.getItemDetails(itemID);
    }

    public void deleteItem(int itemID) {

    }

    public List<String> getLocations() {
        return dbHelper.getAllLocations();
    }

    public List<String> getCategories() {
        return dbHelper.getAllCategories();
    }

    public void addItem(Item item) {
        dbHelper.addItem(item);
    }

    public Cursor getInventoryCursor() {
        return dbHelper.getInventory();
    }

    public Cursor getInventoryCursor(Collection<String> typeFilters, Collection<String> locationFilters){
        return dbHelper.getInventory(typeFilters, locationFilters);
    }
}
