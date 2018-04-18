package com.example.usp05.githubtry.inventory_display;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.usp05.githubtry.item_manipulation.AddItemActivity;
import com.example.usp05.githubtry.data_model.DBItemsHelper;
import com.example.usp05.githubtry.item_filtering.FilterActivity;
import com.example.usp05.githubtry.R;

import java.util.Collection;

/**
 * Created by Ikram 04/04/2018
 */

/**
 * Refactored by nathan on 4/8/18.
 */

public class InventoryActivity extends Activity {

    String username;

    private final DBItemsHelper helper = new DBItemsHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_display_activity);

        username = getIntent().getStringExtra("username");
        Collection<String> typeFilters = (Collection<String>) getIntent().getSerializableExtra("typeFilters");
        Collection<String> locationFilters = (Collection<String>) getIntent().getSerializableExtra("locationFilters");

        InventoryCursorAdapter inventoryAdapter = new InventoryCursorAdapter(this, populateFilteredList(typeFilters, locationFilters), username);

        Button filterButton = findViewById(R.id.BFilter);
        Button addItemButton = findViewById(R.id.BAddItem);

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

        RecyclerView RV_inventory = findViewById(R.id.RV_inventory);
        RV_inventory.setLayoutManager(new LinearLayoutManager(this));
        RV_inventory.setItemAnimator(new DefaultItemAnimator());
        RV_inventory.setAdapter(inventoryAdapter);
    }

    private Cursor populateFilteredList(Collection<String> typeFilters, Collection<String> locationFilters){

        // TODO: Incorporate "You have no items" result

        Cursor returnCursor;

        if ((typeFilters == null) && (locationFilters == null)) {
            returnCursor = helper.getItems(username);
        } else {
            returnCursor = helper.getFilteredItems(username, typeFilters, locationFilters);
        }

        return returnCursor;
    }
}
