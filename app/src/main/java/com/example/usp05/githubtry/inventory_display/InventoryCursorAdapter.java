package com.example.usp05.githubtry.inventory_display;

/**
 * Created by nathan on 4/8/18.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usp05.githubtry.item_manipulation.ItemDisplayDetails;
import com.example.usp05.githubtry.R;


public class InventoryCursorAdapter extends CursorRecyclerViewAdapter<InventoryHolder> {

    private final Context c;
    private final Cursor cursor;

    public InventoryCursorAdapter(Context context, Cursor cursor){
        super(context,cursor);
        c = context;
        this.cursor = cursor;
    }

    static InventoryItemDisplay fromCursor(Cursor cursor) {

        InventoryItemDisplay iid = new InventoryItemDisplay();

        iid.setItemID(cursor.getInt(cursor.getColumnIndex("ID")));
        iid.setItemName(cursor.getString(cursor.getColumnIndex("NAME")));
        iid.setItemLocation(cursor.getString(cursor.getColumnIndex("LOCATION")));
        iid.setItemQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex("QUANTITY"))));

        cursor.moveToNext();

        return iid;
    }

    @Override
    public InventoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_rv_layout,parent,false);
        return new InventoryHolder(v);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    @Override
    public void onBindViewHolder(InventoryHolder viewHolder, Cursor cursor) {
        final InventoryItemDisplay inventoryItem = InventoryCursorAdapter.fromCursor(cursor);

        viewHolder.ctvItem.setText(inventoryItem.getItemName());
        viewHolder.tvLocation.setText(inventoryItem.getItemLocation());
        viewHolder.tvQuantity.setText(inventoryItem.getItemQuantityString());

        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                String name = inventoryItem.getItemName();
                int itemID = inventoryItem.getItemID();

                if (Log.isLoggable(ContentValues.TAG, Log.DEBUG)) {
                    Log.d(ContentValues.TAG, "onItemClick: You clicked on " + name);
                }

                Intent i = new Intent(c, ItemDisplayDetails.class);
                i.putExtra("id", itemID);
                c.startActivity(i);

            }
        });
    }

}