package com.example.usp05.githubtry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.usp05.githubtry.DataModel.Item;
import com.example.usp05.githubtry.DataModel.DBItemsHelper;

public class ItemDisplayDetails extends AppCompatActivity {

    DBItemsHelper db_helper = new DBItemsHelper(this);

    //EXTRA MESSAGE = ID
    //ID of Current Inventory Item clicked needed.

    public static final String EXTRA_MESSAGE_SENT_ID = "com.example.usp05.githubtry.MESSAGE"; //COPY IN CURRENT INVENTORY

    private Item myItem;
    private int ItemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display_details);

        Intent intent = getIntent();
//        ItemID = Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_MESSAGE_RECEIVED_ID));

        myItem = db_helper.searchItem(ItemID);

        ((TextView) findViewById(R.id.location_info_TV)).setText(myItem.getLocation());
        ((TextView) findViewById(R.id.category_info_TV)).setText(myItem.getType());
        ((TextView) findViewById(R.id.quantity_info_TV)).setText(myItem.getQuantity());
        ((TextView) findViewById(R.id.dateexp_info_TV)).setText(myItem.getDate_expired());
        ((TextView) findViewById(R.id.datepurch_info_TV)).setText(myItem.getDate_purchased());
        ((TextView) findViewById(R.id.note_info_TV)).setText(myItem.getNotes());
        ((TextView) findViewById(R.id.avgprice_info_TV)).setText("" + myItem.getAverage_price());
    }

    public void onDeleteClick (View view)
    {
        db_helper.deleteItem(ItemID);
        //DELETE? IS IT WORKING? Needs to be tried with Current Inventory
    }

    public void onEditClick (View view)
    {

        Intent intent = new Intent(this, EditItemActivity.class);
        intent.putExtra(EXTRA_MESSAGE_SENT_ID, ItemID);

        // db_helper.editItem()
        // db_helper.editItem(Integer.parseInt(EXTRA_MESSAGE_RECEIVED_ID), );
    }
}
