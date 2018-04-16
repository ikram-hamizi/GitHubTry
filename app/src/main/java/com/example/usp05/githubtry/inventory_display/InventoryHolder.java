package com.example.usp05.githubtry.inventory_display;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.usp05.githubtry.R;

/**
 * Created by nathan on 4/8/18.
 */

public class InventoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    final CheckedTextView ctvItem;
    final TextView tvLocation;
    final TextView tvQuantity;

    private ItemClickListener mListener;


    public InventoryHolder(View itemView) {
        super(itemView);
        ctvItem = itemView.findViewById(R.id.CTV_item);
        tvLocation = itemView.findViewById(R.id.TV_location);
        tvQuantity = itemView.findViewById(R.id.TV_quantity);
        ctvItem.setOnClickListener(this);
        tvLocation.setOnClickListener(this);
        tvQuantity.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        mListener.onItemClick(v,getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener listener) {
        mListener = listener;
    }
}
