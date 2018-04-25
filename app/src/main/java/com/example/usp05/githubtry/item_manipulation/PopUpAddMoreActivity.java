package com.example.usp05.githubtry.item_manipulation;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import com.example.usp05.githubtry.R;


import java.util.Calendar;

public class PopUpAddMoreActivity extends AppCompatActivity {

    private static final String TAG = "PopUpAddMoreActivity";
    private TextView savedPurchDate;
    private TextView savedEXPDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DatePickerDialog.OnDateSetListener dateSetListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_add_more);
        savedPurchDate = (TextView) findViewById(R.id.TV_DatePur);
        savedEXPDate = (TextView) findViewById(R.id.TV_DateExp);

        savedEXPDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //1. Create calendar object
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                //2. Create Date Picker Dialog object
                DatePickerDialog dialog = new DatePickerDialog(PopUpAddMoreActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener2, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                //3. Show
                dialog.show();
            }
        });

        dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //Jan = 0, Dec = 11
                month += 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                savedEXPDate.setText(date);
            }
        };

        savedPurchDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //1. Create calendar object
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                //2. Create Date Picker Dialog object
                DatePickerDialog dialog = new DatePickerDialog(PopUpAddMoreActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                //3. Show
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //Jan = 0, Dec = 11
                month += 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                savedPurchDate.setText(date);
            }
        };
    }

    public void onClickSave(View view)
    {
        //1. Save Quantity, Price for each, Date Purch, Date Exp

        //2. Add them to DB

        //3. Go back to previous Activity
        finishActivity(0);
        onBackPressed();
    }
}
