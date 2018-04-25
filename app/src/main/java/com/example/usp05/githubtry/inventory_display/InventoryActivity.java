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
import com.example.usp05.githubtry.item_manipulation.ItemDisplayDetails;
import com.example.usp05.githubtry.user_handling.UserHandler;

import java.util.Collection;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ikram 04/04/2018
 */

/**
 * Refactored by nathan on 4/8/18.
 */

public class InventoryActivity extends Activity {

    private Button notiB;
    public static int NOTIFICATION_ID = 1;

    private UserHandler UH = UserHandler.getInstance();
    private String username = UH.getUsername();

    private final DBItemsHelper helper = new DBItemsHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_display_activity);

        Collection<String> typeFilters = (Collection<String>) getIntent().getSerializableExtra("typeFilters");
        Collection<String> locationFilters = (Collection<String>) getIntent().getSerializableExtra("locationFilters");

        InventoryCursorAdapter inventoryAdapter = new InventoryCursorAdapter(this, populateFilteredList(typeFilters, locationFilters), username);

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

        RecyclerView RV_inventory = findViewById(R.id.RV_inventory);
        RV_inventory.setLayoutManager(new LinearLayoutManager(this));
        RV_inventory.setItemAnimator(new DefaultItemAnimator());
        RV_inventory.setAdapter(inventoryAdapter);

        notiB = (Button) findViewById(R.id.notificationButton);
        notiB.setOnClickListener(buttonClickListener);
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
