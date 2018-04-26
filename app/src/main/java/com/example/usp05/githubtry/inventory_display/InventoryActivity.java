package com.example.usp05.githubtry.inventory_display;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import com.example.usp05.githubtry.item_manipulation.AddItemActivity;
import com.example.usp05.githubtry.data_model.DBItemsHelper;
import com.example.usp05.githubtry.item_filtering.FilterActivity;
import com.example.usp05.githubtry.R;
import com.example.usp05.githubtry.item_manipulation.ItemDisplayDetails;
import com.example.usp05.githubtry.notifications.DateHelper;
import com.example.usp05.githubtry.notifications.NotificationPublisher;
import com.example.usp05.githubtry.notifications.TimePickerFragment;
import com.example.usp05.githubtry.user_handling.UserHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;

/**
 * Created by Ikram 04/04/2018
 */

/**
 * Refactored by nathan on 4/8/18.
 * Refactored by minh on 4/25/18.
 */

public class InventoryActivity extends Activity {
  
    private ArrayList<InventoryItemDisplay> items = new ArrayList<>();
    public static int NOTIFICATION_ID = 1;
    private UserHandler UH = UserHandler.getInstance();
    private String username = UH.getUsername();
    InventoryAdapter inventoryAdapter;


    private Collection<String> typeFilters;
    private Collection<String> locationFilters;

    private final DBItemsHelper helper = new DBItemsHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_display_activity);

        typeFilters = (Collection<String>) getIntent().getSerializableExtra("typeFilters");
        locationFilters = (Collection<String>) getIntent().getSerializableExtra("locationFilters");

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


        TimePickerFragment newFragment = new TimePickerFragment();
        long tempTime = newFragment.getTimeInMillis();
        scheduleNotification(getNotification("XYZ item expires today!"), tempTime);
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
        Cursor cursor = helper.getFilteredItems(username, typeFilters, locationFilters);
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

    private void scheduleNotification(Notification notification, long dateTime) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 37);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        return builder.build();
    }



}
