package com.example.usp05.githubtry.item_filtering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.example.usp05.githubtry.R;
import com.example.usp05.githubtry.data_model.ItemDatabaseSingleton;
import com.example.usp05.githubtry.inventory_display.InventoryActivity;

import java.io.Serializable;

/**
 * Created by nathan on 4/4/18.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class FilterActivity extends Activity {

    private static final String TYPE_FILTERS = "typeFilters";
    private static final String LOCATION_FILTERS = "locationFilters";
    FilterAdapter locationFilterAdapter;
    FilterAdapter categoryFilterAdapter;

    private ItemDatabaseSingleton IDS = ItemDatabaseSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_items);

        locationFilterAdapter = new FilterAdapter(this, IDS.getLocations());
        categoryFilterAdapter = new FilterAdapter(this, IDS.getCategories());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.9),(int)(height*0.9));

        Button fb = findViewById(R.id.B_filter);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FilterActivity.this,InventoryActivity.class);

                //noinspection DuplicateStringLiteralInspection
                i.putExtra(FilterActivity.TYPE_FILTERS, (Serializable) categoryFilterAdapter.checkedFilters);
                //noinspection DuplicateStringLiteralInspection
                i.putExtra(FilterActivity.LOCATION_FILTERS, (Serializable) locationFilterAdapter.checkedFilters);

                startActivity(i);
            }
        });

        RecyclerView RV_Location = findViewById(R.id.RV_filterLocation);
        RV_Location.setLayoutManager(new LinearLayoutManager(this));
        RV_Location.setItemAnimator(new DefaultItemAnimator());
        RV_Location.setAdapter(locationFilterAdapter);

        RecyclerView RV_Type = findViewById(R.id.RV_filterType);
        RV_Type.setLayoutManager(new LinearLayoutManager(this));
        RV_Type.setItemAnimator(new DefaultItemAnimator());
        RV_Type.setAdapter(categoryFilterAdapter);
    }
}
