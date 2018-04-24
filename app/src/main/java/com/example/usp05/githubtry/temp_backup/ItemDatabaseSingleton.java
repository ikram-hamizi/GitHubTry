package com.example.usp05.githubtry.temp_backup;

import android.database.Cursor;

import com.example.usp05.githubtry.user_handling.User;
import com.example.usp05.githubtry.user_handling.UserDatabaseSingleton;

import java.util.Collection;
import java.util.List;

import static com.example.usp05.githubtry.AppContext.getContext;


/**
 * Created by nathan on 4/19/18.
 */

public class ItemDatabaseSingleton {

    /*
    * To use, instantiate in a class by including the following:
    *
    *   private ItemDatabaseSingleton IDS = ItemDatabaseSingleton.getInstance();
    *
    *
    *
    *   !!! Only the main activity should use:
    *
    *   ItemDatabaseSingleton DBS = ItemDatabaseSingleton.getInstance(UDS.getUser());
    */

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

    private ItemDatabaseSingleton(String username){
        String DB_Name = username + "_db";
        itemHelper = new ItemDatabaseHelper(getContext(), DB_Name, null, ItemDatabase.DATABASE_VERSION);
        user = new User();
        user.setUsername(username);
    }

    private ItemDatabaseSingleton(User u) {
        String DB_Name = u.getUsername() + "_db";
        itemHelper = new ItemDatabaseHelper(getContext(), DB_Name, null, ItemDatabase.DATABASE_VERSION);
        user = u;
    }


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

    public void insertItem(ItemHandler itemHandler){
        itemHelper.addNewItem(itemHandler);
    }

    public ItemHandler searchItem(int itemID){
        return itemHelper.searchItem(itemID);
    }

    public void deleteItem(int itemID){
        itemHelper.deleteItem(itemID);
    }

    public void shutdownDatabase(){
        itemHelper.closeDatabaseIfOpen();
    }


    public static String getTableInventory() {
        return ItemDatabase.TABLE_INVENTORY;
    }

    public static String getTableItem() {
        return ItemDatabase.TABLE_ITEM;
    }

    public static String getTableLocations() {
        return ItemDatabase.TABLE_LOCATIONS;
    }

    public static String getTableCategories() {
        return ItemDatabase.TABLE_CATEGORIES;
    }

    public static String getKeyId() {
        return ItemDatabase.KEY_ID;
    }

    public static String getKeyCreatedAt() {
        return ItemDatabase.KEY_CREATED_AT;
    }

    public static String getInvColName() {
        return ItemDatabase.INV_COL_NAME;
    }

    public static String getInvColQty() {
        return ItemDatabase.INV_COL_QTY;
    }

    public static String getInvColSed() {
        return ItemDatabase.INV_COL_SED;
    }

    public static String getInvColCat() {
        return ItemDatabase.INV_COL_CAT;
    }

    public static String getInvColAvgp() {
        return ItemDatabase.INV_COL_AVGP;
    }

    public static String getInvColNote() {
        return ItemDatabase.INV_COL_NOTE;
    }

    public static String getItemColInv() {
        return ItemDatabase.ITEM_COL_INV;
    }

    public static String getItemColQty() {
        return ItemDatabase.ITEM_COL_QTY;
    }

    public static String getItemColExp() {
        return ItemDatabase.ITEM_COL_EXP;
    }

    public static String getItemColPdate() {
        return ItemDatabase.ITEM_COL_PDATE;
    }

    public static String getItemColTotcost() {
        return ItemDatabase.ITEM_COL_TOTCOST;
    }

    public static String getItemColUnitcost() {
        return ItemDatabase.ITEM_COL_UNITCOST;
    }

    public static String getItemColLoc() {
        return ItemDatabase.ITEM_COL_LOC;
    }

    public static String getItemColNote() {
        return ItemDatabase.ITEM_COL_NOTE;
    }

    public static String getLocColLoc() {
        return ItemDatabase.LOC_COL_LOC;
    }

    public static String getCatColCat() {
        return ItemDatabase.CAT_COL_CAT;
    }
}
