package com.example.usp05.githubtry.item_manipulation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usp05.githubtry.data_model.DBItemsHelper;
import com.example.usp05.githubtry.data_model.Item;
import com.example.usp05.githubtry.inventory_display.InventoryActivity;
import com.example.usp05.githubtry.R;
import com.example.usp05.githubtry.user_handling.UserHandler;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by minh on 3/24/18.
 */

public class AddItemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private final DBItemsHelper db_helper = new DBItemsHelper(this);
    private UserHandler UH = UserHandler.getInstance();
    private String username = UH.getUsername();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
    }

    //ACTION
    public void onSaveClick (View view)
    {
        String item_name = ((TextView) findViewById(R.id.ET_name)).getText().toString().toUpperCase();
        try {
            String item_location = ((TextView) findViewById(R.id.ET_location)).getText().toString();
            String item_category = ((TextView) findViewById(R.id.ET_category)).getText().toString();
            String item_datePurchased = ((TextView) findViewById(R.id.ET_datePurchased)).getText().toString();
            String item_dateExpired = ((TextView) findViewById(R.id.ET_dateExpired)).getText().toString();
            String item_price = ((TextView) findViewById(R.id.ET_price)).getText().toString();
            String item_note = ((TextView) findViewById(R.id.ET_note)).getText().toString();
            int item_quantity = Integer.parseInt(((TextView) findViewById(R.id.ET_quantity)).getText().toString());

            if((item_name == "") || (String.valueOf(item_quantity) == "")) {
                Toast message = Toast.makeText(this, "Name and quantity must be filled", Toast.LENGTH_SHORT);
                message.show();
            }

            Item newItem = new Item(username, item_name, item_location, item_category, item_datePurchased, item_dateExpired,
                    item_note, item_quantity);

            db_helper.insertItem(newItem); //Insert Item to DB
            db_helper.insertedToast(this).show();
//            Intent i = new Intent(AddItemActivity.this, Inventory.class);
            Intent i = new Intent(this, InventoryActivity.class);
            startActivity(i);
        }
        catch(NumberFormatException ignored) {
            //Wrong input (quantity is not an int)
            Toast not_int_popup = Toast.makeText(this, "Wrong input, please enter an integer for quantity", Toast.LENGTH_SHORT);
            not_int_popup.show(); //<- Working Properly.
        }
    }

    /**
     * To receive a callback when the user sets the date.
     * @param view
     * @param year
     * @param month
     * @param day
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }

    /**
     * This callback method, call DatePickerFragment class,
     * DatePickerFragment class returns calendar view.
     * @param view
     */
    public void datePicker(View view){

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "date");
    }

    /**
     * To set date on TextView
     * @param calendar
     */
    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) findViewById(R.id.showDate)).setText(dateFormat.format(calendar.getTime()));

    }

    /**
     * Create a DatePickerFragment class that extends DialogFragment.
     * Define the onCreateDialog() method to return an instance of DatePickerDialog
     */
    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);
        }

    }

public void onEditSaveClick(View view) {}
public void onEditDeleteClick(View view) {}}

//You can do "File" -> "Invalidate Caches...", and select "Invalidate and Restart" option to fix this.

