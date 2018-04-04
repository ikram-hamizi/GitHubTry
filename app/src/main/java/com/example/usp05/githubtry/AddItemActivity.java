package com.example.usp05.githubtry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.Integer;

import com.example.usp05.githubtry.DataModel.DBItemsHelper;
import com.example.usp05.githubtry.DataModel.Item;

/**
 * Created by minh on 3/24/18.
 */

public class AddItemActivity extends AppCompatActivity
{
    DBItemsHelper db_helper = new DBItemsHelper(this);
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        username = getIntent().getStringExtra("username");
    }

    //ACTION
    public void onSaveClick (View view)
    {
        String item_name = ((EditText) findViewById(R.id.ET_name)).getText().toString().toUpperCase();
        try {
            String item_location = ((EditText) findViewById(R.id.ET_location)).getText().toString();
            String item_category = ((EditText) findViewById(R.id.ET_category)).getText().toString();
            String item_datepurch = ((EditText) findViewById(R.id.ET_datepurch)).getText().toString();
            String item_dateexpired = ((EditText) findViewById(R.id.ET_dateexp)).getText().toString();
            String item_price = ((EditText) findViewById(R.id.ET_dateexp)).getText().toString();
            String item_note = ((EditText) findViewById(R.id.ET_note)).getText().toString();
            int item_quantity = Integer.parseInt(((EditText) findViewById(R.id.ET_quantity)).getText().toString());

            if(item_name == "" || String.valueOf(item_quantity) == "") {
                Toast message = Toast.makeText(AddItemActivity.this, "Name and quantity must be filled", Toast.LENGTH_SHORT);
                message.show();
            }

            Item newItem = new Item(username, item_name, item_location, item_category, item_datepurch, item_dateexpired,
                    item_note, item_quantity);

            db_helper.insertItem(newItem); //Insert Item to DB
            db_helper.isInsertedToast(AddItemActivity.this).show();
            Intent i = new Intent(AddItemActivity.this, Inventory.class);
            i.putExtra("username", username);
            startActivity(i);
        }
        catch(NumberFormatException e) {
            //Wrong input (quantity is not an int)
            Toast not_int_popup = Toast.makeText(AddItemActivity.this, "Wrong input, please enter an integer for quantity", Toast.LENGTH_SHORT);
            not_int_popup.show(); //<- Working Properly.
        }
    }
}

//You can do "File" -> "Invalidate Caches...", and select "Invalidate and Restart" option to fix this.

