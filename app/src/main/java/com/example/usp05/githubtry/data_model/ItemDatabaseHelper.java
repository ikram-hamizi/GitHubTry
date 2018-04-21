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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.lang.reflect.*;
import java.util.Set;

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
    private static final String INV_COL_NAME = "ITEM_NAME";
    private static final String INV_COL_QTY = "TOTAL_QUANTITY";
    private static final String INV_COL_SED = "SOONEST_EXPIRATION_DATE";
    private static final String INV_COL_CAT = "ITEM_CATEGORY";
    private static final String INV_COL_AVGP = "AVERAGE_PRICE";
    private static final String INV_COL_NOTE = "INVENTORY_NOTES";

    // Item table -- column names
    private static final String ITEM_COL_INV = "INVENTORY_ID";
    private static final String ITEM_COL_QTY = "ITEM_QUANTITY";
    private static final String ITEM_COL_EXP = "EXPIRATION_DATE";
    private static final String ITEM_COL_PDATE = "PURCHASE_DATE";
    private static final String ITEM_COL_TOTCOST = "TOTAL_COST";
    private static final String ITEM_COL_UNITCOST = "UNIT_COST";
    private static final String ITEM_COL_LOC = "ITEM_LOCATION";
    private static final String ITEM_COL_NOTE = "ITEM_NOTES";


    // Location table -- column names
    private static final String LOC_COL_LOC = "LOCATION";


    // Category table -- column names
    private static final String CAT_COL_CAT = "CATEGORY";


    /***** SQL STATEMENTS *****/
    private static final String CREATE_INVENTORY_TABLE = "CREATE TABLE "
            + TABLE_INVENTORY + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + INV_COL_NAME + " TEXT NOT NULL UNIQUE, "
            + INV_COL_QTY + " INTEGER NOT NULL, "
            + INV_COL_SED + " DATETIME, "
            + INV_COL_CAT + " INTEGER NOT NULL DEFAULT 0, "
            + INV_COL_AVGP + " REAL, "
            + INV_COL_NOTE + " REAL, "
            + KEY_CREATED_AT + " DATETIME"
            + "FOREIGN KEY ("
            + INV_COL_CAT + ") REFERENCES "
            + TABLE_CATEGORIES + " ("
            + KEY_ID + ") ON DELETE SET DEFAULT"
            + ");";

    private static final String CREATE_ITEM_TABLE = "CREATE TABLE IF NOT EXISTS"
            + TABLE_ITEM + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + ITEM_COL_INV + " INTEGER NOT NULL, "
            + ITEM_COL_QTY + " INTEGER NOT NULL, "
            + ITEM_COL_EXP + " DATETIME NOT NULL, "
            + ITEM_COL_PDATE + " DATETIME NOT NULL, "
            + ITEM_COL_TOTCOST + " REAL NOT NULL, "
            + ITEM_COL_UNITCOST + " REAL NOT NULL, "
            + ITEM_COL_LOC + " INTEGER NOT NULL DEFAULT 0, "
            + ITEM_COL_NOTE + " TEXT, "
            + KEY_CREATED_AT + " DATETIME, "
            + "FOREIGN KEY ("
            + ITEM_COL_INV + ") REFERENCES "
            + TABLE_INVENTORY + " ("
            + KEY_ID + ") ON DELETE CASCADE "
            + "FOREIGN KEY ("
            + ITEM_COL_LOC + ") REFERENCES "
            + TABLE_LOCATIONS + " ("
            + KEY_ID + ") ON DELETE SET DEFAULT"
            + ");";

    private static final String CREATE_LOCATION_TABLE = "CREATE TABLE "
            + TABLE_LOCATIONS + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + LOC_COL_LOC + " TEXT NOT NULL UNIQUE, "
            + KEY_CREATED_AT + " DATETIME" + ");";

    private static final String CREATE_CATEGORY_TABLE = "CREATE TABLE "
            + TABLE_CATEGORIES + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + CAT_COL_CAT + " TEXT NOT NULL UNIQUE, "
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

        itemDatabase = getWritableDatabase();

        ContentValues categoryValues = new ContentValues();
        categoryValues.put(CAT_COL_CAT, "Other");
        itemDatabase.insertOrThrow(TABLE_CATEGORIES, null, categoryValues);

        ContentValues locationValues = new ContentValues();
        locationValues.put(LOC_COL_LOC, "Other");
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

        int foreignKey = getForeignKey(item.getName(), TABLE_INVENTORY, INV_COL_NAME);

        // Add the item to the item table
        ContentValues itemValues = new ContentValues();
        itemValues.put(ITEM_COL_INV, foreignKey);
        itemValues.put(ITEM_COL_QTY, item.getQuantity());
        itemValues.put(ITEM_COL_EXP,
                dateFormat.format(item.getExpiration_date()));
        itemValues.put(ITEM_COL_PDATE,
                dateFormat.format(item.getPurchase_date()));
        itemValues.put(ITEM_COL_TOTCOST, item.getTotalPrice());
        itemValues.put(ITEM_COL_UNITCOST, item.getUnitPrice());
        itemValues.put(ITEM_COL_LOC,
                getForeignKey(item.getLocation(),
                        TABLE_LOCATIONS, LOC_COL_LOC));
        itemValues.put(ITEM_COL_NOTE, item.getNotes());
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
                + ITEM_COL_QTY + " FROM "
                + TABLE_ITEM + " WHERE "
                + ITEM_COL_INV + " = "
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
        values.put(INV_COL_QTY, newQuantity);

        query = KEY_ID + " = ?";
        String whereArgs[] = {String.valueOf(inventoryKey)};

        itemDatabase.update(TABLE_INVENTORY, values, query, whereArgs);

    }

    private void updateSoonestExpirationDate(int inventoryKey){

        itemDatabase = getWritableDatabase();

        Date oldestDate = null;
        Date d = null;

        String query = "SELECT "
                + ITEM_COL_EXP + " FROM "
                + TABLE_ITEM + " WHERE "
                + ITEM_COL_INV + " = "
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
        values.put(INV_COL_SED, dateFormat.format(oldestDate));

        query = KEY_ID + " = ?";
        String whereArgs[] = {String.valueOf(inventoryKey)};

        itemDatabase.update(TABLE_INVENTORY, values, query, whereArgs);
    }

    private void appendInventoryNotes(int inventoryKey){

        itemDatabase = getWritableDatabase();

        StringBuilder sb = new StringBuilder();

        String query = "SELECT "
                + ITEM_COL_NOTE + " FROM "
                + TABLE_ITEM + " WHERE "
                + ITEM_COL_INV + " = "
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
        values.put(INV_COL_NOTE, sb.toString());

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
        inventoryValues.put(INV_COL_NAME, item.getName());
        inventoryValues.put(INV_COL_QTY, item.getQuantity());
        inventoryValues.put(INV_COL_SED,
                dateFormat.format(item.getExpiration_date()));
        inventoryValues.put(INV_COL_CAT,
                getForeignKey(item.getCategory(),
                        TABLE_CATEGORIES, CAT_COL_CAT));
        inventoryValues.put(INV_COL_AVGP, item.getUnitPrice());
        inventoryValues.put(INV_COL_NOTE, item.getNotes());
        itemDatabase.insertOrThrow(TABLE_INVENTORY,
                null, inventoryValues);

        // Add the item to the item table
        ContentValues itemValues = new ContentValues();
        itemValues.put(ITEM_COL_INV,
                getForeignKey(item.getName(),
                        TABLE_INVENTORY, INV_COL_NAME));
        itemValues.put(ITEM_COL_QTY, item.getQuantity());
        itemValues.put(ITEM_COL_EXP,
                dateFormat.format(item.getExpiration_date()));
        itemValues.put(ITEM_COL_PDATE,
                dateFormat.format(item.getPurchase_date()));
        itemValues.put(ITEM_COL_TOTCOST, item.getTotalPrice());
        itemValues.put(ITEM_COL_UNITCOST, item.getUnitPrice());
        itemValues.put(ITEM_COL_LOC,
                getForeignKey(item.getLocation(),
                        TABLE_LOCATIONS, LOC_COL_LOC));
        itemValues.put(ITEM_COL_NOTE, item.getNotes());
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
        String get_row_query = "SELECT * FROM "
                + TABLE_INVENTORY + " WHERE " + KEY_ID + " = ?";
        Cursor c = itemDatabase.rawQuery(get_row_query, new String[] {String.valueOf(search_id)});

        if (c.moveToFirst() && (c != null))
        {
            do {
                String id = c.getString(0);
                if(search_id == Integer.parseInt(id))
                {
                    Item result = new Item();
                    result.setName(c.getString(c.getColumnIndex(INV_COL_NAME)));
                    result.setQuantity(c.getInt(c.getColumnIndex(INV_COL_QTY)));
                    result.setExpiration_date(Date.valueOf(c.getString(c.getColumnIndex(INV_COL_SED))));
                    result.setCategory(c.getString(c.getColumnIndex(INV_COL_CAT)));
                    result.setUnitPrice(c.getFloat(c.getColumnIndex(INV_COL_AVGP)));
                    result.setNotes(c.getString(c.getColumnIndex(INV_COL_NOTE)));

                    return result;
                }
            }while(c.moveToNext());
        }
        c.close();
        return null;
    }

    protected void deleteItem(int itemID){
        itemDatabase = getWritableDatabase();

        String query = "DELETE FROM "
                + TABLE_INVENTORY + " WHERE "
                + KEY_ID + " = "
                + itemID + ";";

        itemDatabase.execSQL(query);
    }

    private Cursor doQuery(String query){
        return itemDatabase.rawQuery(query, null);
    }

    protected void editItem(int invID, ContentValues values) {

        itemDatabase = getWritableDatabase();

        ContentValues next = updateTableWithCV(TABLE_INVENTORY, invID, values);
        next = updateTableWithCV(TABLE_ITEM, invID, next);
        next = updateTableWithCV(TABLE_LOCATIONS, invID, next);
        next = updateTableWithCV(TABLE_CATEGORIES, invID, next);

        if(next.size()>0) {
            // TODO: add error handling here
        }

    }

    private ContentValues updateTableWithCV(String table, int invID, ContentValues values){

        itemDatabase = getWritableDatabase();

        boolean hasUpdate = false;

        String query = "SELECT "
                + " * " + " FROM "
                + table + " LIMIT 2;";

        Cursor c = doQuery(query);

        String[] columns = new String[0];
        if (c.moveToFirst()) {
            columns = c.getColumnNames();
        }

        List<String> updateColInv = new ArrayList<>();
        for (String str : columns) {
            if(values.containsKey(str)) {
                hasUpdate = true;
                updateColInv.add(str);
            }
        }

        if(hasUpdate){
            StringBuffer sb = new StringBuffer();
            sb.append("UPDATE ");
            sb.append(table);
            sb.append(" SET ");
            for (String str : updateColInv) {
                sb.append(str);
                sb.append(" = '");
                sb.append(values.get(str));
                values.remove(str);
                sb.append("', ");
            }
            int lastComma = sb.lastIndexOf(", ");
            sb.delete(lastComma, lastComma+2);
            sb.append(" WHERE ");
            sb.append(KEY_ID);
            sb.append(" = ");
            sb.append(invID).append(';');
            itemDatabase.execSQL(sb.toString());
        }

        c.close();
        return values;
    }

    protected Cursor getItems(){
        itemDatabase = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_INVENTORY + ';';
        return itemDatabase.rawQuery(query, null);
    }

    protected Cursor getItems(Collection<String> typeFilters, Collection<String> locationFilters){
        itemDatabase = getReadableDatabase();
        Cursor c = null;
        StringBuffer query = new StringBuffer();

        if((typeFilters.isEmpty()) && (locationFilters.isEmpty())) {
            c = getItems();
        } else {
            query.append("SELECT ");
            query.append(" * ");
            query.append(" FROM ");
            query.append(TABLE_ITEM);

            query.append(" INNER JOIN ");
            query.append(TABLE_INVENTORY);
            query.append(" ON ");
            query.append(TABLE_INVENTORY).append('.').append(KEY_ID);
            query.append(" = ");
            query.append(TABLE_ITEM).append('.').append(ITEM_COL_INV);

            query.append(" INNER JOIN ");
            query.append(TABLE_LOCATIONS);
            query.append(" ON ");
            query.append(TABLE_LOCATIONS).append('.').append(KEY_ID);
            query.append(" = ");
            query.append(TABLE_ITEM).append('.').append(ITEM_COL_LOC);

            query.append(" INNER JOIN ");
            query.append(TABLE_CATEGORIES);
            query.append(" ON ");
            query.append(TABLE_CATEGORIES).append('.').append(KEY_ID);
            query.append(" = ");
            query.append(TABLE_INVENTORY).append('.').append(INV_COL_CAT);

            if(!locationFilters.isEmpty()) {
                query.append(" WHERE (");
                for (String str : locationFilters) {
                    query.append(TABLE_LOCATIONS).append('.').append(LOC_COL_LOC);
                    query.append(" = '");
                    query.append(str);
                    query.append("' OR ");
                }
                int lastOR = query.lastIndexOf(" OR");
                query.delete(lastOR, lastOR + 4);
                query.append(") ");
            }

            if(!typeFilters.isEmpty()) {
                query.append(" WHERE (");
                for (String str : typeFilters) {
                    query.append(TABLE_CATEGORIES).append('.').append(CAT_COL_CAT);
                    query.append(" = '");
                    query.append(str);
                    query.append("' OR ");
                }
                int lastOR = query.lastIndexOf(" OR");
                query.delete(lastOR, lastOR + 4);
                query.append(") ");
            }

            query.append(';');

            c = itemDatabase.rawQuery(query.toString(), null);
        }

        return c;
    }

    protected List<String> getCategories(){
        itemDatabase = getReadableDatabase();

        String query = "SELECT "
                + CAT_COL_CAT + " FROM "
                + TABLE_CATEGORIES + ';';

        Cursor c = itemDatabase.rawQuery(query,null);

        List<String> results = new ArrayList<>();

        if(c.moveToFirst()){
            do {
                String newString = c.getString(0);
                if(!newString.isEmpty()) {
                    results.add(newString);
                }
            } while(c.moveToNext());
        }

        c.close();
        return results;
    }

    protected List<String> getLocations(){
        itemDatabase = getReadableDatabase();

        String query = "SELECT "
                + LOC_COL_LOC + " FROM "
                + TABLE_LOCATIONS + ';';

        Cursor c = itemDatabase.rawQuery(query,null);

        List<String> results = new ArrayList<>();

        if(c.moveToFirst()){
            do {
                String newString = c.getString(0);
                if(!newString.isEmpty()) {
                    results.add(newString);
                }
            } while(c.moveToNext());
        }

        c.close();
        return results;
    }


}
