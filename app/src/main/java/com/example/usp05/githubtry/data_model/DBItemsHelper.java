package com.example.usp05.githubtry.data_model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.usp05.githubtry.user_handling.UserHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by cstech on 3/24/18.
 */

public class DBItemsHelper extends SQLiteOpenHelper{

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DATABASE_MYINVENTORYAPP";

    private static final String ITEM_TABLE_NAME = "ITEM";

    private static final String ITEM_COL_ID = "ID";
    private static final String ITEM_COL_USERNAME = "USERNAME";
    private static final String ITEM_COL_NAME = "NAME";
    private static final String ITEM_COL_LOCATION = "LOCATION";
    private static final String ITEM_COL_TYPE = "TYPE";
    private static final String ITEM_COL_DATE_PURCHASED = "DATE_PURCHASED";
    private static final String ITEM_COL_DATE_EXPIRED = "DATE_EXPIRED";
    private static final String ITEM_COL_QUANTITY = "QUANTITY";
    private static final String ITEM_COL_AVERAGE_PRICE = "AVERAGE_PRICE";
    private static final String ITEM_COL_NOTES = "NOTES";

    private long row_nInsert;
    private SQLiteDatabase appDB;

    private static final String TABLE_CREATE = "CREATE TABLE ITEM (ID INTEGER PRIMARY KEY, USERNAME TEXT NOT NULL, " +
            "NAME TEXT NOT NULL , LOCATION TEXT NOT NULL , TYPE TEXT NOT NULL , DATE_PURCHASED TEXT NOT NULL , " +
            "DATE_EXPIRED TEXT NOT NULL , QUANTITY INT NOT NULL, AVERAGE_PRICE FLOAT NOT NULL , NOTES TEXT NOT NULL);";

    UserHandler UH;

    public DBItemsHelper(Context context) {
        super(context, DBItemsHelper.DB_NAME, null, DBItemsHelper.DB_VERSION);
        UH = UserHandler.getInstance();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBItemsHelper.TABLE_CREATE);
        appDB = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String drop_query = "DROP TABLE IF EXISTS "+ DBItemsHelper.ITEM_TABLE_NAME;
        appDB.execSQL(drop_query);
        onCreate(db);
    }

    public void insertItem(Item item)
    {
        row_nInsert = -1; //reset

        appDB = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBItemsHelper.ITEM_COL_USERNAME, UH.getUsername());
        values.put(DBItemsHelper.ITEM_COL_NAME, item.getName());
        values.put(DBItemsHelper.ITEM_COL_LOCATION, item.getLocation());
        values.put(DBItemsHelper.ITEM_COL_TYPE, item.getType());
        values.put(DBItemsHelper.ITEM_COL_DATE_PURCHASED, item.getDate_purchased());
        values.put(DBItemsHelper.ITEM_COL_DATE_EXPIRED, item.getDate_expired());
        values.put(DBItemsHelper.ITEM_COL_QUANTITY, item.getQuantity());
        values.put(DBItemsHelper.ITEM_COL_AVERAGE_PRICE, item.getAverage_price());
        values.put(DBItemsHelper.ITEM_COL_NOTES, item.getNotes());

        row_nInsert = appDB.insert(DBItemsHelper.ITEM_TABLE_NAME, null, values); //Insert Item to DB
        appDB.close();
    }

    public Toast insertedToast(Context context)
    {
        Toast db_insert;
        if (row_nInsert == -1) {
            db_insert = Toast.makeText(context, "Could not insert item...", Toast.LENGTH_SHORT);
        } else {
            db_insert = Toast.makeText(context, "Item inserted successfully!", Toast.LENGTH_SHORT);
        }
        //show db_insert in AddItemActivity
        return db_insert;
    }

    @Nullable
    public Item searchItem(String username, int search_id)
    {
        // TODO: Fix username being null problem

        appDB = getReadableDatabase();
        String get_row_query = "SELECT * FROM "+ ITEM_TABLE_NAME + " WHERE USERNAME = ? and ID = ?";
        Cursor found = appDB.rawQuery(get_row_query, new String[] {UH.getUsername(), String.valueOf(search_id)});
//        String get_row_query = "SELECT * FROM "+ DBItemsHelper.ITEM_TABLE_NAME + " WHERE ID = ?";
//        Cursor found = appDB.rawQuery(get_row_query, new String[] {String.valueOf(search_id)});

        if (found.moveToFirst() && (found != null))
        {
            do {
                String id = found.getString(0);
                if(search_id == Integer.parseInt(id))
                {
                    // USERNAME 0
                    // ID 1
                    // NAME 2
                    // LOCATION 3
                    // TYPE 4
                    // DATE_PURCHASED 5
                    // DATE_EXPIRED 6
                    // QUANTITY 7
                    // AVERAGE_PRICE 8
                    // NOTES 9

                    //public Item(String username, String name, String location, String type, String date_purchased, String date_expired, String notes, int quantity /*.float price*/)
                    return new Item(found.getString(1), found.getString(2), found.getString(3), found.getString(4), found.getString(5),
                            found.getString(6), found.getString(9), Integer.parseInt(found.getString(7)));
                }
            }while(found.moveToNext());
        }
        found.close();
        return null;
    }

    public void deleteItem(String username, int delete_id)
    {
        appDB = getWritableDatabase();
        String delete_row_query = "DELETE FROM "+ ITEM_TABLE_NAME + " WHERE USERNAME = '" + UH.getUsername() + "' and ID = " + delete_id + ";";
//        String delete_row_query = "DELETE FROM "+ DBItemsHelper.ITEM_TABLE_NAME + " WHERE ID = " + delete_id + ';';
        appDB.execSQL(delete_row_query);
    }

    public void editItem(int edited_item_id, String editedColumnName, String newInfo)
    {
        appDB = getWritableDatabase();
        String edit_row_query = "UPDATE "+ DBItemsHelper.ITEM_TABLE_NAME + " SET " + editedColumnName + " = '" + newInfo +
                "' WHERE ID = " + edited_item_id + ';';
        appDB.execSQL(edit_row_query);
    }

    // gets all data from items database and displays in the ListView in inventory screen
    public Cursor getItems(String username) {
        appDB = getReadableDatabase();
        String query = "select * from " + ITEM_TABLE_NAME + " where " + ITEM_COL_USERNAME + " = ? order by " + ITEM_COL_NAME;
//        Cursor cursor = appDB.rawQuery(query, new String[]{username});
//        String query = "select * from " + DBItemsHelper.ITEM_TABLE_NAME;
        return appDB.rawQuery(query, new String[]{UH.getUsername()});
    }

    public Cursor getItemLocationDetails(String itemName) {
        appDB = getReadableDatabase();

        String query = "SELECT " + ITEM_COL_LOCATION +", "+ ITEM_COL_QUANTITY +", "+ ITEM_COL_DATE_EXPIRED +", "+ ITEM_COL_DATE_PURCHASED
                + " FROM " + ITEM_TABLE_NAME + " WHERE " + ITEM_COL_NAME + " = ? ;";

        return appDB.rawQuery(query, new String[]{itemName});

//        SQLiteQueryBuilder query = new SQLiteQueryBuilder();
//
//        String[] projectionIn = new String[]{ITEM_COL_LOCATION,
//                ITEM_COL_QUANTITY, ITEM_COL_DATE_EXPIRED, ITEM_COL_DATE_PURCHASED};
//
//        String selection = ITEM_COL_NAME + " = ?";
//        String[] selectionArgs = new String[]{itemName};
//
//        return query.query(appDB, projectionIn, selection, selectionArgs, null, null, null);
    }

    public Cursor getFilteredItems(String username, Collection<String> typeFilters, Collection<String> locationFilters){

        appDB = getReadableDatabase();
        StringBuffer filterQuery = new StringBuffer();
        int lastOR;

        filterQuery.append("SELECT * FROM " + ITEM_TABLE_NAME + " WHERE (" + ITEM_COL_USERNAME + " = '");
        filterQuery.append(UH.getUsername());
        filterQuery.append("')");

        if((typeFilters != null) && !typeFilters.isEmpty()) {
            filterQuery.append(" AND (");

            for (String str : typeFilters) {
                filterQuery.append(ITEM_COL_TYPE + " = '").append(str).append("' OR ");
            }

            lastOR = filterQuery.lastIndexOf(" OR");
            filterQuery.delete(lastOR,lastOR+4);
            filterQuery.append(')');
        }

        if((locationFilters != null) && !locationFilters.isEmpty()) {
            filterQuery.append(" AND (");

            for (String str : locationFilters) {
                filterQuery.append(ITEM_COL_LOCATION + " = '").append(str).append("' OR ");
            }

            lastOR = filterQuery.lastIndexOf(" OR");
            filterQuery.delete(lastOR,lastOR+4);
            filterQuery.append(')');
        }

        filterQuery.append(';');

        return appDB.rawQuery(filterQuery.toString(), null);
    }

    public List<String> getTypes(){
        appDB = getReadableDatabase();
        String query = "select " + DBItemsHelper.ITEM_COL_TYPE + " from " + DBItemsHelper.ITEM_TABLE_NAME;
        Cursor cursor = appDB.rawQuery(query,null);

        List<String> results = new ArrayList<>();

        // TODO: Implement "All" checkbox
//        results.add("All");

        if(cursor.moveToFirst()){
            do {
                String newString = cursor.getString(0);
                if(!newString.isEmpty()) {
                    int rs = results.size();
                    boolean isNew = true;

                    for (int i = 0; i < rs; i++) {
                        if (results.get(i).trim().equalsIgnoreCase(newString))
                            isNew = false;
                    }
                    if (isNew)
                        results.add(newString);
                }
            } while(cursor.moveToNext());
        }

        cursor.close();
        return results;
    }

    public List<String> getLocations() {
        appDB = getReadableDatabase();
        String query = "select " + DBItemsHelper.ITEM_COL_LOCATION + " from " + DBItemsHelper.ITEM_TABLE_NAME;
        Cursor cursor = appDB.rawQuery(query,null);

        List<String> results = new ArrayList<>();

        // TODO: Implement "All" checkbox
//        results.add("All");

        if(cursor.moveToFirst()){
            do {
                String newString = cursor.getString(0);
                if(!newString.isEmpty()) {
                    int rs = results.size();
                    boolean isNew = true;

                    for (int i = 0; i < rs; i++) {
                        if (results.get(i).trim().equalsIgnoreCase(newString))
                            isNew = false;
                    }
                    if (isNew)
                        results.add(newString);
                }
            } while(cursor.moveToNext());
        }

        cursor.close();
        return results;
    }

// --Commented out by Inspection START (4/16/18 9:54 PM):
//    public Cursor getItemID(String username, String name) {
//
//        appDB = getReadableDatabase();
////        String query = "select " + ITEM_COL_ID + " from " + ITEM_TABLE_NAME + " where " + ITEM_COL_USERNAME + " = ? and " + ITEM_COL_NAME + " = ?;";
////        Cursor cursor = appDB.rawQuery(query, new String[]{username, name});
//        String query = "select " + DBItemsHelper.ITEM_COL_ID + " from " + DBItemsHelper.ITEM_TABLE_NAME + " where " + DBItemsHelper.ITEM_COL_NAME + " = ?;";
//        return appDB.rawQuery(query, new String[]{name});
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)

//    public List<InventoryItemDisplay> getRelevantItems() {
//
//    }
}
