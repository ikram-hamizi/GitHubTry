package com.example.usp05.githubtry.InventoryDisplay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usp05.githubtry.InventoryDisplay.ItemClickListener;
import com.example.usp05.githubtry.InventoryDisplay.InventoryHolder;
import com.example.usp05.githubtry.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nathan on 4/8/18.
 */

//public class InventoryAdapter extends CursorRecyclerViewAdapter<InventoryAdapter.ViewHolder>{
//
//    Context c;
//    List<String> filters;
//    List<String> checkedFilters = new ArrayList<String>();
//
//    public InventoryAdapter(Context c, Cursor cursor) {
//        super(c,cursor);
////        this.c = c;
////        this.filters = filters;
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView mTextView;
//        public ViewHolder(View view) {
//            super(view);
//            mTextView = view.findViewById(R.id.text);
//        }
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_rv_layout,parent,false);
//        ViewHolder holder = new ViewHolder(v);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, final int position, Cursor cursor) {
//        holder.ctvItem.setText(filters.get(position));
//        holder.tvLocation.setText(filters.get(position));
//        holder.tvQuantity.setText(filters.get(position));
//
//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onItemClick(View v, int pos) {
//                CheckedTextView ctvItem = (CheckedTextView) v;
//                TextView tvLocation = (TextView) v;
//                TextView tvQuantity = (TextView) v;
//
//                if(ctvItem.isChecked()){
//                    checkedFilters.add(filters.get(pos));
//                } else {
//                    checkedFilters.remove(filters.get(pos));
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return filters.size();
//    }
//}

public class InventoryAdapter extends RecyclerView.Adapter<InventoryHolder>{

    Context c;
    List<String> filters;
    List<String> checkedFilters = new ArrayList<String>();

    public InventoryAdapter(Context c, List<String> filters) {
        this.c = c;
        this.filters = filters;
    }

    @Override
    public InventoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_rv_layout,parent,false);
        InventoryHolder holder = new InventoryHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(InventoryHolder holder, final int position) {
        holder.ctvItem.setText(filters.get(position));
        holder.tvLocation.setText(filters.get(position));
        holder.tvQuantity.setText(filters.get(position));

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                CheckedTextView ctvItem = (CheckedTextView) v;
                TextView tvLocation = (TextView) v;
                TextView tvQuantity = (TextView) v;

                if(ctvItem.isChecked()){
                    checkedFilters.add(filters.get(pos));
                } else {
                    checkedFilters.remove(filters.get(pos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }
}

