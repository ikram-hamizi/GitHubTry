package com.example.usp05.githubtry.item_manipulation;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usp05.githubtry.data_model.Item;
import com.example.usp05.githubtry.data_model.ItemDatabaseSingleton;
import com.example.usp05.githubtry.inventory_display.InventoryActivity;
import com.example.usp05.githubtry.R;

public class EditItemActivity extends AppCompatActivity
{
    private int itemID;

    private ItemDatabaseSingleton IDS = ItemDatabaseSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        itemID = getIntent().getIntExtra("id", 0);

        //Load information as place-holder in the edit screen
        Item editedItem = IDS.searchItem(itemID);

        if (Log.isLoggable(ContentValues.TAG,Log.VERBOSE)){
            Log.v(ContentValues.TAG,">>>>>>>>>>>>>> NAME: "+editedItem.getName());
        }

        if(editedItem != null) {
            ((TextView) findViewById(R.id.ET_name)).setText(editedItem.getName());
            ((TextView) findViewById(R.id.ET_quantity)).setText(String.valueOf(editedItem.getQuantity()));
            ((TextView) findViewById(R.id.ET_location)).setText(editedItem.getLocation());

            // TODO: Fix these date fields
//            ((TextView) findViewById(R.id.ET_dateExpired)).setText(editedItem.getExpiration_date());
//            ((TextView) findViewById(R.id.ET_datePurchased)).setText(editedItem.getPurchase_date());
            //((EditText) findViewById(R.id.ET_price)).setText(editedItem.getAverage_price());
            ((TextView) findViewById(R.id.ET_category)).setText(editedItem.getCategory());
            ((TextView) findViewById(R.id.ET_note)).setText(editedItem.getNotes());
        }
    }

    //ACTION
    public void onEditSaveClick (View view)
    {

        try {
            int item_quantity = Integer.parseInt(((TextView) findViewById(R.id.ET_quantity)).getText().toString());
            String item_name = ((TextView) findViewById(R.id.ET_name)).getText().toString().toUpperCase();
            String item_location = ((TextView) findViewById(R.id.ET_location)).getText().toString();
            String item_category = ((TextView) findViewById(R.id.ET_category)).getText().toString();
            String item_datePurchased = ((TextView) findViewById(R.id.ET_datePurchased)).getText().toString();
            String item_dateExpired = ((TextView) findViewById(R.id.ET_dateExpired)).getText().toString();

            String item_price = ((TextView) findViewById(R.id.ET_price)).getText().toString();
            String item_note = ((TextView) findViewById(R.id.ET_note)).getText().toString();

            String[] array_item_components_edited = {item_name, item_location, item_category, item_datePurchased, item_dateExpired, String.valueOf(item_quantity),
                    item_price, item_note};

            String[] array_cols_names_DB = {
                    "USERNAME",
                    "ID",
                    "NAME",
                    "LOCATION",
                    "TYPE",
                    "DATE_PURCHASED",
                    "DATE_EXPIRED",
                    "QUANTITY",
                    "AVERAGE_PRICE",
                    "NOTES"};
            for(int i = 2; i <= 9; i++) //IS THERE ANY ITEM COMPONENT MISSING? <<<<<<  PRICE??
            {
                // USERNAME 0
                // ID 1
                // NAME 2
                // LOCATION 3
                // TYPE 4
                // DATE_PURCHASED 5
                // DATE_EXPIRED 6
                // QUANTITY 7
                // AVERAGE_PRICE 8
                // NOTES 9

                //editItem(int edited_item_id, String editedColumnName, String newInfo)

                if (i != 8) { //Cannot change average price
//                    db_helper.editItem(itemID, array_cols_names_DB[i], array_item_components_edited[i - 2]);
                    // TODO: Incorporate this activity with the updated database methods
                }
            }
        } catch(NumberFormatException ignored)
        {
            //Wrong input (quantity is not an int)
            Toast not_int_popup = Toast.makeText(this, "Wrong input, please enter an integer for quantity", Toast.LENGTH_SHORT);
            not_int_popup.show(); //<- Working Properly.
        }

        Intent i = new Intent(this, InventoryActivity.class);
        startActivity(i);
    }

    public void onEditDeleteClick (View view)
    {
        IDS.deleteItem(itemID);
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }


}

//You can do "File" -> "Invalidate Caches...", and select "Invalidate and Restart" option to fix this.

