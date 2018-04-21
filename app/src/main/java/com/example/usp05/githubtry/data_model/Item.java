package com.example.usp05.githubtry.data_model;

import java.sql.Date;

/**
 * Created by Ikram Hamizi on 3/24/18.
 */

public class Item {

    private String name;
    private String location;
    private String category;
    private Date purchase_date;
    private Date expiration_date;
    private String notes;
    private int quantity;
    private float totalPrice;
    private float unitPrice;

    public Item() {
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

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    // TODO: Make sure these are the equals and hash methods we want

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (Float.compare(item.getUnitPrice(), getUnitPrice()) != 0) return false;
        if (!getName().equals(item.getName())) return false;
        if (!getLocation().equals(item.getLocation())) return false;
        if (!getCategory().equals(item.getCategory())) return false;
        if (getPurchase_date() != null ? !getPurchase_date().equals(item.getPurchase_date()) : item.getPurchase_date() != null)
            return false;
        return getExpiration_date() != null ? getExpiration_date().equals(item.getExpiration_date()) : item.getExpiration_date() == null;
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getLocation().hashCode();
        result = 31 * result + getCategory().hashCode();
        result = 31 * result + (getUnitPrice() != +0.0f ? Float.floatToIntBits(getUnitPrice()) : 0);
        return result;
    }

}
