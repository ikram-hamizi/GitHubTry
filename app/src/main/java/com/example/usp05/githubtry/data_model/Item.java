package com.example.usp05.githubtry.data_model;

import java.util.Date;

/**
 * Created by Ikram Hamizi on 3/24/18.
 */

public class Item {
    //<<<<<<<<<<<<<<<<< IMPORTANT ADD PRICE TO ITEM
    //UPDATE DATABASE
    //1- Fields: 8
    private String username;
    private String name;
    private String location;
    private String type;
    private String date_purchased;
    private String date_expired;
    private String notes;
    private int quantity;
    private float average_price;
    private Date expirationDate;
    private Date purchaseDate;
    // --Commented out by Inspection (4/16/18 9:54 PM):private float price;

    public Item(String username, String name, String location, String type, String date_purchased, String date_expired, String notes, int quantity /*.float price*/)
    {
        this.username = username;
        this.name = name;
        this.location = location;
        this.type = type;
        this.date_expired = date_expired;
        this.date_purchased = date_purchased;
        this.notes = notes;
        this.quantity = quantity;
        average_price = 0.0f;
        //this.price = price;
    }

    public Item() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate_purchased() {
        return date_purchased;
    }

    public void setDate_purchased(String date_purchased) {
        this.date_purchased = date_purchased;
    }

    public String getDate_expired() {
        return date_expired;
    }

    public void setDate_expired(String date_expired) {
        this.date_expired = date_expired;
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

    public float getAverage_price() {
        return average_price;
    }

    public void setAverage_price(float average_price) {
        this.average_price = average_price;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (!getUsername().equals(item.getUsername())) return false;
        if (!getName().equals(item.getName())) return false;
        return getType().equals(item.getType());
    }

    @Override
    public int hashCode() {
        int result = getUsername().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getType().hashCode();
        return result;
    }
}
