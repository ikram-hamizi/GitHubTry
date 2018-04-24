package com.example.usp05.githubtry.item_manipulation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usp05.githubtry.data_model.DBItemsHelper;
import com.example.usp05.githubtry.data_model.Item;
import com.example.usp05.githubtry.inventory_display.InventoryActivity;
import com.example.usp05.githubtry.R;
import com.example.usp05.githubtry.user_handling.UserHandler;

/**
 * Created by minh on 3/24/18.
 */

public class AddItemActivity extends AppCompatActivity
{
    private final DBItemsHelper db_helper = new DBItemsHelper(this);
    private UserHandler UH = UserHandler.getInstance();
    private String username = UH.getUsername();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
    }

    //ACTION
    public void onSaveClick (View view)
    {
        String item_name = ((TextView) findViewById(R.id.ET_name)).getText().toString().toUpperCase();
        try {
            String item_location = ((TextView) findViewById(R.id.ET_location)).getText().toString();
            String item_category = ((TextView) findViewById(R.id.ET_category)).getText().toString();
            String item_datePurchased = ((TextView) findViewById(R.id.ET_datePurchased)).getText().toString();
            String item_dateExpired = ((TextView) findViewById(R.id.ET_dateExpired)).getText().toString();
            String item_price = ((TextView) findViewById(R.id.ET_price)).getText().toString();
            String item_note = ((TextView) findViewById(R.id.ET_note)).getText().toString();
            int item_quantity = Integer.parseInt(((TextView) findViewById(R.id.ET_quantity)).getText().toString());

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

