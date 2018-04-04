package com.example.usp05.githubtry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usp05.githubtry.DataModel.DBItemsHelper;
import com.example.usp05.githubtry.DataModel.Item;

public class EditItemActivity extends AppCompatActivity
{
    private int itemID;
    private String username;
    private DBItemsHelper db_helper = new DBItemsHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        username = getIntent().getStringExtra("username");
        itemID = getIntent().getIntExtra("id", 0);

        //Load information as place-holder in the edit screen

        Item editedItem = db_helper.searchItem(username, itemID);
        System.out.println(">>>>>>>>>>>>>> NAME: "+editedItem.getName());

        if(editedItem != null) {
            ((EditText) findViewById(R.id.ET_name)).setText(editedItem.getName());
            ((EditText) findViewById(R.id.ET_quantity)).setText(""+editedItem.getQuantity());
            ((EditText) findViewById(R.id.ET_location)).setText(editedItem.getLocation());
            ((EditText) findViewById(R.id.ET_dateexp)).setText(editedItem.getDate_expired());
            ((EditText) findViewById(R.id.ET_datepurch)).setText(editedItem.getDate_purchased());
            //((EditText) findViewById(R.id.ET_price)).setText(editedItem.getAverage_price());
            ((EditText) findViewById(R.id.ET_category)).setText(editedItem.getType());
            ((EditText) findViewById(R.id.ET_note)).setText(editedItem.getNotes());
        }
    }

    //ACTION
    public void onEditSaveClick (View view)
    {

        String[] array_cols_names_DB = {
                "USERNAME",
                "ID" ,
                "NAME" ,
                "LOCATION" ,
                "TYPE" ,
                "DATE_PURCHASED" ,
                "DATE_EXPIRED" ,
                "QUANTITY" ,
                "AVERAGE_PRICE" ,
                "NOTES" };

        try {
            int item_quantity = Integer.parseInt(((EditText) findViewById(R.id.ET_quantity)).getText().toString());
            String item_name = ((EditText) findViewById(R.id.ET_name)).getText().toString().toUpperCase();
            String item_location = ((EditText) findViewById(R.id.ET_location)).getText().toString();
            String item_category = ((EditText) findViewById(R.id.ET_category)).getText().toString();
            String item_datepurch = ((EditText) findViewById(R.id.ET_datepurch)).getText().toString();
            String item_dateexpired = ((EditText) findViewById(R.id.ET_dateexp)).getText().toString();

            String item_price = ((EditText) findViewById(R.id.ET_price)).getText().toString();
            String item_note = ((EditText) findViewById(R.id.ET_note)).getText().toString();

            String[] array_item_components_edited = {item_name, item_location, item_category, item_datepurch, item_dateexpired, String.valueOf(item_quantity),
                    item_price, item_note};

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
                    db_helper.editItem(itemID, array_cols_names_DB[i], array_item_components_edited[i - 2]);
                }
            }
        } catch(NumberFormatException e)
        {
            //Wrong input (quantity is not an int)
            Toast not_int_popup = Toast.makeText(EditItemActivity.this, "Wrong input, please enter an integer for quantity", Toast.LENGTH_SHORT);
            not_int_popup.show(); //<- Working Properly.
        }

        Intent i = new Intent(EditItemActivity.this, Inventory.class);
        i.putExtra("username", username);
        startActivity(i);
    }

    public void onDeleteClick (View view)
    {
        db_helper.deleteItem(username, itemID);
        Intent intent = new Intent(EditItemActivity.this, Inventory.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }


}

//You can do "File" -> "Invalidate Caches...", and select "Invalidate and Restart" option to fix this.

