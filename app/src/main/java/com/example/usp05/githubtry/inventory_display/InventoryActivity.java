package com.example.usp05.githubtry.inventory_display;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.example.usp05.githubtry.item_manipulation.ItemDisplayDetails;
import com.example.usp05.githubtry.user_handling.UserHandler;
import java.util.ArrayList;
import java.util.Collection;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Ikram 04/04/2018
 */

/**
 * Refactored by nathan on 4/8/18.
 * Refactored by minh on 4/25/18.
 */

public class InventoryActivity extends Activity {
  
    private ArrayList<InventoryItemDisplay> items = new ArrayList<>();
    private FloatingActionButton notiB;
    public static int NOTIFICATION_ID = 1;
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
        FloatingActionButton addItemButton = findViewById(R.id.BAddItem);

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
        notiB = (FloatingActionButton) findViewById(R.id.notificationButton);
        notiB.setOnClickListener(buttonClickListener);
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
        // TODO: Get filters to work
        // TODO: Incorporate "You have no items" result

        Cursor returnCursor;

        if ((typeFilters == null) && (locationFilters == null)) {
            returnCursor = helper.getItems(username);
        } else {
            returnCursor = helper.getFilteredItems(username, typeFilters, locationFilters);
        }

        return returnCursor;
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Create PendingIntent to take us to DetailsActivity
            // as a result of notification action
            Intent detailsIntent = new Intent(InventoryActivity.this, ItemDisplayDetails.class);
            detailsIntent.putExtra("EXTRA_DETAILS_ID", 42);
            PendingIntent detailsPendingIntent = PendingIntent.getActivity(
                    InventoryActivity.this,
                    0,
                    detailsIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            // NotificationCompat Builder takes care of backwards compatibility and
            // provides clean API to create rich notifications
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(InventoryActivity.this)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("GithubTry")
                    .setContentText("An item is almost depleted, do you want to buy more?")
                    .setAutoCancel(true)
                    .setContentIntent(detailsPendingIntent)
                    .addAction(android.R.drawable.ic_menu_compass, "Details", detailsPendingIntent);


            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, mBuilder.build());

            }

    };

}
