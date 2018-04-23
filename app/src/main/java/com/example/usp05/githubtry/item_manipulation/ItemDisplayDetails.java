package com.example.usp05.githubtry.item_manipulation;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.usp05.githubtry.data_model.ItemDatabaseSingleton;
import com.example.usp05.githubtry.data_model.ItemHandler;
import com.example.usp05.githubtry.inventory_display.InventoryActivity;
import com.example.usp05.githubtry.R;

public class ItemDisplayDetails extends AppCompatActivity {

    private ItemDatabaseSingleton IDS = ItemDatabaseSingleton.getInstance();

    //EXTRA MESSAGE = ID
    //ID of Current Inventory ItemHandler clicked needed.

    private String username;
    private ItemHandler myItemHandler;
    private int itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details_activity);
        itemID = getIntent().getIntExtra("id", 0);

        myItemHandler = IDS.searchItem(itemID);
        if(myItemHandler != null) {
//            setTitle(myItemHandler.getName());
            // TODO: This version check should be changed.  If the version is not high enough, the user won't know what item they are looking at.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((android.support.v7.widget.Toolbar) findViewById(R.id.details_toolbar)).setTitle(myItemHandler.getName());
            }
            ((TextView) findViewById(R.id.location_info_TV)).setText(myItemHandler.getLocation());
            ((TextView) findViewById(R.id.category_info_TV)).setText(myItemHandler.getCategory());
            ((TextView) findViewById(R.id.quantity_info_TV)).setText(String.valueOf(myItemHandler.getQuantity()));

            // FIXME: Fix these fields so the date is properly shown
//            ((TextView) findViewById(R.id.dateExpired_info_TV)).setText(myItemHandler.getExpiration_date());
//            ((TextView) findViewById(R.id.datePurchased_info_TV)).setText(myItemHandler.getPurchase_date());
            ((TextView) findViewById(R.id.note_info_TV)).setText(myItemHandler.getNotes());

            // TODO: Fix this so it shows the average price
//            ((TextView) findViewById(R.id.avgPrice_info_TV)).setText(String.valueOf(myItemHandler.getTotalPrice()));
        }
//        else
//        {
//            finish(); //Does it work? -> Intended to go back to previous activity if item is not found.
//        }
    }

    public void onDeleteClick (View view)
    {
        IDS.deleteItem(itemID);
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
        //DELETE? IS IT WORKING? Needs to be tried with Current Inventory
    }

    public void onEditClick (View view)
    {

        Intent intent = new Intent(this, EditItemActivity.class);
        intent.putExtra("id", itemID);
        startActivity(intent);

        // db_helper.editItem()
        // db_helper.editItem(Integer.parseInt(EXTRA_MESSAGE_RECEIVED_ID), );
    }
}
