package com.example.usp05.githubtry.data_model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by nathan on 4/23/18.
 */

public class Item {

    DateHandler DH = new DateHandler();

    private int DB_ID = -1;
    private String name = null;
    private int totalQuantity = -1;
    private String category = "Other";
    private double totalPrice = 0;
    private double avgPrice = 0;
    private Date nextExpiration = null;
    private String notes = null;
    private ArrayList<itemData> data = null;
    itemData id = new itemData();

    class itemData {
        private int itemId = 0;
        private String location = "Other";
        private Date purchase_date = null;
        private Date expiration_date = null;
        private int quantity = -1;
        private double unitPrice = 0;
        private double totalPrice = 0;

        itemData() {
        }

        int getItemId() {
            return itemId;
        }

        void setItemId(int itemId) {
            this.itemId = itemId;
        }

        String getLocation() {
            return location;
        }

        void setLocation(String location) {
            this.location = location;
        }

        Date getPurchase_date() {
            return purchase_date;
        }

        void setPurchase_date(Date purchase_date) {
            this.purchase_date = purchase_date;
        }

        void setPurchase_date(String purchase_date) {
            this.purchase_date = DH.itemStringToDate(purchase_date);
        }

        Date getExpiration_date() {
            return expiration_date;
        }

        void setExpiration_date(Date expiration_date) {
            this.expiration_date = expiration_date;
        }

        void setExpiration_date(String expiration_date) {
            this.expiration_date = DH.itemStringToDate(expiration_date);
        }

        int getQuantity() {
            return quantity;
        }

        void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        double getUnitPrice() {
            return unitPrice;
        }

        void setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
        }

        double getTotalPrice() {
            return totalPrice;
        }

        void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }
    }

    // Constructors

    public Item() {}

    Item(String name, int totalQuantity) {
        this.name = name;
        this.totalQuantity = totalQuantity;
        id.quantity = totalQuantity;
    }

    // Getter/Setter DB_ID

    public int getDB_ID() {
        return DB_ID;
    }

    protected void setDB_ID(int DB_ID) {
        this.DB_ID = DB_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
        id.quantity = totalQuantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        id.totalPrice = totalPrice;
    }

    public double getUnitPrice() {
        return id.unitPrice;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    protected void setAvgPrice() {
        double ap = 0;
        for (int i = 0; i < data.size(); i++){
            ap += data.get(i).unitPrice;
        }
        this.avgPrice = ap / data.size();
    }

    protected void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Date getNextExpiration() {
        return nextExpiration;
    }

    void setNextExpirationString(String expDate) {
        nextExpiration = DH.itemStringToDate(expDate);
    }

    public String getNextExpirationString() {
        return DH.itemDateToString(nextExpiration);
    }

    public String getPurchaseDateString() {
        return DH.itemDateToString(id.purchase_date);
    }

    public void setExpiration(Date expDate) {
        id.expiration_date = expDate;
    }

    public void setExpirationDateString(String str) {
        id.expiration_date = DH.itemStringToDate(str);
    }

    public void setPurchaseDateString(String str){
        id.purchase_date = DH.itemStringToDate(str);
    }

    public void setPrice(double price){
        id.totalPrice = price;
        id.unitPrice = price;
        totalPrice = price;
        avgPrice = price;
    }

    public void setQuantity(int quantity) {
        this.totalQuantity = quantity;
        id.quantity = quantity;
    }

    public void setLocation(String location) {
        id.location = location;
    }

    protected void setNextExpiration(Date nextExpiration) {
        Date exp = data.get(0).expiration_date;
        for (int i = 1; i < data.size(); i++){
            if(data.get(i).expiration_date.before(exp)) {
                exp = data.get(i).expiration_date;
            }
        }
        this.nextExpiration = exp;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    void appendNotes(String notes) {
        this.notes = this.notes + notes;
    }

    protected ArrayList<itemData> getData() {
        return data;
    }

    protected void setData(ArrayList<itemData> data) {
        this.data = data;
    }

    public int getNumberOfLocations(){
        List<String> locations = new ArrayList<>();

        int result = 0;

        for (int i = 0; i < data.size(); i++){
            if(!locations.contains(data.get(i).location)) {
                locations.add(data.get(i).location);
                result++;
            }
        }

        return result;
    }

    public List<String> getItemLocations() {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < data.size(); i++){
            result.add(data.get(i).location);
        }

        return result;
    }

    public String getItemLocation() {
        return id.location;
    }

    void setItemLocation(String itemLocation) {
        id.location = itemLocation;
    }
}
