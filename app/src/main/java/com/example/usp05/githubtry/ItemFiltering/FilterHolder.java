package com.example.usp05.githubtry.ItemFiltering;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.example.usp05.githubtry.R;

/**
 * Created by nathan on 4/4/18.
 */

public class FilterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CheckBox chk;

    ItemClickListener itemClickListener;

    public FilterHolder(View itemView) {
        super(itemView);

        chk = (CheckBox) itemView.findViewById(R.id.CB_filter);

        chk.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener=ic;
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());
    }
}
