package com.example.usp05.githubtry.data_model;

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

    //2- Methods
//    public float getPrice() {return price;}
//    public void setPrice(float price) {this.price = price;}

// --Commented out by Inspection START (4/16/18 9:54 PM):
//    public void setAverage_price(float average_price) {
//        this.average_price = average_price;
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)

// --Commented out by Inspection START (4/16/18 9:54 PM):
//    public float calculateAvgPrice()
//    {
//        return average_price;
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)

    public float getAverage_price() {
        return average_price;
    }


// --Commented out by Inspection START (4/16/18 9:54 PM):
//    @SuppressWarnings("MethodMayBeStatic")
//    public int calculateRecommendedQuantity()
//    {
//        return 0;
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)


// --Commented out by Inspection START (4/16/18 9:54 PM):
//    @SuppressWarnings("MethodMayBeStatic")
//    public float rateOfUsage()
//    {
//        return 0.0f; //%
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)


// --Commented out by Inspection START (4/16/18 9:54 PM):
//    public void setDate_expired(String date_expired) {
//        this.date_expired = date_expired;
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)

    public String getDate_expired() {
        return date_expired;
    }


// --Commented out by Inspection START (4/16/18 9:54 PM):
//    public void setDate_purchased(String date_purchased) {
//        this.date_purchased = date_purchased;
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)

    public String getDate_purchased() {
        return date_purchased;
    }


// --Commented out by Inspection START (4/16/18 9:54 PM):
//    public void setLocation(String location) {
//        this.location = location;
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)

    public String getLocation() {
        return location;
    }


// --Commented out by Inspection START (4/16/18 9:54 PM):
//    public void setUsername(String name) {
//        username = name; }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)

    public String getUsername() { return username; }


// --Commented out by Inspection START (4/16/18 9:54 PM):
//    public void setName(String name) {
//        this.name = name;
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)

    public String getName() {
        return name;
    }


// --Commented out by Inspection START (4/16/18 9:54 PM):
//    public void setNotes(String notes) {
//        this.notes = notes;
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)

    public String getNotes() {
        return notes;
    }


// --Commented out by Inspection START (4/16/18 9:54 PM):
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)

    public int getQuantity() {
        return quantity;
    }


// --Commented out by Inspection START (4/16/18 9:54 PM):
//    public void setType(String type) {
//        this.type = type;
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)

    public String getType() {
        return type;
    }

    @Override
    public boolean equals (Object obj)
    {
        return true;
    }
}
