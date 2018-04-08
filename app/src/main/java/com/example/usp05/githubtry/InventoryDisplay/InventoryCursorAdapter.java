package com.example.usp05.githubtry.InventoryDisplay;

/**
 * Created by nathan on 4/8/18.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.usp05.githubtry.R;

/**
 * Created by skyfishjy on 10/31/14.
 */
public class InventoryCursorAdapter extends CursorRecyclerViewAdapter<InventoryCursorAdapter.ViewHolder>{

    public InventoryCursorAdapter(Context context, Cursor cursor){
        super(context,cursor);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckedTextView ctvItem;
        TextView tvLocation;
        TextView tvQuantity;

        public ViewHolder(View view) {
            super(view);
            ctvItem = view.findViewById(R.id.CTV_item);
            tvLocation = view.findViewById(R.id.TV_location);
            tvQuantity = view.findViewById(R.id.TV_quantity);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inventory_rv_layout, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        InventoryItemDisplay inventoryItem = InventoryItemDisplay.fromCursor(cursor);

        viewHolder.ctvItem.setText(inventoryItem.getItemName());
        viewHolder.tvLocation.setText(inventoryItem.getItemLocation());
        viewHolder.tvQuantity.setText(inventoryItem.getItemQuantityString());

//        setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onItemClick(View v, int pos) {
//                CheckedTextView ctvItem = (CheckedTextView) v;
//                TextView tvLocation = (TextView) v;
//                TextView tvQuantity = (TextView) v;
//
//                if(ctvItem.isChecked()){
////                    checkedFilters.add(filters.get(pos));
//                } else {
////                    checkedFilters.remove(filters.get(pos));
//                }
//            }
//        });
    }
}