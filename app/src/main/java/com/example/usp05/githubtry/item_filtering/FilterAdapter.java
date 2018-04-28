package com.example.usp05.githubtry.item_filtering;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.usp05.githubtry.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by nathan on 4/4/18.
 */

@SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
class FilterAdapter extends RecyclerView.Adapter<FilterHolder>{

    private final Context c;
    private final List<String> filters;
    final Collection<String> checkedFilters = new ArrayList<>();

    public FilterAdapter(Context c, List<String> filters) {
        this.c = c;
        this.filters = filters;
    }

    @Override
    public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_checkbox_layout,parent,false);
        return new FilterHolder(v);
    }

    @Override
    public void onBindViewHolder(FilterHolder holder, int position) {
        holder.chk.setText(filters.get(position));

        holder.setFilterClickListener(new FilterClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                CheckBox chk = (CheckBox) v;

                // TODO: Implement "All" checkbox

                if(chk.isChecked()){
                    if("All".equals(chk.getText())) {
                        //noinspection CollectionAddedToSelf
                        checkedFilters.removeAll(checkedFilters);

                    } else {
                        checkedFilters.add(filters.get(pos));
                    }
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
