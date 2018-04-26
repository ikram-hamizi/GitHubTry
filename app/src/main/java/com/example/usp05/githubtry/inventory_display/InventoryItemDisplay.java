package com.example.usp05.githubtry.inventory_display;

/**
 * Created by nathan on 4/8/18.
 */

public class InventoryItemDisplay {

    private int itemID;
    private String itemName;
    private String itemLocation;
    private int itemQuantity;

    boolean haveExpired = false;

    public InventoryItemDisplay() {
    }

    public InventoryItemDisplay(int itemID, String itemName, String itemLocation, int itemQuantity) {
        setItemID(itemID);
        setItemName(itemName);
        setItemLocation(itemLocation);
        setItemQuantity(itemQuantity);
        if (itemName.length() > 6) {
            // TODO: Delete this. Used for testing purposes.
            haveExpired = true;
        }
    }

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

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }
}
