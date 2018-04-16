package com.example.usp05.githubtry.DataModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
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

    private long rownInsert;
    SQLiteDatabase appDB;

    private static final String TABLE_CREATE = "CREATE TABLE ITEM (ID INTEGER PRIMARY KEY, USERNAME TEXT NOT NULL, " +
            "NAME TEXT NOT NULL , LOCATION TEXT NOT NULL , TYPE TEXT NOT NULL , DATE_PURCHASED TEXT NOT NULL , " +
            "DATE_EXPIRED TEXT NOT NULL , QUANTITY INT NOT NULL, AVERAGE_PRICE FLOAT NOT NULL , NOTES TEXT NOT NULL);";

    public DBItemsHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.appDB = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String drop_query = "DROP TABLE IF EXISTS "+ ITEM_TABLE_NAME;
        appDB.execSQL(drop_query);
        this.onCreate(db);
    }

    public void insertItem(Item item)
    {
        rownInsert = -1; //reset

        appDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ITEM_COL_USERNAME, item.getUsername());
        values.put(ITEM_COL_NAME, item.getName());
        values.put(ITEM_COL_LOCATION, item.getLocation());
        values.put(ITEM_COL_TYPE, item.getType());
        values.put(ITEM_COL_DATE_PURCHASED, item.getDate_purchased());
        values.put(ITEM_COL_DATE_EXPIRED, item.getDate_expired());
        values.put(ITEM_COL_QUANTITY, item.getQuantity());
        values.put(ITEM_COL_AVERAGE_PRICE, item.getAverage_price());
        values.put(ITEM_COL_NOTES, item.getNotes());

        rownInsert = appDB.insert(ITEM_TABLE_NAME, null, values); //Insert Item to DB
        appDB.close();
    }

    public Toast isInsertedToast(Context context)
    {
        Toast db_insert;
        if(rownInsert != -1)
        {
            db_insert = Toast.makeText(context, "Item inserted successfully!", Toast.LENGTH_SHORT);
        }
        else
        {
            db_insert = Toast.makeText(context, "Could not insert item...", Toast.LENGTH_SHORT);
        }
        //show db_insert in AddItemActivity
        return db_insert;
    }

    public Item searchItem(String username, int search_id)
    {
        // TODO: Fix username being null problem

        appDB = this.getReadableDatabase();
//        String get_row_query = "SELECT * FROM "+ ITEM_TABLE_NAME + " WHERE USERNAME = ? and ID = ?";
//        Cursor found = appDB.rawQuery(get_row_query, new String[] {username, String.valueOf(search_id)});
        String get_row_query = "SELECT * FROM "+ ITEM_TABLE_NAME + " WHERE ID = ?";
        Cursor found = appDB.rawQuery(get_row_query, new String[] {String.valueOf(search_id)});

        String id;
        if (found.moveToFirst() && found != null)
        {
            do {
                id = found.getString(0);
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
        return null;
    }

    public void deleteItem(String username, int delete_id)
    {
        // TODO: Fix username being null problem
        appDB = this.getWritableDatabase();
//        String delete_row_query = "DELETE FROM "+ ITEM_TABLE_NAME + " WHERE USERNAME = '" + username + "' and ID = " + delete_id + ";";
        String delete_row_query = "DELETE FROM "+ ITEM_TABLE_NAME + " WHERE ID = " + delete_id + ";";
        appDB.execSQL(delete_row_query);
    }

    public void editItem(int edited_item_id, String editedColumnName, String newInfo)
    {
        appDB = this.getWritableDatabase();
        String edit_row_query = "UPDATE "+ ITEM_TABLE_NAME + " SET " + editedColumnName + " = '" + newInfo +
                "' WHERE ID = " + edited_item_id + ";";
        appDB.execSQL(edit_row_query);
    }

    // gets all data from items database and displays in the ListView in inventory screen
    public Cursor getItems(String username) {
        // TODO: Fix username being null problem
        appDB = this.getReadableDatabase();
//        String query = "select * from " + ITEM_TABLE_NAME + " where " + ITEM_COL_USERNAME + " = ?";
//        Cursor cursor = appDB.rawQuery(query, new String[]{username});
        String query = "select * from " + ITEM_TABLE_NAME;
        Cursor cursor = appDB.rawQuery(query, null);
        return cursor;
    }

    public Cursor getFilteredItems(String username, List<String> typeFilters, List<String> locationFilters){

        // TODO: Fix username being null problem
        appDB = this.getReadableDatabase();
        Cursor cursor = null;
        String query;
        StringBuffer fquery = new StringBuffer();

        if((typeFilters.size()==0) && (locationFilters.size()==0)) {

//            query = "select * from " + ITEM_TABLE_NAME + " where " + ITEM_COL_USERNAME + " = ?";
//            cursor = appDB.rawQuery(query, new String[]{username});
            query = "select * from " + ITEM_TABLE_NAME;
            cursor = appDB.rawQuery(query, null);

        } else
            if((typeFilters.size()==0) && (locationFilters.size()>0)){

//            fquery.append("select * from " + ITEM_TABLE_NAME + " where " + ITEM_COL_USERNAME + " = ");
//            fquery.append(username);
//            fquery.append(" AND (");
                fquery.append("select * from " + ITEM_TABLE_NAME + " where (");
                for (String str : locationFilters) {
                    fquery.append(ITEM_COL_LOCATION + " = '" + str + "' OR ");
                }
                int lastOR = fquery.lastIndexOf(" OR");
                fquery.delete(lastOR,lastOR+4);
                fquery.append(")");

                cursor = appDB.rawQuery(fquery.toString(), null);

            } else
                if((typeFilters.size()>0) && (locationFilters.size()==0)){
//            fquery.append("select * from " + ITEM_TABLE_NAME + " where " + ITEM_COL_USERNAME + " = ");
//            fquery.append(username);
//            fquery.append(" AND (");
                    fquery.append("select * from " + ITEM_TABLE_NAME + " where (");
                    for (String str : typeFilters) {
                        fquery.append(ITEM_COL_TYPE + " = '" + str + "' OR ");
                    }
                    int lastOR = fquery.lastIndexOf(" OR");
                    fquery.delete(lastOR,lastOR+4);
                    fquery.append(")");

                    cursor = appDB.rawQuery(fquery.toString(), null);
                } else
                    if((typeFilters.size()>0) && (locationFilters.size()>0)){
//            fquery.append("select * from " + ITEM_TABLE_NAME + " where " + ITEM_COL_USERNAME + " = ");
//            fquery.append(username);
//            fquery.append(" AND (");
                        fquery.append("select * from " + ITEM_TABLE_NAME + " where (");
                        for (String str : typeFilters) {
                            fquery.append(ITEM_COL_TYPE + " = '" + str + "' OR ");
                        }
                        int lastOR = fquery.lastIndexOf(" OR");
                        fquery.delete(lastOR,lastOR+4);
                        fquery.append(") AND (");
                        for (String str : locationFilters) {
                            fquery.append(ITEM_COL_LOCATION + " = '" + str + "' OR ");
                        }
                        lastOR = fquery.lastIndexOf(" OR");
                        fquery.delete(lastOR,lastOR+4);
                        fquery.append(")");

                        cursor = appDB.rawQuery(fquery.toString(), null);
                    }
        return cursor;
    }

    public List<String> getTypes(){
        appDB = this.getReadableDatabase();
        String query = "select " + ITEM_COL_TYPE + " from " + ITEM_TABLE_NAME;
        Cursor cursor = appDB.rawQuery(query,null);

        List<String> results = new ArrayList<String>();

        // TODO: Implement "All" checkbox
//        results.add("All");
        String newString;

        if(cursor.moveToFirst()){
            do {
                newString = cursor.getString(0);
                if(newString.length() > 0) {
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

        return results;
    }

    public List<String> getLocations() {
        appDB = this.getReadableDatabase();
        String query = "select " + ITEM_COL_LOCATION + " from " + ITEM_TABLE_NAME;
        Cursor cursor = appDB.rawQuery(query,null);

        List<String> results = new ArrayList<String>();

        // TODO: Implement "All" checkbox
//        results.add("All");
        String newString;

        if(cursor.moveToFirst()){
            do {
                newString = cursor.getString(0);
                if(newString.length() > 0) {
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

        return results;
    }

    public Cursor getItemID(String username, String name) {

        // TODO: Fix username being null problem
        appDB = this.getReadableDatabase();
//        String query = "select " + ITEM_COL_ID + " from " + ITEM_TABLE_NAME + " where " + ITEM_COL_USERNAME + " = ? and " + ITEM_COL_NAME + " = ?;";
//        Cursor cursor = appDB.rawQuery(query, new String[]{username, name});
        String query = "select " + ITEM_COL_ID + " from " + ITEM_TABLE_NAME + " where " + ITEM_COL_NAME + " = ?;";
        Cursor cursor = appDB.rawQuery(query, new String[]{name});
        return cursor;
    }

//    public List<InventoryItemDisplay> getRelevantItems() {
//
//    }
}
