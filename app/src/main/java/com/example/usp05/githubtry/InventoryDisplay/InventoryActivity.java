package com.example.usp05.githubtry.InventoryDisplay;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.usp05.githubtry.AddItemActivity;
import com.example.usp05.githubtry.DataModel.DBItemsHelper;
import com.example.usp05.githubtry.Inventory;
import com.example.usp05.githubtry.InventoryDisplay.InventoryActivity;
//import com.example.usp05.githubtry.InventoryDisplay.InventoryAdapter;
import com.example.usp05.githubtry.InventoryDisplay.InventoryCursorAdapter;
import com.example.usp05.githubtry.ItemDisplayDetails;
import com.example.usp05.githubtry.ItemFiltering.FilterActivity;
import com.example.usp05.githubtry.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nathan on 4/8/18.
 */

public class InventoryActivity extends Activity {
    private static final String TAG = "InventoryActivity";

    StringBuffer sb = null;
//    InventoryAdapter inventoryAdapter;
    InventoryCursorAdapter inventoryAdapter;
    private String username;

    DBItemsHelper helper = new DBItemsHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_display_activity);

        username = getIntent().getStringExtra("username");
        List<String> typeFilters = (ArrayList<String>) getIntent().getSerializableExtra("typeFilters");
        List<String> locationFilters = (ArrayList<String>) getIntent().getSerializableExtra("locationFilters");

//        inventoryAdapter = new InventoryAdapter(this, getLocationFilters());
        inventoryAdapter = new InventoryCursorAdapter(this, populateFilteredList(typeFilters,locationFilters));

        Button filterButton = (Button) findViewById(R.id.BFilter);
        Button addItemButton = (Button) findViewById(R.id.BAddItem);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InventoryActivity.this,FilterActivity.class);
                i.putExtra("username",username);
                startActivity(new Intent(InventoryActivity.this,FilterActivity.class));
            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InventoryActivity.this, AddItemActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        RecyclerView RV_inventory = (RecyclerView) findViewById(R.id.RV_inventory);
        RV_inventory.setLayoutManager(new LinearLayoutManager(this));
        RV_inventory.setItemAnimator(new DefaultItemAnimator());
        RV_inventory.setAdapter(inventoryAdapter);

//        populateFilteredList(typeFilters,locationFilters);

    }

    private List<String> getLocationFilters() {
        List<String> results = new ArrayList<String>();
        results = helper.getLocations();
        return results;
    }

//    private List<InventoryItemDisplay> getItemList() {
//        List<InventoryItemDisplay> results = new ArrayList<InventoryItemDisplay>();
//        results = helper.getRelevantItems();
//        return results;
//    }

    public Cursor populateFilteredList(List<String> typeFilters, List<String> locationFilters){
//        ArrayList<String> items = new ArrayList<String>();
        Cursor result;

        // TODO: Incorporate "You have no items" result

        if((typeFilters==null) && (locationFilters==null)) {
            result = helper.getItems(username);
        } else {
            result = helper.getFilteredItems(username, typeFilters, locationFilters);
        }

//        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
//        ListView listItems = (ListView) findViewById(R.id.listItems);
//        listItems.setAdapter(adapter);
//
//        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String name = adapterView.getItemAtPosition(i).toString();
//                Log.d(TAG, "onItemClick: You clicked on " + name);
//                Cursor data = helper.getItemID(username, name);
//                int itemID = -1;
//                while(data.moveToNext()) {
//                    itemID = data.getInt(0);
//                    if(itemID > -1) {
//                        Log.d(TAG, "onItemClick: ID is " + itemID);
//                        Intent intent = new Intent(InventoryActivity.this, ItemDisplayDetails.class);
//                        intent.putExtra("username", username);
//                        intent.putExtra("id", itemID);
//                        startActivity(intent);
//                    }
//                    else {
//                        Toast message = Toast.makeText(InventoryActivity.this, "No ID available", Toast.LENGTH_SHORT);
//                        message.show();
//                    }
//                }
//            }
//        });

        return result;
    }

//    public void populateFilteredList(List<String> typeFilters, List<String> locationFilters){
//        ArrayList<String> items = new ArrayList<String>();
//
//        if((typeFilters==null) && (locationFilters==null)) {
//
//            Cursor cursor = helper.getItems(username);
//            if (cursor.moveToFirst()) {
//                do {
//                    String a = cursor.getString(cursor.getColumnIndex("NAME"));
//                    items.add(a);
//                }
//                while (cursor.moveToNext());
//            } else {
//                items.add("You have no items. Click 'Add Item' to start adding items!");
//            }
//        } else {
//            Cursor cursor = helper.getFilteredItems(username, typeFilters, locationFilters);
//            if (cursor.moveToFirst()) {
//                do {
//                    String a = cursor.getString(cursor.getColumnIndex("NAME"));
//                    items.add(a);
//                }
//                while (cursor.moveToNext());
//            } else {
//                items.add("You have no items. Click 'Add Item' to start adding items!");
//            }
//        }
//
//        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
//        ListView listItems = (ListView) findViewById(R.id.listItems);
//        listItems.setAdapter(adapter);
//
//        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String name = adapterView.getItemAtPosition(i).toString();
//                Log.d(TAG, "onItemClick: You clicked on " + name);
//                Cursor data = helper.getItemID(username, name);
//                int itemID = -1;
//                while(data.moveToNext()) {
//                    itemID = data.getInt(0);
//                    if(itemID > -1) {
//                        Log.d(TAG, "onItemClick: ID is " + itemID);
//                        Intent intent = new Intent(InventoryActivity.this, ItemDisplayDetails.class);
//                        intent.putExtra("username", username);
//                        intent.putExtra("id", itemID);
//                        startActivity(intent);
//                    }
//                    else {
//                        Toast message = Toast.makeText(InventoryActivity.this, "No ID available", Toast.LENGTH_SHORT);
//                        message.show();
//                    }
//                }
//            }
//        });
//    }
}
