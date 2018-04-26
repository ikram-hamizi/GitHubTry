package com.example.usp05.githubtry.item_manipulation;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.usp05.githubtry.data_model.Item;
import com.example.usp05.githubtry.data_model.DBItemsHelper;
import com.example.usp05.githubtry.inventory_display.InventoryActivity;
import com.example.usp05.githubtry.R;
import com.example.usp05.githubtry.item_manipulation.item_location_details.ItemLocationDetails;
import com.example.usp05.githubtry.item_manipulation.item_location_details.LocationDetailsAdapter;
import com.example.usp05.githubtry.user_handling.UserHandler;

import android.util.Log;
import android.app.NotificationManager;

import java.util.ArrayList;


public class ItemDisplayDetails extends AppCompatActivity {

    private final DBItemsHelper db_helper = new DBItemsHelper(this);
    private UserHandler UH = UserHandler.getInstance();
    private String username = UH.getUsername();

    private ArrayList<ItemLocationDetails> locationDetails = new ArrayList<>();

    private final DBItemsHelper helper = new DBItemsHelper(this);


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
//            ((TextView) findViewById(R.id.location_info_TV)).setText(myItem.getLocation());
            ((TextView) findViewById(R.id.category_info_TV)).setText(myItem.getType());
            ((TextView) findViewById(R.id.quantity_info_TV)).setText(String.valueOf(myItem.getQuantity()));
//            ((TextView) findViewById(R.id.dateExpired_info_TV)).setText(myItem.getDate_expired());
//            ((TextView) findViewById(R.id.datePurchased_info_TV)).setText(myItem.getDate_purchased());
            ((TextView) findViewById(R.id.note_info_TV)).setText(myItem.getNotes());
            ((TextView) findViewById(R.id.avgPrice_info_TV)).setText(String.valueOf(myItem.getAverage_price()));
        }

        // displaying items in recycle view
        createList();
        LocationDetailsAdapter adapter = new LocationDetailsAdapter(this, locationDetails);
        RecyclerView RV_locationDetails = findViewById(R.id.RV_item_location_details);
        RV_locationDetails.setLayoutManager(new LinearLayoutManager(this));
//        RV_inventory.setItemAnimator(new DefaultItemAnimator());
        RV_locationDetails.setAdapter(adapter);

        Log.d("PLAYGROUND", "Details ID: " + getIntent().getIntExtra("EXTRA_DETAILS_ID", -1));
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(InventoryActivity.NOTIFICATION_ID);
    }

    public void onDeleteClick (View view) {
        if (view.getId() == R.id.itemDelete) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            // "Yes" button clicked
                            db_helper.deleteItem(username, itemID);
                            Intent intent = new Intent(ItemDisplayDetails.this, InventoryActivity.class);
                            startActivity(intent);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            // "No" button clicked
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("Are you sure you want to delete this item?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }


    public void onAddMoreClick(View view)
    {
        Intent addMorePop = new Intent(this, PopUpAddMoreActivity.class);
        addMorePop.putExtra("username", username);
        addMorePop.putExtra("itemName", myItem.getName());

        startActivityForResult(addMorePop, 0);
        //startActivity(addMorePop);
    }

    public void onEditClick (View view)
    {

        Intent intent = new Intent(this, EditItemActivity.class);
        intent.putExtra("id", itemID);
        startActivity(intent);

        // db_helper.editItem()
        // db_helper.editItem(Integer.parseInt(EXTRA_MESSAGE_RECEIVED_ID), );
    }

    // TODO: Fix layout when this list grows too big (buttons are disappearing)

    private void createList() {
        Cursor cursor = helper.getItemLocationDetails(myItem.getName());
        if (cursor.moveToFirst()) {
            do {
//                int id = cursor.getInt(cursor.getColumnIndex("ID"));
//                String name = cursor.getString(cursor.getColumnIndex("NAME"));
//                String location = cursor.getString(cursor.getColumnIndex("LOCATION"));
//                int quantity = cursor.getInt(cursor.getColumnIndex("QUANTITY"));

                ItemLocationDetails details = new ItemLocationDetails();
                locationDetails.add(details);
            }
            while (cursor.moveToNext());
        }
    }
}