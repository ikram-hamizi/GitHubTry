package com.example.usp05.githubtry.DataModel;

import android.support.annotation.NonNull;

/**
 * Created by Ikram Hamizi on 3/24/18.
 */

public class Item {

    //1- Fields: 8
    private String name, location, type, date_purchased, date_expired, notes;
    private int quantity;
    private float average_price;

    public Item(String name, String location, String type, String date_purchased, String date_expired, String notes, int quantity)
    {

    }

    //2- Methods
    public float calculateAvgPrice()
    {
        return average_price;
    }

    public int calculateRecommendedQuantity()
    {
        return 0;
    }

    public float rateOfUsage()
    {
        return 0.0f; //%
    }

    public void setDate_expired(String date_expired) {
        this.date_expired = date_expired;
    }

    public void setDate_purchased(String date_purchased) {
        this.date_purchased = date_purchased;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getAverage_price() {
        return average_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate_expired() {
        return date_expired;
    }

    public String getDate_purchased() {
        return date_purchased;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals (Object obj)
    {
        return true;
    }
}
