package com.example.usp05.githubtry;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usp05.githubtry.DataModel.DBItemsHelper;

import java.util.ArrayList;

/**
 * Created by minh on 3/29/18.
 */

public class Inventory extends AppCompatActivity {
    private static final String TAG = "InventoryActivity";
    private String username;

    DBItemsHelper itemsHelper = new DBItemsHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory);
        username = getIntent().getStringExtra("username");

        populateList();
    }

    public void onAddItemClick(View view) {
        if(view.getId() == R.id.addItemButton) {
            Intent i = new Intent(Inventory.this, AddItemActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        }
    }

    public void populateList() {
        ArrayList<String> items = new ArrayList<String>();
        Cursor cursor = itemsHelper.getItems(username);
        if (cursor.moveToFirst()) {
            do {
                String a = cursor.getString(2);
                items.add(a);
            }
            while (cursor.moveToNext());
        }
        else {
            items.add("You have no items. Click 'Add Item' to start adding items!");
        }
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        ListView listItems = (ListView) findViewById(R.id.listItems);
        listItems.setAdapter(adapter);

        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on " + name);
                Cursor data = itemsHelper.getItemID(username, name);
                int itemID = -1;
                while(data.moveToNext()) {
                    itemID = data.getInt(0);
                    if(itemID > -1) {
                        Log.d(TAG, "onItemClick: ID is " + itemID);
                        Intent intent = new Intent(Inventory.this, ItemDisplayDetails.class);
                        intent.putExtra("username", username);
                        intent.putExtra("id", itemID);
                        startActivity(intent);
                    }
                    else {
                        Toast message = Toast.makeText(Inventory.this, "No ID available", Toast.LENGTH_SHORT);
                        message.show();
                    }
                }
             }
        });
    }
}

