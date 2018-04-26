package com.example.usp05.githubtry.item_manipulation;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usp05.githubtry.R;
import com.example.usp05.githubtry.data_model.DBItemsHelper;
import com.example.usp05.githubtry.data_model.Item;
import com.example.usp05.githubtry.inventory_display.InventoryActivity;


import java.util.Calendar;

public class PopUpAddMoreActivity extends AppCompatActivity {

    private static final String TAG = "PopUpAddMoreActivity";
    private TextView savedPurchDate;
    private TextView savedEXPDate;
    private DatePickerDialog.OnDateSetListener dateSetListenerPURCH;
    private DatePickerDialog.OnDateSetListener dateSetListenerEXP;
    private int dd, mm, yyyy;
    private boolean isValidDates = false;
    private final DBItemsHelper db_helper = new DBItemsHelper(this);
    private String username, itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_add_more);

        //Get the Username from previous activity
        username = getIntent().getStringExtra("username");
        itemName = getIntent().getStringExtra("itemName");

        //1- DATE OF PURCHASE INPUT
        savedPurchDate = (TextView) findViewById(R.id.TV_DatePur);
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
                DatePickerDialog dialog = new DatePickerDialog(PopUpAddMoreActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListenerPURCH, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                //3. Show
                dialog.show();
            }
        });

        dateSetListenerPURCH = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //Jan = 0, Dec = 11
                month += 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + day + "/" + year);
                dd = day; mm = month; yyyy = year;
                String date = month + "/" + day + "/" + year;
                savedPurchDate.setText(date);
            }
        };


        //2- DATE OF EXPIRATION
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
                DatePickerDialog dialog = new DatePickerDialog(PopUpAddMoreActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListenerEXP, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                //3. Show
                dialog.show();
            }
        });

        dateSetListenerEXP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //Jan = 0, Dec = 11
                month += 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + day + "/" + year);

                if(year<yyyy || (year>=yyyy && month<mm) || (year>=yyyy && month>=mm && day<dd)) //INVALID DATES
                {
                    isValidDates = false;
                }
                else //VALID DATES
                {
                    isValidDates = true;
                    String date = month + "/" + day + "/" + year;
                    savedEXPDate.setText(date);
                }
            }
        };
    }

    public void onClickSave(View view)
    {
        //1. Save Quantity, Price for each, Date Purch, Date Exp
        if(!isValidDates)
        {
            Toast datesInvalid = Toast.makeText(this, "Expiration and Purchase Dates are not valid", Toast.LENGTH_SHORT);
            datesInvalid.show();
        }
        else
        {
            addMoreOfItemX();
        }
        //2. Add them to DB

        //3. Go back to previous Activity
        finishActivity(0);
        onBackPressed();
    }

    private void addMoreOfItemX()
    {
        try {
            String item_datePurchased = ((TextView) findViewById(R.id.TV_DatePur)).getText().toString();
            String item_dateExpired = ((TextView) findViewById(R.id.TV_DateExp)).getText().toString();
            int item_quantity = Integer.parseInt(((TextView) findViewById(R.id.ET_Quantity)).getText().toString());

            if((itemName == null) || (String.valueOf(item_quantity) == null)||(itemName == "") || (String.valueOf(item_quantity) == "")) {
                Toast message = Toast.makeText(this, "Name and quantity must be filled", Toast.LENGTH_SHORT);
                message.show();
            }
            else {
                Item newItem = new Item(username, itemName, "other", "NA", item_datePurchased, item_dateExpired,
                        "No notes", item_quantity);

                db_helper.insertItem(newItem); //Insert Item to DB
                db_helper.insertedToast(this).show();

                Intent i = new Intent(this, InventoryActivity.class);
                startActivity(i);
            }
        }
        catch(NumberFormatException ignored) {
            //Wrong input (quantity is not an int)
            Toast not_int_popup = Toast.makeText(this, "Wrong input, please enter an integer for quantity", Toast.LENGTH_SHORT);
            not_int_popup.show(); //<- Working Properly.
        }
    }
}
