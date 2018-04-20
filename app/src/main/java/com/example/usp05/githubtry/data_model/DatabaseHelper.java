package com.example.usp05.githubtry.data_model;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nathan on 4/19/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    // Database information
    static final int DB_VERSION = 2;
    static final String DB_NAME = "DATABASE_MYINVENTORYAPP";
    protected String USER_DB_NAME;

    // Database tables
    static final String TABLE_USER = "USER";
    static final String TABLE_INVENTORY = "INVENTORY";
    static final String TABLE_ITEM = "ITEM";
    static final String TABLE_LOCATION = "LOCATION";
    static final String TABLE_CATEGORY = "CATEGORY";

    /***** COLUMN NAMES FOR TABLES *****/
    // Common column names
    static final String KEY_ID = "ID";
    static final String KEY_CREATED_AT = "CREATED";

    // User table -- column names
    private static final String USER_KEY = "KEY_ID";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PASSWORD2 = "password2";
    private static final String COLUMN_SECQUESTION1 = "secQuestion1";
    private static final String COLUMN_SECQUESTION2 = "secQuestion2";
    private static final String COLUMN_SECQUESTION3 = "secQuestion3";

    // Inventory table -- column names
    static final String INV_KEY = "INV_ID";
    static final String INV_NAME = "NAME";
    static final String INV_QUANTITY = "QUANTITY";
    static final String INV_AVERAGE_PRICE = "AVERAGE_PRICE";
    static final String INV_AVERAGE_USAGE = "AVERAGE_USAGE";
    static final String INV_ITEM_KEY = "ITEM_KEY";


    // Item table -- column names
    static final String ITEM_KEY = "ITEM_ID";
    static final String ITEM_COL_LOCATION = "LOCATION";
    static final String ITEM_COL_TYPE = "TYPE";
    static final String ITEM_COL_DATE_PURCHASED = "DATE_PURCHASED";
    static final String ITEM_COL_DATE_EXPIRED = "DATE_EXPIRED";
    static final String ITEM_COL_QUANTITY = "QUANTITY";
    static final String ITEM_COL_AVERAGE_PRICE = "AVERAGE_PRICE";
    static final String ITEM_COL_NOTES = "NOTES";

    // Location table -- column names


    // Category table -- column names


    /***** SQL STATEMENTS *****/
    private static final String CREATE_TABLE_ITEM = "CREATE TABLE "
            + TABLE_ITEM + "(" + KEY_ID + " INTEGER PRIMARY KEY," + ITEM_KEY
            + " TEXT," + ITEM_COL_LOCATION + " INTEGER," + KEY_CREATED_AT + " DATETIME" + ")";

    long row_nInsert;
    SQLiteDatabase itemDB;




    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
//    public DatabaseHelper(Context context) {
//        super(context, DB_NAME, null, DB_VERSION);
//    }


    /**
     * Create a helper object to create, open, and/or manage a database.
     * The database is not actually created or opened until one of
     * {@link #getWritableDatabase} or {@link #getReadableDatabase} is called.
     * <p>
     * <p>Accepts input param: a concrete instance of {@link DatabaseErrorHandler} to be
     * used to handle corruption when sqlite reports database corruption.</p>
     *
     * @param context      to use to open or create the database
     * @param name         of the database file, or null for an in-memory database
     * @param factory      to use for creating cursor objects, or null for the default
     * @param version      number of the database (starting at 1); if the database is older,
     *                     {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                     newer, {@link #onDowngrade} will be used to downgrade the database
     * @param errorHandler the {@link DatabaseErrorHandler} to be used when sqlite reports database
     */
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
