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
import com.example.usp05.githubtry.data_model.DBItemsHelper;
import com.example.usp05.githubtry.inventory_display.InventoryActivity;
import com.example.usp05.githubtry.user_handling.UserHandler;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nathan on 4/4/18.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class FilterActivity extends Activity {

    private UserHandler UH = UserHandler.getInstance();
    private String username = UH.getUsername();
    private static final String TYPE_FILTERS = "typeFilters";
    private static final String LOCATION_FILTERS = "locationFilters";
    FilterAdapter locationFilterAdapter;
    FilterAdapter typeFilterAdapter;

    private final DBItemsHelper filterDB = new DBItemsHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_items);


        locationFilterAdapter = new FilterAdapter(this, getLocationFilters());
        typeFilterAdapter = new FilterAdapter(this, getTypeFilters());

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
                i.putExtra(FilterActivity.TYPE_FILTERS, (Serializable) typeFilterAdapter.checkedFilters);
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
        RV_Type.setAdapter(typeFilterAdapter);
    }

    private List<String> getTypeFilters() {
        return filterDB.getTypes();
    }

    private List<String> getLocationFilters() {
        return filterDB.getLocations();
    }
}
