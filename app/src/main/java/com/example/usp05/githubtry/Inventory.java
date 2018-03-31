package com.example.usp05.githubtry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by minh on 3/29/18.
 */

public class Inventory extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory);
        username = getIntent().getStringExtra("username");


        // this is an example view list, just used to visual purposes
        String items[] = {"bacon", "eggs", "potatos", "pizza", "candy", "meatball", "tuna", "phone", "headphones", "fish", "meat", "pig", "cow"};
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        ListView listItems = (ListView) findViewById(R.id.listItems);
        listItems.setAdapter(adapter);
    }

    public void onAddItemClick(View view) {
        if(view.getId() == R.id.addItemButton) {
            Intent i = new Intent(Inventory.this, AddItemActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        }
    }
}

