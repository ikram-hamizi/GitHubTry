package com.example.usp05.githubtry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usp05.githubtry.DataModel.DBItemsHelper;
import com.example.usp05.githubtry.DataModel.Item;

import static com.example.usp05.githubtry.R.layout.activity_edit_item;

public class EditItemActivity extends AppCompatActivity
{
    private int itemID;
    DBItemsHelper db_helper = new DBItemsHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        itemID = getIntent().getIntExtra("id", 0);
    }

    //ACTION
//    public void onSaveClick (View view)
//    {
//        String item_name = ((EditText) findViewById(R.id.ET_name)).getText().toString();
//
//        try {
//            int item_quantity = Integer.parseInt(((EditText) findViewById(R.id.ET_quantity)).getText().toString());
//
//            String item_location = ((EditText) findViewById(R.id.ET_location)).getText().toString();
//            String item_datepurch = ((EditText) findViewById(R.id.ET_datepurch)).getText().toString();
//            //String item_dateexpired = ((EditText) findViewById(R.id.ET_dateexpired)).getText().toString();
//            String item_price = ((EditText) findViewById(R.id.ET_price)).getText().toString();
//            String item_category = ((EditText) findViewById(R.id.ET_category)).getText().toString();
//            String item_note = ((EditText) findViewById(R.id.ET_note)).getText().toString();
//
//            Item newItem = new Item(item_name, item_location, item_category, item_datepurch, "no date of exp",
//                    item_note, item_quantity);
//
//            db_helper.insertItem(newItem); //Insert Item to DB //<- App crashes
//            db_helper.isInsertedToast(EditItemActivity.this).show(); //<- App crashes before it shows up
//
//        } catch(NumberFormatException e)
//        {
//            //Wrong input (quantity is not an int)
//            Toast not_int_popup = Toast.makeText(EditItemActivity.this, "Wrong input, please enter an integer for quantity", Toast.LENGTH_SHORT);
//            not_int_popup.show(); //<- Working Properly.
//        }
//    }
}

//You can do "File" -> "Invalidate Caches...", and select "Invalidate and Restart" option to fix this.

