package com.example.usp05.githubtry.ItemFiltering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.usp05.githubtry.DataModel.DBItemsHelper;
import com.example.usp05.githubtry.InventoryDisplay.InventoryActivity;
import com.example.usp05.githubtry.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nathan on 4/4/18.
 */

public class FilterActivity extends Activity {

    StringBuffer sb = null;
    FilterAdapter locationFilterAdapter, typeFilterAdapter;
    private String username;

    DBItemsHelper filterDB = new DBItemsHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_items);

        username = getIntent().getStringExtra("username");

        locationFilterAdapter = new FilterAdapter(this, getLocationFilters());
        typeFilterAdapter = new FilterAdapter(this,getTypeFilters());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.9),(int)(height*0.9));

        Button fb = (Button) findViewById(R.id.Bgofilter);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(FilterActivity.this,Inventory.class);
                Intent i = new Intent(FilterActivity.this,InventoryActivity.class);

                i.putExtra("username", username);
                i.putExtra("typeFilters",(ArrayList<String>)typeFilterAdapter.checkedFilters);
                i.putExtra("locationFilters",(ArrayList<String>)locationFilterAdapter.checkedFilters);

                startActivity(i);
//                finish();
            }
        });

        RecyclerView RV_Location = (RecyclerView) findViewById(R.id.RV_filterlocation);
        RV_Location.setLayoutManager(new LinearLayoutManager(this));
        RV_Location.setItemAnimator(new DefaultItemAnimator());
        RV_Location.setAdapter(locationFilterAdapter);

        RecyclerView RV_Type = (RecyclerView) findViewById(R.id.RV_filtertype);
        RV_Type.setLayoutManager(new LinearLayoutManager(this));
        RV_Type.setItemAnimator(new DefaultItemAnimator());
        RV_Type.setAdapter(typeFilterAdapter);
    }

    private List<String> getTypeFilters() {
        List<String> results = new ArrayList<String>();
        results = filterDB.getTypes();
        return results;
    }

    private List<String> getLocationFilters() {
        List<String> results = new ArrayList<String>();
        results = filterDB.getLocations();
        return results;
    }
}
