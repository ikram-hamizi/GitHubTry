package com.example.usp05.githubtry.InventoryDisplay;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.usp05.githubtry.R;

/**
 * Created by nathan on 4/8/18.
 */

public class InventoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CheckedTextView ctvItem;
    TextView tvLocation;
    TextView tvQuantity;

    ItemClickListener mListener;


    public InventoryHolder(View itemView) {
        super(itemView);
        ctvItem = (CheckedTextView) itemView.findViewById(R.id.CTV_item);
        tvLocation = (TextView) itemView.findViewById(R.id.TV_location);
        tvQuantity = (TextView) itemView.findViewById(R.id.TV_quantity);
        ctvItem.setOnClickListener(this);
        tvLocation.setOnClickListener(this);
        tvQuantity.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        this.mListener.onItemClick(v,getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.mListener = listener;
    }
}
