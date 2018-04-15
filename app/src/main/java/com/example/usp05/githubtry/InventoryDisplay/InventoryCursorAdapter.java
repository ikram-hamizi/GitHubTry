package com.example.usp05.githubtry.InventoryDisplay;

/**
 * Created by nathan on 4/8/18.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usp05.githubtry.ItemDisplayDetails;
import com.example.usp05.githubtry.R;

import static android.content.ContentValues.TAG;


public class InventoryCursorAdapter extends CursorRecyclerViewAdapter<InventoryHolder> {

    public Context c;
    Cursor cursor;
    String username;

    public InventoryCursorAdapter(Context context, Cursor cursor, String username){
        super(context,cursor);
        this.c = context;
        this.cursor = cursor;
        this.username = username;
    }

    @Override
    public InventoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_rv_layout,parent,false);
        InventoryHolder holder = new InventoryHolder(v);
        return holder;
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    @Override
    public void onBindViewHolder(final InventoryHolder viewHolder, final Cursor cursor) {
        final InventoryItemDisplay inventoryItem = InventoryItemDisplay.fromCursor(cursor);

        viewHolder.ctvItem.setText(inventoryItem.getItemName());
        viewHolder.tvLocation.setText(inventoryItem.getItemLocation());
        viewHolder.tvQuantity.setText(inventoryItem.getItemQuantityString());

        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                String name = inventoryItem.getItemName();
                int itemID = inventoryItem.getItemID();

                Log.d(TAG, "onItemClick: You clicked on " + name);

                Intent i = new Intent(c, ItemDisplayDetails.class);
                i.putExtra("username", username);
                i.putExtra("id", itemID);
                c.startActivity(i);

            }
        });
    }

}