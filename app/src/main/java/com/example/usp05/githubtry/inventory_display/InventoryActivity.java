package com.example.usp05.githubtry.inventory_display;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.usp05.githubtry.item_manipulation.AddItemActivity;
import com.example.usp05.githubtry.data_model.DBItemsHelper;
import com.example.usp05.githubtry.item_filtering.FilterActivity;
import com.example.usp05.githubtry.R;
import com.example.usp05.githubtry.user_handling.UserHandler;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Ikram 04/04/2018
 */

/**
 * Refactored by nathan on 4/8/18.
 */

public class InventoryActivity extends Activity {

    private ArrayList<InventoryItemDisplay> items = new ArrayList<>();
    private UserHandler UH = UserHandler.getInstance();
    private String username = UH.getUsername();
    InventoryAdapter inventoryAdapter;

    private final DBItemsHelper helper = new DBItemsHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_display_activity);

        Collection<String> typeFilters = (Collection<String>) getIntent().getSerializableExtra("typeFilters");
        Collection<String> locationFilters = (Collection<String>) getIntent().getSerializableExtra("locationFilters");

        // clicking buttons on inventory screen
        Button filterButton = findViewById(R.id.BFilter);
        Button addItemButton = findViewById(R.id.BAddItem);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InventoryActivity.this,FilterActivity.class);
                startActivity(i);
            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InventoryActivity.this, AddItemActivity.class);
                startActivity(i);
            }
        });

        // displaying items in recycle view
        createList();
        // InventoryCursorAdapter inventoryAdapter = new InventoryCursorAdapter(this, populateFilteredList(typeFilters, locationFilters), username);
        inventoryAdapter = new InventoryAdapter(this, items);
        RecyclerView RV_inventory = findViewById(R.id.RV_inventory);
        RV_inventory.setLayoutManager(new LinearLayoutManager(this));
//        RV_inventory.setItemAnimator(new DefaultItemAnimator());
        RV_inventory.setAdapter(inventoryAdapter);

        // implementing search bar
        EditText searchBar = findViewById(R.id.SV_Inventory);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<InventoryItemDisplay> filteredList = new ArrayList<>();
        for(InventoryItemDisplay item: items) {
            if (item.getItemName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        inventoryAdapter.filterList(filteredList);
    }

    public void createList() {
        Cursor cursor = helper.getItems(username);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                String name = cursor.getString(cursor.getColumnIndex("NAME"));
                String location = cursor.getString(cursor.getColumnIndex("LOCATION"));
                int quantity = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
                InventoryItemDisplay item = new InventoryItemDisplay(id, name, location, quantity);
                items.add(item);
            }
            while (cursor.moveToNext());
        }
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
