package com.example.usp05.githubtry.item_manipulation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usp05.githubtry.data_model.DBHelper;
import com.example.usp05.githubtry.data_model.DBItemsHelper;
import com.example.usp05.githubtry.data_model.Item;
import com.example.usp05.githubtry.inventory_display.InventoryActivity;
import com.example.usp05.githubtry.R;

import java.util.List;

/**
 * Created by minh on 3/24/18.
 */

public class AddItemActivity extends AppCompatActivity
{
    private final DBItemsHelper db_helper = new DBItemsHelper(this);
    private String username;
    AutoCompleteTextView location_aCTV, category_aCTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_activity);
        username = getIntent().getStringExtra("username");

        location_aCTV = (AutoCompleteTextView) findViewById(R.id.aCTV_item_location);
        List<String> locations = db_helper.getLocations();
        String[] location_suggestions = locations.toArray(new String[locations.size()]);
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,location_suggestions);

        category_aCTV = (AutoCompleteTextView) findViewById(R.id.aCTV_item_category);
        List<String> categories = db_helper.getTypes();
        String[] category_suggestions = categories.toArray(new String[categories.size()]);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,category_suggestions);

        location_aCTV.setThreshold(1);
        location_aCTV.setAdapter(locationAdapter);
        category_aCTV.setThreshold(1);
        category_aCTV.setAdapter(categoryAdapter);
    }

    //ACTION
    public void onSaveClick (View view)
    {
        String item_name = ((TextView) findViewById(R.id.ET_name)).getText().toString();
        try {
            String item_location = ((AutoCompleteTextView) findViewById(R.id.aCTV_item_location)).getText().toString();
            String item_category = ((AutoCompleteTextView) findViewById(R.id.aCTV_item_category)).getText().toString();
            String item_datePurchased = ((TextView) findViewById(R.id.ET_date_purchased)).getText().toString();
            String item_dateExpired = ((TextView) findViewById(R.id.ET_expiration_date)).getText().toString();
            String item_price = ((TextView) findViewById(R.id.ET_item_price)).getText().toString();
            String item_note = ((TextView) findViewById(R.id.ET_item_notes)).getText().toString();
            int item_quantity = Integer.parseInt(((TextView) findViewById(R.id.ET_item_quantity)).getText().toString());

            if((item_name == "") || (String.valueOf(item_quantity) == "")) {
                Toast message = Toast.makeText(this, "Name and quantity must be filled", Toast.LENGTH_SHORT);
                message.show();
            }

            Item newItem = new Item(username, item_name, item_location, item_category, item_datePurchased, item_dateExpired,
                    item_note, item_quantity);

            db_helper.insertItem(newItem); //Insert Item to DB
            db_helper.insertedToast(this).show();
//            Intent i = new Intent(AddItemActivity.this, Inventory.class);
            Intent i = new Intent(this, InventoryActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        }
        catch(NumberFormatException ignored) {
            //Wrong input (quantity is not an int)
            Toast not_int_popup = Toast.makeText(this, "Wrong input, please enter an integer for quantity", Toast.LENGTH_SHORT);
            not_int_popup.show(); //<- Working Properly.
        }
    }
public void onEditSaveClick(View view) {}
public void onEditDeleteClick(View view) {}}

//You can do "File" -> "Invalidate Caches...", and select "Invalidate and Restart" option to fix this.

