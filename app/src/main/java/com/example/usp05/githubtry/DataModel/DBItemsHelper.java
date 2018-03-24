package com.example.usp05.githubtry.DataModel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cstech on 3/24/18.
 */

public class DBItemsHelper extends SQLiteOpenHelper{

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DATABASE_MYINVENTORYAPP";

    private static final String TABLE_NAME = "ITEM";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "NAME";
    private static final String COL_LOCATION = "LOCATION";
    private static final String COL_TYPE = "TYPE";
    private static final String COL_DATE_PURCHASED = "DATE_PURCHASED";
    private static final String COL_DATE_EXPIRED = "DATE_EXPIRED";
    private static final String COL_QUANTITY = "QUANTITY";
    private static final String COL_NOTES = "NOTES";
    private static final String COL_AVERAGE_PRICE = "AVERAGE_PRICE";

    SQLiteDatabase appDB;

    private static final String TABLE_CREATE = "CREATE TABLE ITEM (ID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT , " +
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
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
