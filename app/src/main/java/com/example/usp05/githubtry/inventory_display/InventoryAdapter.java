package com.example.usp05.githubtry.inventory_display;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usp05.githubtry.R;
import com.example.usp05.githubtry.item_manipulation.ItemDisplayDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minh on 4/25/18.
 */

public class InventoryAdapter extends RecyclerView.Adapter<InventoryHolder> {

    private int pos = 0;

    private List<InventoryItemDisplay> items;
    Context c;

    public InventoryAdapter(Context c, ArrayList<InventoryItemDisplay> items) {
        this.c = c;
        this.items = items;
    }

    @Override
    public void onBindViewHolder(InventoryHolder holder, int position) {
//        pos = position;

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

        //        if(items.get(pos).getItemQuantity() <= 0) {
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.depleted_inventory_rv_layout,parent,false);
//        } else if(items.get(pos).haveExpired){
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expired_inventory_rv_layout,parent,false);
//        } else {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_rv_layout, parent, false);
//        }

        pos++;

//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_rv_layout,parent,false);
        return new InventoryHolder(v);
    }

    public void filterList(ArrayList<InventoryItemDisplay> filteredList) {
        items = filteredList;
        notifyDataSetChanged();
    }

}
