package com.example.usp05.githubtry.data_model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nathan on 4/23/18.
 */

class DatabaseItem {

    int DB_ID = -1;
    String name = null;
    int totalQuantity = -1;
    String category = null;
    double totalPrice = -1;
    double avgPrice = -1;
    Date nextExpiration = null;
    String notes = null;
    ArrayList<itemData> data = null;

    class itemData {
        String location = null;
        Date purchase_date = null;
        Date expiration_date = null;
        int quantity = -1;
        double unitPrice = -1;
        double totalPrice = -1;

        itemData() {
        }
    }

    DatabaseItem() {

    }
}
