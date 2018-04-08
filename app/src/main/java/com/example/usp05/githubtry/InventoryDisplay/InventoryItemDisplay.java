package com.example.usp05.githubtry.InventoryDisplay;

import android.database.Cursor;

/**
 * Created by nathan on 4/8/18.
 */

public class InventoryItemDisplay {

    private String itemName;
    private String itemLocation;
    private int itemQuantity;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public static InventoryItemDisplay fromCursor(Cursor cursor) {

        InventoryItemDisplay iid = new InventoryItemDisplay();

        iid.itemName = cursor.getString(cursor.getColumnIndex("NAME"));
        iid.itemLocation = cursor.getString(cursor.getColumnIndex("LOCATION"));
        iid.itemLocation = cursor.getString(cursor.getColumnIndex("QUANTITY"));

        cursor.moveToNext();

        return iid;
    }

    public String getItemQuantityString() {
        return Integer.toString(this.itemQuantity);
    }
}
