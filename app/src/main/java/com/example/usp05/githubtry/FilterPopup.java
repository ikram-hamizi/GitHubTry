package com.example.usp05.githubtry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.example.usp05.githubtry.DataModel.DBItemsHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nathan on 4/3/18.
 */

public class FilterPopup extends Activity {
    private RecyclerView RVtype, RVlocation;
    private RecyclerView.Adapter typeAdapter,locationAdapter;
    private RecyclerView.LayoutManager typeLayout, locationLayout;

    DBItemsHelper filterDB = new DBItemsHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_items);

        setTypeFilterCheckboxes(RVtype,R.id.RV_filtertype);
        setLocationFilterCheckboxes(RVlocation,R.id.RV_filterlocation);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.9),(int)(height*0.9));

        Button fb = (Button) findViewById(R.id.Bgofilter);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(FilterPopup.this,Inventory.class));

                finish();
            }
        });
    }

    private void setLocationFilterCheckboxes(RecyclerView RV, int RVid) {
        RV = (RecyclerView) findViewById(RVid);

        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        //RV.setHasFixedSize(true);

        // use a linear layout manager
        locationLayout = new LinearLayoutManager(this);
        RV.setLayoutManager(locationLayout);

        // define an adapter
        locationAdapter = new FilterAdapter(filterDB.getLocations());
        RV.setAdapter(locationAdapter);
    }

    private void setTypeFilterCheckboxes(RecyclerView RV, int RVid){
        RV = (RecyclerView) findViewById(RVid);

        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        //RV.setHasFixedSize(true);

        // use a linear layout manager
        typeLayout = new LinearLayoutManager(this);
        RV.setLayoutManager(typeLayout);

        // define an adapter
        typeAdapter = new FilterAdapter(filterDB.getTypes());
        RV.setAdapter(typeAdapter);
    }
}
