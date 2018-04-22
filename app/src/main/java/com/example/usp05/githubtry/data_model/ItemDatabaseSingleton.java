package com.example.usp05.githubtry.data_model;

import android.content.Context;
import android.database.Cursor;

import com.example.usp05.githubtry.user_handling.User;
import com.example.usp05.githubtry.user_handling.UserDatabaseSingleton;

import java.util.Collection;
import java.util.List;

import static com.example.usp05.githubtry.AppContext.getContext;
import static com.example.usp05.githubtry.data_model.ItemDatabase.*;


/**
 * Created by nathan on 4/19/18.
 */

public class ItemDatabaseSingleton {

    private static ItemDatabaseHelper itemHelper;
    private UserDatabaseSingleton UDS;
    private static User user;

    private static ItemDatabaseSingleton thisInstance = new ItemDatabaseSingleton();

    public static ItemDatabaseSingleton getInstance() {
        if(thisInstance == null){

        }
        return thisInstance;
    }

    public static ItemDatabaseSingleton getInstance(User u) {
        if (user == null) {
            thisInstance = new ItemDatabaseSingleton(u);
        }
        else if(!user.equals(u)){
            thisInstance = new ItemDatabaseSingleton(u);
        }
        return thisInstance;
    }

    private ItemDatabaseSingleton() {}

    private ItemDatabaseSingleton(User u) {
        String DB_Name = u.getUsername() + "_db";
        itemHelper = new ItemDatabaseHelper(getContext(), DB_Name, null, DATABASE_VERSION);
        user = u;
    }


    public ItemDatabaseHelper getItemHelper() {
        return itemHelper;
    }

    public User getUser() {
        return user;
    }

    // TODO: Fix items not showing when they are in the "Other" category or location
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


    public static String getTableInventory() {
        return TABLE_INVENTORY;
    }

    public static String getTableItem() {
        return TABLE_ITEM;
    }

    public static String getTableLocations() {
        return TABLE_LOCATIONS;
    }

    public static String getTableCategories() {
        return TABLE_CATEGORIES;
    }

    public static String getKeyId() {
        return KEY_ID;
    }

    public static String getKeyCreatedAt() {
        return KEY_CREATED_AT;
    }

    public static String getInvColName() {
        return INV_COL_NAME;
    }

    public static String getInvColQty() {
        return INV_COL_QTY;
    }

    public static String getInvColSed() {
        return INV_COL_SED;
    }

    public static String getInvColCat() {
        return INV_COL_CAT;
    }

    public static String getInvColAvgp() {
        return INV_COL_AVGP;
    }

    public static String getInvColNote() {
        return INV_COL_NOTE;
    }

    public static String getItemColInv() {
        return ITEM_COL_INV;
    }

    public static String getItemColQty() {
        return ITEM_COL_QTY;
    }

    public static String getItemColExp() {
        return ITEM_COL_EXP;
    }

    public static String getItemColPdate() {
        return ITEM_COL_PDATE;
    }

    public static String getItemColTotcost() {
        return ITEM_COL_TOTCOST;
    }

    public static String getItemColUnitcost() {
        return ITEM_COL_UNITCOST;
    }

    public static String getItemColLoc() {
        return ITEM_COL_LOC;
    }

    public static String getItemColNote() {
        return ITEM_COL_NOTE;
    }

    public static String getLocColLoc() {
        return LOC_COL_LOC;
    }

    public static String getCatColCat() {
        return CAT_COL_CAT;
    }
}
