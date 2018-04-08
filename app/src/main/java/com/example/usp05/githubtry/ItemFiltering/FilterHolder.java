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

    FilterClickListener filterClickListener;

    public FilterHolder(View itemView) {
        super(itemView);

        chk = (CheckBox) itemView.findViewById(R.id.CB_filter);

        chk.setOnClickListener(this);
    }

    public void setFilterClickListener(FilterClickListener ic){
        this.filterClickListener =ic;
    }

    @Override
    public void onClick(View v) {
        this.filterClickListener.onItemClick(v,getLayoutPosition());
    }
}
