package com.example.usp05.githubtry.inventory_display;

/**
 * Created by nathan on 4/8/18.
 */

public class InventoryItemDisplay {


    private int itemID;
    private String itemName;
    private String itemLocation;
    private int itemQuantity;

    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public CharSequence getItemLocation() {
        return itemLocation;
    }

    public CharSequence getItemQuantityString() {
        return Integer.toString(itemQuantity);
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

// --Commented out by Inspection START (4/16/18 9:54 PM):
//    public int getItemQuantity() {
//        return itemQuantity;
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }
}
