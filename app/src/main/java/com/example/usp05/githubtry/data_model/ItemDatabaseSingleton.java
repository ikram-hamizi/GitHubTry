package com.example.usp05.githubtry.data_model;

import android.content.Context;
import android.database.Cursor;

import com.example.usp05.githubtry.user_handling.User;
import com.example.usp05.githubtry.user_handling.UserDatabaseSingleton;

import java.util.Collection;
import java.util.List;


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

    public Cursor getItems(Collection<String> typeFilters, Collection<String> locationFilters){
        return itemHelper.getItems(typeFilters, locationFilters);
    }

    public List<String> getCategories(){
        return itemHelper.getCategories();
    }

    public List<String> getLocations(){
        return itemHelper.getLocations();
    }

    public void insertItem(Item item){
        itemHelper.addNewItem(item);
    }

    public Item searchItem(int itemID){
        return itemHelper.searchItem(itemID);
    }

    public void deleteItem(int itemID){
        itemHelper.deleteItem(itemID);
    }
}
