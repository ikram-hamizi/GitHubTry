package com.example.usp05.githubtry.temp_backup;

/**
 * Created by nathan on 4/23/18.
 */

public class ItemHelper extends ItemHandler {

    ItemHelper() {
    }

    private ItemHandler IH = new ItemHandler();

    public class BaseItem {
        String name;
        String category;
        String location;
        int quantity;
        double price;
        boolean isTotalPrice;
    }

    public void addItem(Item item){
        // Check if baseItem exists
        int ID = IH.getInventoryID(item);

        if (ID < 0) {
            IH.createItem(item);
        }

    }


    public void editItem(Item item){}
    public void deleteItem(){}

    public void findItem(){}

}
