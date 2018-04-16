package com.example.usp05.githubtry.InventoryDisplay;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.usp05.githubtry.ItemManipulation.AddItemActivity;
import com.example.usp05.githubtry.DataModel.DBItemsHelper;
import com.example.usp05.githubtry.ItemFiltering.FilterActivity;
import com.example.usp05.githubtry.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ikram 04/04/2018
 */

/**
 * Refactored by nathan on 4/8/18.
 */

public class InventoryActivity extends Activity {
    private static final String TAG = "InventoryActivity";

    StringBuffer sb = null;
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

        inventoryAdapter = new InventoryCursorAdapter(this, populateFilteredList(typeFilters,locationFilters), username);

        Button filterButton = (Button) findViewById(R.id.BFilter);
        Button addItemButton = (Button) findViewById(R.id.BAddItem);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InventoryActivity.this,FilterActivity.class);
                i.putExtra("username",username);
                startActivity(i);
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
    }

    private List<String> getLocationFilters() {
        List<String> results = new ArrayList<String>();
        results = helper.getLocations();
        return results;
    }

    public Cursor populateFilteredList(List<String> typeFilters, List<String> locationFilters){
        Cursor result;

        // TODO: Incorporate "You have no items" result

        if((typeFilters==null) && (locationFilters==null)) {
            result = helper.getItems(username);
        } else {
            result = helper.getFilteredItems(username, typeFilters, locationFilters);
        }


        return result;
    }
}
