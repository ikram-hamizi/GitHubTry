package com.example.usp05.githubtry.item_manipulation.item_location_details;

/**
 * Created by nathan on 4/26/18.
 */

public class ItemLocationDetails {

    private String location = "Other";
    private int quantity = 0;
    private String expirationDate = "None";
    private String purchaseDate = "None";

    String getLocation() {
        return location;
    }

    void setLocation(String location) {
        this.location = location;
    }

    int getQuantity() {
        return quantity;
    }

    void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    String getExpirationDate() {
        return expirationDate;
    }

    void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    String getPurchaseDate() {
        return purchaseDate;
    }

    void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
