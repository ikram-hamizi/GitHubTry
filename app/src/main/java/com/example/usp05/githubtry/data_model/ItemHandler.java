package com.example.usp05.githubtry.data_model;

import android.database.Cursor;

import java.io.InvalidObjectException;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

import com.example.usp05.githubtry.data_model.DatabaseItem.*;
import com.example.usp05.githubtry.data_model.ItemDatabaseHelper.*;

/**
 * Created by Ikram Hamizi on 3/24/18.
 */

class ItemHandler extends ItemDetails {

    public ItemHandler() {}

    ListIterator<itemData> litr = data.listIterator();

    private String location;
    private Date purchase_date;
    private Date expiration_date;
    private int quantity;
    private double unitPrice;




    public boolean isValid(){
        if (DB_ID < 0) {
            return false;
        }
        if (name == null) {
            return false;
        }
        if (totalQuantity < 0){
            return false;
        }
        if (category == null) {
            return false;
        }
        if(totalPrice < 0) {
            return false;
        }
        if (avgPrice < 0){
            return false;
        }
        if (nextExpiration == null) {
            return false;
        }
        if (notes == null) {
            return false;
        }
        if (data == null) {
            return false;
        }

        Iterator<itemData> itr = data.iterator();
        while (itr.hasNext()){
            itemData id = itr.next();
            if (id.location == null) {
                return false;
            }
            if (id.purchase_date == null) {
                return false;
            }
            if (id.expiration_date == null) {
                return false;
            }
            if (id.quantity < 0){
                return false;
            }
            if (id.unitPrice < 0){
                return false;
            }
            if (id.totalPrice < 0){
                return false;
            }
        }
        return true;
    }

    private void updateTotalQuantity(){
        if (this.isValid()) {
            totalQuantity = 0;
            Iterator<itemData> itr = data.iterator();

            if ((data != null) && (itr.hasNext())) {
                while(itr.hasNext()){
                    itemData id = itr.next();
                    totalQuantity += id.quantity;
                }
            }
        } else {
        }
    }

    int getInventoryID(Item item){
        int result = -1;


        return result;
    }

    void createItem(Item item){
        DatabaseItem dbI = new DatabaseItem();
        itemData id = new itemData();

        id.expiration_date = item.expirationDate;
        id.location = item.location;
        id.purchase_date = item.purchaseDate;
        id.quantity = item.quantity;

        dbI.name = item.name;
        dbI.category = item.category;
        dbI.nextExpiration = item.expirationDate;
        dbI.notes = item.notes;
        dbI.totalQuantity = item.quantity;
        if (item.isTotalPrice){
            dbI.totalPrice = item.price;
            dbI.avgPrice = item.price / item.quantity;
            id.totalPrice = item.price;
            id.unitPrice = item.price / item.quantity;
        } else {
            dbI.totalPrice = item.price * item.quantity;
            dbI.avgPrice = item.price;
            id.totalPrice = item.price * item.quantity;
            id.unitPrice = item.price;
        }
        dbI.data.add(id);

    }

    Item findItem(Item item){
        Item result = new Item();

        return result;
    }


    int matchingItem(String name, String category){
        int result = -1;

//        Cursor cursor = getItems();

        return result;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemHandler that = (ItemHandler) o;

        if (!getName().equals(that.getName())) return false;
        return getCategory().equals(that.getCategory());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getCategory().hashCode();
        return result;
    }
}
