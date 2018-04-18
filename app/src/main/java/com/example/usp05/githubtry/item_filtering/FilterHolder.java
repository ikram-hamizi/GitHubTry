package com.example.usp05.githubtry.item_filtering;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.example.usp05.githubtry.R;

/**
 * Created by nathan on 4/4/18.
 */

public class FilterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    final CheckBox chk;

    private FilterClickListener filterClickListener;

    public FilterHolder(View itemView) {
        super(itemView);

        chk = itemView.findViewById(R.id.CB_filter);

        chk.setOnClickListener(this);
    }

    public void setFilterClickListener(FilterClickListener ic){
        filterClickListener =ic;
    }

    @Override
    public void onClick(View v) {
        filterClickListener.onItemClick(v,getLayoutPosition());
    }
}
