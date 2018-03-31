package com.example.usp05.githubtry;

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

    String EXTRA_MESSAGE_RECEIVED_ID = "0";

    Item myItem = db_helper.searchItem(Integer.parseInt(EXTRA_MESSAGE_RECEIVED_ID));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display_details);

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
        db_helper.deleteItem(Integer.parseInt(EXTRA_MESSAGE_RECEIVED_ID));
        //DELETE? IS IT WORKING? Needs to be tried with Current Inventory
    }
}
