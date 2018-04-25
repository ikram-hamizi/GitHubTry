package com.example.usp05.githubtry.item_manipulation;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.usp05.githubtry.data_model.Item;
import com.example.usp05.githubtry.data_model.DBItemsHelper;
import com.example.usp05.githubtry.inventory_display.InventoryActivity;
import com.example.usp05.githubtry.R;
import com.example.usp05.githubtry.user_handling.UserHandler;

import android.util.Log;
import android.app.NotificationManager;


public class ItemDisplayDetails extends AppCompatActivity {

    private final DBItemsHelper db_helper = new DBItemsHelper(this);
    private UserHandler UH = UserHandler.getInstance();
    private String username = UH.getUsername();


    //EXTRA MESSAGE = ID
    //ID of Current Inventory Item clicked needed.

    private Item myItem;
    private int itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details_activity);
        itemID = getIntent().getIntExtra("id", 0);

        myItem = db_helper.searchItem(username, itemID);
        if(myItem != null) {
//            setTitle(myItem.getName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((android.support.v7.widget.Toolbar) findViewById(R.id.details_toolbar)).setTitle(myItem.getName());
            }
            ((TextView) findViewById(R.id.location_info_TV)).setText(myItem.getLocation());
            ((TextView) findViewById(R.id.category_info_TV)).setText(myItem.getType());
            ((TextView) findViewById(R.id.quantity_info_TV)).setText(String.valueOf(myItem.getQuantity()));
            ((TextView) findViewById(R.id.dateExpired_info_TV)).setText(myItem.getDate_expired());
            ((TextView) findViewById(R.id.datePurchased_info_TV)).setText(myItem.getDate_purchased());
            ((TextView) findViewById(R.id.note_info_TV)).setText(myItem.getNotes());
            ((TextView) findViewById(R.id.avgPrice_info_TV)).setText(String.valueOf(myItem.getAverage_price()));
        }
//        else
//        {
//            finish(); //Does it work? -> Intended to go back to previous activity if item is not found.
//        }
        Log.d("PLAYGROUND", "Details ID: " + getIntent().getIntExtra("EXTRA_DETAILS_ID", -1));
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(InventoryActivity.NOTIFICATION_ID);
    }

    public void onDeleteClick (View view)
    {
        db_helper.deleteItem(username, itemID);
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