package com.example.usp05.githubtry.data_model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by nathan on 4/20/18.
 */

class ItemDatabaseHelper extends SQLiteOpenHelper {

   /*
    *
    * IMPORTANT! If any changes are made to the structure
    * of the database (e.g. changing the name of a column),
    * the database version MUST be updated!!
    *
    * */

    // Database information
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "USER_INVENTORY_DATABASE";
    private static final String ENABLE_FOREIGN_KEYS = "PRAGMA foreign_keys = ON;";

    // Database tables
    private static final String TABLE_INVENTORY = "INVENTORY";
    private static final String TABLE_ITEM = "ITEMS";
    private static final String TABLE_LOCATIONS = "LOCATIONS";
    private static final String TABLE_CATEGORIES = "CATEGORIES";

    /***** COLUMN NAMES FOR TABLES *****/
    // Common column names
    static final String KEY_ID = "ID";
    static final String KEY_CREATED_AT = "CREATED";

    // Inventory table -- column names
    private static final String COLUMN_ITEM_NAME = "NAME";
    private static final String COLUMN_TOTAL_QUANTITY = "QUANTITY";
    private static final String COLUMN_NEXT_EXPIRATION = "SOONEST_EXPIRATION_DATE";
    private static final String COLUMN_CATEGORY = "CATEGORY";
    private static final String COLUMN_AVERAGE_PRICE = "AVERAGE_PRICE";
    private static final String COLUMN_INVENTORY_NOTES = "NOTES";

    // Item table -- column names
    private static final String COLUMN_INVENTORY_KEY = "INVENTORY_ID";
    private static final String COLUMN_QUANTITY = "QUANTITY";
    private static final String COLUMN_EXPIRATION = "EXPIRATION_DATE";
    private static final String COLUMN_PURCHASE_DATE = "PURCHASE_DATE";
    private static final String COLUMN_TOTAL_COST = "TOTAL_COST";
    private static final String COLUMN_UNIT_COST = "UNIT_COST";
    private static final String COLUMN_LOCATION = "ITEM_LOCATION";
    private static final String COLUMN_ITEM_NOTES = "ITEM_NOTES";

    // Location table -- column names
    private static final String COLUMN_LOCATION_NAME = "LOCATION";

    // Category table -- column names
    private static final String COLUMN_CATEGORY_NAME = "CATEGORY";

    /***** SQL STATEMENTS *****/
    private static final String CREATE_INVENTORY_TABLE = "CREATE TABLE "
            + TABLE_INVENTORY + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_ITEM_NAME + " TEXT NOT NULL UNIQUE, "
            + COLUMN_TOTAL_QUANTITY + " INTEGER NOT NULL, "
            + COLUMN_NEXT_EXPIRATION + " DATETIME, "
            + COLUMN_CATEGORY + " INTEGER NOT NULL DEFAULT 0, "
            + COLUMN_AVERAGE_PRICE + " REAL, "
            + COLUMN_INVENTORY_NOTES + " REAL, "
            + KEY_CREATED_AT + " DATETIME"
            + "FOREIGN KEY ("
            + COLUMN_CATEGORY + ") REFERENCES "
            + TABLE_CATEGORIES + " ("
            + KEY_ID + ") ON DELETE SET DEFAULT"
            + ");";

    private static final String CREATE_ITEM_TABLE = "CREATE TABLE IF NOT EXISTS"
            + TABLE_ITEM + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_INVENTORY_KEY + " INTEGER NOT NULL, "
            + COLUMN_QUANTITY + " INTEGER NOT NULL, "
            + COLUMN_EXPIRATION + " DATETIME NOT NULL, "
            + COLUMN_PURCHASE_DATE + " DATETIME NOT NULL, "
            + COLUMN_TOTAL_COST + " REAL NOT NULL, "
            + COLUMN_UNIT_COST + " REAL NOT NULL, "
            + COLUMN_LOCATION + " INTEGER NOT NULL DEFAULT 0, "
            + COLUMN_ITEM_NOTES + " TEXT, "
            + KEY_CREATED_AT + " DATETIME, "
            + "FOREIGN KEY ("
            + COLUMN_INVENTORY_KEY + ") REFERENCES "
            + TABLE_INVENTORY + " ("
            + KEY_ID + ") ON DELETE CASCADE "
            + "FOREIGN KEY ("
            + COLUMN_LOCATION + ") REFERENCES "
            + TABLE_LOCATIONS + " ("
            + KEY_ID + ") ON DELETE SET DEFAULT"
            + ");";

    private static final String CREATE_LOCATION_TABLE = "CREATE TABLE "
            + TABLE_LOCATIONS + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_LOCATION_NAME + " TEXT NOT NULL UNIQUE, "
            + KEY_CREATED_AT + " DATETIME" + ");";

    private static final String CREATE_CATEGORY_TABLE = "CREATE TABLE "
            + TABLE_CATEGORIES + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_CATEGORY_NAME + " TEXT NOT NULL UNIQUE, "
            + KEY_CREATED_AT + " DATETIME" + ");";

    private static final String DROP_INVENTORY_TABLE = "DROP TABLE IF EXISTS "
            + TABLE_INVENTORY + ";";

    private static final String DROP_ITEM_TABLE = "DROP TABLE IF EXISTS "
            + TABLE_ITEM + ";";

    private static final String DROP_LOCATION_TABLE = "DROP TABLE IF EXISTS "
            + TABLE_LOCATIONS + ";";

    private static final String DROP_CATEGORY_TABLE = "DROP TABLE IF EXISTS "
            + TABLE_CATEGORIES + ";";


    SQLiteDatabase itemDatabase;
    SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.y");

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
    public ItemDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

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
    public ItemDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
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
        db.execSQL(ENABLE_FOREIGN_KEYS);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_LOCATION_TABLE);
        db.execSQL(CREATE_INVENTORY_TABLE);
        db.execSQL(CREATE_ITEM_TABLE);

        itemDatabase = db;
//        itemDatabase.setForeignKeyConstraintsEnabled(true);

        itemDatabase = getWritableDatabase();

        ContentValues categoryValues = new ContentValues();
        categoryValues.put(COLUMN_CATEGORY_NAME, "Other");
        itemDatabase.insertOrThrow(TABLE_CATEGORIES, null, categoryValues);

        ContentValues locationValues = new ContentValues();
        locationValues.put(COLUMN_LOCATION_NAME, "Other");
        itemDatabase.insertOrThrow(TABLE_LOCATIONS, null, locationValues);

        itemDatabase.close();
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
        db.execSQL(DROP_ITEM_TABLE);
        db.execSQL(DROP_INVENTORY_TABLE);
        db.execSQL(DROP_CATEGORY_TABLE);
        db.execSQL(DROP_LOCATION_TABLE);
        onCreate(db);
    }

    protected void addItem(Item item){
        itemDatabase = getWritableDatabase();

        int foreignKey = getForeignKey(item.getName(), TABLE_INVENTORY, COLUMN_ITEM_NAME);

        // Add the item to the item table
        ContentValues itemValues = new ContentValues();
        itemValues.put(COLUMN_INVENTORY_KEY, foreignKey);
        itemValues.put(COLUMN_QUANTITY, item.getQuantity());
        itemValues.put(COLUMN_EXPIRATION,
                dateFormat.format(item.getExpiration_date()));
        itemValues.put(COLUMN_PURCHASE_DATE,
                dateFormat.format(item.getPurchase_date()));
        itemValues.put(COLUMN_TOTAL_COST, item.getTotalPrice());
        itemValues.put(COLUMN_UNIT_COST, item.getUnitPrice());
        itemValues.put(COLUMN_LOCATION,
                getForeignKey(item.getLocation(),
                        TABLE_LOCATIONS, COLUMN_LOCATION_NAME));
        itemValues.put(COLUMN_ITEM_NOTES, item.getNotes());
        itemDatabase.insertOrThrow(TABLE_ITEM,
                null, itemValues);

        updateQuantity(foreignKey);
        updateSoonestExpirationDate(foreignKey);
        appendInventoryNotes(foreignKey);

        closeDatabaseIfOpen();
    }

    private void updateQuantity(int inventoryKey){

        itemDatabase = getWritableDatabase();

        int newQuantity = 0;

        String query = "SELECT "
                + COLUMN_QUANTITY + " FROM "
                + TABLE_ITEM + " WHERE "
                + COLUMN_INVENTORY_KEY + " = "
                + String.valueOf(inventoryKey)
                + ";";

        Cursor c = itemDatabase.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                newQuantity += c.getInt(0);
            } while(c.moveToNext());
        }
        c.close();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TOTAL_QUANTITY, newQuantity);

        query = KEY_ID + " = ?";
        String whereArgs[] = {String.valueOf(inventoryKey)};

        itemDatabase.update(TABLE_INVENTORY, values, query, whereArgs);

    }

    private void updateSoonestExpirationDate(int inventoryKey){

        itemDatabase = getWritableDatabase();

        Date oldestDate = null;
        Date d = null;

        String query = "SELECT "
                + COLUMN_EXPIRATION + " FROM "
                + TABLE_ITEM + " WHERE "
                + COLUMN_INVENTORY_KEY + " = "
                + String.valueOf(inventoryKey)
                + ";";

        Cursor c = itemDatabase.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                if(oldestDate == null){
                    oldestDate = Date.valueOf(c.getString(0));
                    dateFormat.format(oldestDate);
                } else {
                    d = Date.valueOf(c.getString(0));
                    dateFormat.format(d);

                    if(d.before(oldestDate)){
                        oldestDate = d;
                    }
                }
            } while(c.moveToNext());
        }
        c.close();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NEXT_EXPIRATION, dateFormat.format(oldestDate));

        query = KEY_ID + " = ?";
        String whereArgs[] = {String.valueOf(inventoryKey)};

        itemDatabase.update(TABLE_INVENTORY, values, query, whereArgs);
    }

    private void appendInventoryNotes(int inventoryKey){

        itemDatabase = getWritableDatabase();

        StringBuilder sb = new StringBuilder();

        String query = "SELECT "
                + COLUMN_ITEM_NOTES + " FROM "
                + TABLE_ITEM + " WHERE "
                + COLUMN_INVENTORY_KEY + " = "
                + String.valueOf(inventoryKey)
                + ";";

        Cursor c = itemDatabase.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                sb.append(c.getString(0));
                sb.append("\n");
            } while(c.moveToNext());
        }
        c.close();

        ContentValues values = new ContentValues();
        values.put(COLUMN_INVENTORY_NOTES, sb.toString());

        query = KEY_ID + " = ?";
        String whereArgs[] = {String.valueOf(inventoryKey)};

        itemDatabase.update(TABLE_INVENTORY, values, query, whereArgs);
    }

    public void closeDatabaseIfOpen(){
        if(itemDatabase.isOpen()) {
            itemDatabase.close();
        }
    }

    protected void addNewItem(Item item) {
        itemDatabase = getWritableDatabase();

        // Add the item to the inventory table
        ContentValues inventoryValues = new ContentValues();
        inventoryValues.put(COLUMN_ITEM_NAME, item.getName());
        inventoryValues.put(COLUMN_TOTAL_QUANTITY, item.getQuantity());
        inventoryValues.put(COLUMN_NEXT_EXPIRATION,
                dateFormat.format(item.getExpiration_date()));
        inventoryValues.put(COLUMN_CATEGORY,
                getForeignKey(item.getCategory(),
                        TABLE_CATEGORIES, COLUMN_CATEGORY_NAME));
        inventoryValues.put(COLUMN_AVERAGE_PRICE, item.getUnitPrice());
        inventoryValues.put(COLUMN_INVENTORY_NOTES, item.getNotes());
        itemDatabase.insertOrThrow(TABLE_INVENTORY,
                null, inventoryValues);

        // Add the item to the item table
        ContentValues itemValues = new ContentValues();
        itemValues.put(COLUMN_INVENTORY_KEY,
                getForeignKey(item.getName(),
                        TABLE_INVENTORY, COLUMN_ITEM_NAME));
        itemValues.put(COLUMN_QUANTITY, item.getQuantity());
        itemValues.put(COLUMN_EXPIRATION,
                dateFormat.format(item.getExpiration_date()));
        itemValues.put(COLUMN_PURCHASE_DATE,
                dateFormat.format(item.getPurchase_date()));
        itemValues.put(COLUMN_TOTAL_COST, item.getTotalPrice());
        itemValues.put(COLUMN_UNIT_COST, item.getUnitPrice());
        itemValues.put(COLUMN_LOCATION,
                getForeignKey(item.getLocation(),
                        TABLE_LOCATIONS, COLUMN_LOCATION_NAME));
        itemValues.put(COLUMN_ITEM_NOTES, item.getNotes());
        itemDatabase.insertOrThrow(TABLE_ITEM,
                null, itemValues);

        itemDatabase.close();
    }

    private int getForeignKey(String entry, String tableName, String columnName) {
        int foreignKey = 0;

        // TODO: Implement try and catch here

        String query = "SELECT * FROM " + tableName
                + " WHERE " + columnName
                + " = " + entry;

        Cursor c = itemDatabase.rawQuery(query, null);

        if(c.moveToFirst()){
            foreignKey = c.getInt(0);
        } else {
            ContentValues values = new ContentValues();
            values.put(columnName, entry);
            itemDatabase.insertOrThrow(tableName, null, values);

            c = itemDatabase.rawQuery(query, null);

            if (c.moveToFirst()) {
                foreignKey = c.getInt(0);
            } else {
                foreignKey = -1;
            }
        }

        c.close();

        return foreignKey;
    }

    @Nullable
    protected Item searchItem(int search_id){

        itemDatabase = getReadableDatabase();
        String get_row_query = "SELECT * FROM "+
                TABLE_INVENTORY + " WHERE ID = ?";
        Cursor c = itemDatabase.rawQuery(get_row_query, new String[] {String.valueOf(search_id)});

        if (c.moveToFirst() && (c != null))
        {
            do {
                String id = c.getString(0);
                if(search_id == Integer.parseInt(id))
                {
                    Item result = new Item();
                    result.setName(c.getString(c.getColumnIndex(COLUMN_ITEM_NAME)));
                    result.setQuantity(c.getInt(c.getColumnIndex(COLUMN_TOTAL_QUANTITY)));
                    result.setExpiration_date(Date.valueOf(c.getString(c.getColumnIndex(COLUMN_NEXT_EXPIRATION))));
                    result.setCategory(c.getString(c.getColumnIndex(COLUMN_CATEGORY)));
                    result.setUnitPrice(c.getFloat(c.getColumnIndex(COLUMN_AVERAGE_PRICE)));
                    result.setNotes(c.getString(c.getColumnIndex(COLUMN_INVENTORY_NOTES)));

                    return result;
                }
            }while(c.moveToNext());
        }
        c.close();
        return null;
    }

    protected void deleteItem(){}

    protected void editItem(){}

    protected void getItems(){}

    protected void getFilteredItems(){}

    protected void getCategories(){}

    protected void getLocations(){}


}
