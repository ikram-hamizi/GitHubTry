package com.example.usp05.githubtry.inventory_display;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usp05.githubtry.R;
import com.example.usp05.githubtry.data_model.Item;
import com.example.usp05.githubtry.item_manipulation.ItemDisplayDetails;

import java.util.ArrayList;

/**
 * Created by minh on 4/25/18.
 */

public class InventoryAdapter extends RecyclerView.Adapter<InventoryHolder> {

    private ArrayList<InventoryItemDisplay> items;
    Context c;

//    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
//        public TextView ctvItem;
//        public TextView tvLocation;
//        public TextView tvQuantity;
//
//        public ExampleViewHolder(View itemView) {
//            super(itemView);
//            ctvItem = itemView.findViewById(R.id.CTV_item);
//            tvLocation = itemView.findViewById(R.id.TV_location);
//            tvQuantity = itemView.findViewById(R.id.TV_quantity);
//
//        }
//    }

    public InventoryAdapter(Context c, ArrayList<InventoryItemDisplay> items) {
        this.c = c;
        this.items = items;
    }

//    @Override
//    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_rv_layout,
//                parent, false);
//        ExampleViewHolder evh = new ExampleViewHolder(v);
//        return evh;
//    }

    @Override
    public void onBindViewHolder(InventoryHolder holder, int position) {
        final InventoryItemDisplay currentItem = items.get(position);

        holder.ctvItem.setText(currentItem.getItemName());
        holder.tvLocation.setText(currentItem.getItemLocation());
        holder.tvQuantity.setText((currentItem.getItemQuantityString()));

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                String name = currentItem.getItemName();
                int itemID = currentItem.getItemID();

                if (Log.isLoggable(ContentValues.TAG, Log.DEBUG)) {
                    Log.d(ContentValues.TAG, "onItemClick: You clicked on " + name);
                }

                Intent i = new Intent(c, ItemDisplayDetails.class);
                i.putExtra("id", itemID);
                c.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public InventoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_rv_layout,parent,false);
        return new InventoryHolder(v);
    }
}
