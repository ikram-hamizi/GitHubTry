package com.example.usp05.githubtry.data_model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.example.usp05.githubtry.data_model.ItemDatabase.*;

/**
 * Created by nathan on 4/23/18.
 */

class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase itemDatabase;

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
        createTables(db);

        itemDatabase = db;
        itemDatabase = getWritableDatabase();

        ContentValues categoryValues = new ContentValues();
        categoryValues.put(ItemDatabase.CAT_COL_CAT, "Other");
        itemDatabase.insertOrThrow(ItemDatabase.TABLE_CATEGORIES, null, categoryValues);

        ContentValues locationValues = new ContentValues();
        locationValues.put(ItemDatabase.LOC_COL_LOC, "Other");
        itemDatabase.insertOrThrow(ItemDatabase.TABLE_LOCATIONS, null, locationValues);
    }

    private void createTables(SQLiteDatabase db) {
        db.execSQL(ItemDatabase.ENABLE_FOREIGN_KEYS);
        db.execSQL(ItemDatabase.CREATE_CATEGORY_TABLE);
        db.execSQL(ItemDatabase.CREATE_LOCATION_TABLE);
        db.execSQL(ItemDatabase.CREATE_INVENTORY_TABLE);
        db.execSQL(ItemDatabase.CREATE_ITEM_TABLE);
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
        db.execSQL(ItemDatabase.DROP_ITEM_TABLE);
        db.execSQL(ItemDatabase.DROP_INVENTORY_TABLE);
        db.execSQL(ItemDatabase.DROP_CATEGORY_TABLE);
        db.execSQL(ItemDatabase.DROP_LOCATION_TABLE);
        onCreate(db);
    }

    String buildQuery(String column, String whereVal) {
        StringBuffer query = new StringBuffer();

        query.append("SELECT ");
        query.append(" * ");
        query.append(" FROM ");
        query.append(ItemDatabase.TABLE_ITEM);

        query.append(" INNER JOIN ");
        query.append(ItemDatabase.TABLE_INVENTORY);
        query.append(" ON ");
        query.append(ItemDatabase.TABLE_INVENTORY).append('.').append(ItemDatabase.KEY_ID);
        query.append(" = ");
        query.append(ItemDatabase.TABLE_ITEM).append('.').append(ItemDatabase.ITEM_COL_INV);

        query.append(" INNER JOIN ");
        query.append(ItemDatabase.TABLE_LOCATIONS);
        query.append(" ON ");
        query.append(ItemDatabase.TABLE_LOCATIONS).append('.').append(ItemDatabase.KEY_ID);
        query.append(" = ");
        query.append(ItemDatabase.TABLE_ITEM).append('.').append(ItemDatabase.ITEM_COL_LOC);

        query.append(" INNER JOIN ");
        query.append(ItemDatabase.TABLE_CATEGORIES);
        query.append(" ON ");
        query.append(ItemDatabase.TABLE_CATEGORIES).append('.').append(ItemDatabase.KEY_ID);
        query.append(" = ");
        query.append(ItemDatabase.TABLE_INVENTORY).append('.').append(ItemDatabase.INV_COL_CAT);

        if ((column != null) && (whereVal != null)){
            query.append(" WHERE (");
            query.append(column);
            query.append(" = '");
            query.append(whereVal);
        }

        query.append("');");

        return query.toString();
    }

    void addItem(Item item) {

        Cursor cursor = itemDatabase.rawQuery(buildQuery(INV_COL_NAME, item.getName()), null);

        int nResults = cursor.getCount();

        if (nResults > 0) {
            if(nResults == 1) {
                cursor.moveToFirst();
                Item existing = getItemDetails(cursor.getInt(cursor.getColumnIndex(TABLE_INVENTORY + '.' + KEY_ID)));
                appendItem(existing, item);
            } else {
                boolean foundMatch = false;
                while(cursor.moveToNext() && !foundMatch) {
                    String category = cursor.getString(cursor.getColumnIndex(TABLE_CATEGORIES + '.' + CAT_COL_CAT));
                    if(item.getCategory().equalsIgnoreCase(category)) {
                        foundMatch = true;
                        Item existing = getItemDetails(cursor.getInt(cursor.getColumnIndex(TABLE_INVENTORY + '.' + KEY_ID)));
                        appendItem(existing, item);
                    }
                }
                if (!foundMatch) {
                    newInventoryItem(item);
                }
            }
        } else {
            newInventoryItem(item);
        }

        cursor.close();
    }

    private int createNewCategory(String category) {
        itemDatabase = getWritableDatabase();

        StringBuffer sb = new StringBuffer();

        sb.append("INSERT INTO ");
        sb.append(TABLE_CATEGORIES);
        sb.append(" (");
        sb.append(CAT_COL_CAT);
        sb.append(") ");
        sb.append("VALUES (");
        sb.append(category);
        sb.append(')').append(';');

        itemDatabase.execSQL(sb.toString());

        return getCategoryID(category);
    }

    private int createNewLocation(String location) {
        itemDatabase = getWritableDatabase();

        StringBuffer sb = new StringBuffer();

        sb.append("INSERT INTO ");
        sb.append(TABLE_LOCATIONS);
        sb.append(" (");
        sb.append(LOC_COL_LOC);
        sb.append(") ");
        sb.append("VALUES (");
        sb.append(location);
        sb.append(')').append(';');

        itemDatabase.execSQL(sb.toString());

        return getLocationID(location);
    }

    private int queryNameAndCategory(String name, String category) {
        StringBuffer query = new StringBuffer();

        query.append("SELECT ");
        query.append(" * ");
        query.append(" FROM ");
        query.append(ItemDatabase.TABLE_ITEM);

        query.append(" INNER JOIN ");
        query.append(ItemDatabase.TABLE_INVENTORY);
        query.append(" ON ");
        query.append(ItemDatabase.TABLE_INVENTORY).append('.').append(ItemDatabase.KEY_ID);
        query.append(" = ");
        query.append(ItemDatabase.TABLE_ITEM).append('.').append(ItemDatabase.ITEM_COL_INV);

        query.append(" INNER JOIN ");
        query.append(ItemDatabase.TABLE_LOCATIONS);
        query.append(" ON ");
        query.append(ItemDatabase.TABLE_LOCATIONS).append('.').append(ItemDatabase.KEY_ID);
        query.append(" = ");
        query.append(ItemDatabase.TABLE_ITEM).append('.').append(ItemDatabase.ITEM_COL_LOC);

        query.append(" INNER JOIN ");
        query.append(ItemDatabase.TABLE_CATEGORIES);
        query.append(" ON ");
        query.append(ItemDatabase.TABLE_CATEGORIES).append('.').append(ItemDatabase.KEY_ID);
        query.append(" = ");
        query.append(ItemDatabase.TABLE_INVENTORY).append('.').append(ItemDatabase.INV_COL_CAT);

        query.append(" WHERE (");
        query.append(INV_COL_NAME);
        query.append(" = '");
        query.append(name);

        query.append("' AND ");
        query.append(CAT_COL_CAT);
        query.append(" = '");
        query.append(category);
        query.append("');");

        Cursor cursor = itemDatabase.rawQuery(query.toString(), null);
        int result = 0;

        if(cursor.moveToFirst()){
            result = cursor.getInt(cursor.getColumnIndex(TABLE_INVENTORY + '.' + KEY_ID));
        } else {
            result = -1;
        }

        cursor.close();
        return result;
    }

    private void newInventoryItem(Item item) {

        itemDatabase = getWritableDatabase();

        StringBuffer sb = new StringBuffer();

        // Find existing category with same category name as item's
        int categoryID = getCategoryID(item.getCategory());

        if(categoryID <= 0) {
            // Create a new category
            categoryID = createNewCategory(item.getCategory());
        }

        // Find existing location with same location name as item's
        int locationID = getLocationID(item.getItemLocation());

        if(locationID <= 0) {
            // Create a new location
            locationID = createNewLocation(item.getItemLocation());
        }

        ContentValues cvInv = new ContentValues();
        cvInv.put(INV_COL_NAME, item.getName());
        cvInv.put(INV_COL_QTY, item.getTotalQuantity());
        cvInv.put(INV_COL_SED, item.getNextExpirationString());
        cvInv.put(INV_COL_CAT, categoryID);
        cvInv.put(INV_COL_AVGP, item.getAvgPrice());
        cvInv.put(INV_COL_NOTE, item.getNotes());
        itemDatabase.insert(TABLE_INVENTORY, null, cvInv);

        ContentValues cvItem = new ContentValues();
        cvItem.put(ITEM_COL_INV, queryNameAndCategory(item.getName(), item.getCategory()));
        cvItem.put(ITEM_COL_QTY, item.getTotalQuantity());
        cvItem.put(ITEM_COL_EXP, item.getNextExpirationString());
        cvItem.put(ITEM_COL_PDATE, item.getPurchaseDateString());
        cvItem.put(ITEM_COL_TOTCOST, item.getAvgPrice());
        cvItem.put(ITEM_COL_UNITCOST, item.getUnitPrice());
        cvItem.put(ITEM_COL_LOC, locationID);
        cvItem.put(ITEM_COL_NOTE, item.getNotes());
        itemDatabase.insert(TABLE_ITEM, null, cvItem);
    }

    int getCategoryID(String categoryName) {

        Cursor cursor = itemDatabase.rawQuery(buildQuery(CAT_COL_CAT, categoryName), null);

        if(cursor.getCount() <=0) {
            return -1;
        } else {
            cursor.moveToFirst();
            return cursor.getInt(cursor.getColumnIndex(TABLE_CATEGORIES + '.' + KEY_ID));
        }
    }

    int getLocationID(String locationName) {

        Cursor cursor = itemDatabase.rawQuery(buildQuery(LOC_COL_LOC, locationName), null);

        if(cursor.getCount() <=0) {
            return -1;
        } else {
            cursor.moveToFirst();
            return cursor.getInt(cursor.getColumnIndex(TABLE_LOCATIONS + '.' + KEY_ID));
        }
    }

    void appendItem(Item item) {}

    void appendItem(int invID, Item item) {}

    void appendItem(Item dbItem, Item item) {

    }

    public Cursor getInventory() {
        itemDatabase = getReadableDatabase();

        StringBuffer sb = new StringBuffer();

        sb.append("SELECT * FROM ");
        sb.append(TABLE_INVENTORY);
        sb.append(" INNER JOIN ");
        sb.append(TABLE_CATEGORIES);
        sb.append(" ON ");
        sb.append(INV_COL_CAT);
        sb.append(" = ");
        sb.append(TABLE_CATEGORIES).append('.').append(KEY_ID);
        sb.append(';');

        return itemDatabase.rawQuery(sb.toString(),null);
    }

    public Cursor getInventory(Collection<String> typeFilters, Collection<String> locationFilters) {
        itemDatabase = getReadableDatabase();
        Cursor c = null;
        StringBuffer query = new StringBuffer();

        // FIXME: Fix checking for filters being null
        if ((typeFilters == null) || (locationFilters == null)) {
            c = getInventory();
        }
        else if((typeFilters.isEmpty()) && (locationFilters.isEmpty())) {
            c = getInventory();
        } else {
            query.append("SELECT ");
            query.append(" * ");
            query.append(" FROM ");
            query.append(ItemDatabase.TABLE_ITEM);

            query.append(" INNER JOIN ");
            query.append(ItemDatabase.TABLE_INVENTORY);
            query.append(" ON ");
            query.append(ItemDatabase.TABLE_INVENTORY).append('.').append(ItemDatabase.KEY_ID);
            query.append(" = ");
            query.append(ItemDatabase.TABLE_ITEM).append('.').append(ItemDatabase.ITEM_COL_INV);

            query.append(" INNER JOIN ");
            query.append(ItemDatabase.TABLE_LOCATIONS);
            query.append(" ON ");
            query.append(ItemDatabase.TABLE_LOCATIONS).append('.').append(ItemDatabase.KEY_ID);
            query.append(" = ");
            query.append(ItemDatabase.TABLE_ITEM).append('.').append(ItemDatabase.ITEM_COL_LOC);

            query.append(" INNER JOIN ");
            query.append(ItemDatabase.TABLE_CATEGORIES);
            query.append(" ON ");
            query.append(ItemDatabase.TABLE_CATEGORIES).append('.').append(ItemDatabase.KEY_ID);
            query.append(" = ");
            query.append(ItemDatabase.TABLE_INVENTORY).append('.').append(ItemDatabase.INV_COL_CAT);

            if(!locationFilters.isEmpty()) {
                query.append(" WHERE (");
                for (String str : locationFilters) {
                    query.append(ItemDatabase.TABLE_LOCATIONS).append('.').append(ItemDatabase.LOC_COL_LOC);
                    query.append(" = '");
                    query.append(str);
                    query.append("' OR ");
                }
                if(!typeFilters.isEmpty()) {
                    for (String str : typeFilters) {
                        query.append(ItemDatabase.TABLE_CATEGORIES).append('.').append(ItemDatabase.CAT_COL_CAT);
                        query.append(" = '");
                        query.append(str);
                        query.append("' OR ");
                    }
                }
                int lastOR = query.lastIndexOf(" OR");
                query.delete(lastOR, lastOR + 4);
                query.append(") ");
            } else if(!typeFilters.isEmpty()) {
                query.append(" WHERE (");
                for (String str : typeFilters) {
                    query.append(ItemDatabase.TABLE_CATEGORIES).append('.').append(ItemDatabase.CAT_COL_CAT);
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

    void editItem(Item item) {
//        itemDatabase = getWritableDatabase();
//
//        ContentValues next = updateTableWithCV(ItemDatabase.TABLE_INVENTORY, invID, values);
//        next = updateTableWithCV(ItemDatabase.TABLE_ITEM, invID, next);
//        next = updateTableWithCV(ItemDatabase.TABLE_LOCATIONS, invID, next);
//        next = updateTableWithCV(ItemDatabase.TABLE_CATEGORIES, invID, next);
//
//        if(next.size()>0) {
//            // TODO: add error handling here
//        }
    }

    void deleteItem(Item item) {}

    List<Integer> searchItem(String column, String value){
        List<Integer> result = new ArrayList<>();

        return result;
    }

    Item getItemDetails(int id){
        Item result = new Item();
        Cursor cursor = itemDatabase.rawQuery(buildQuery(TABLE_INVENTORY + '.' + KEY_ID, Integer.toString(id)), null);

        if(cursor.moveToFirst()){
            result.setName(cursor.getString(cursor.getColumnIndex(INV_COL_NAME)));
            result.setTotalQuantity(cursor.getInt(cursor.getColumnIndex(INV_COL_QTY)));
            result.setNextExpirationString(cursor.getString(cursor.getColumnIndex(INV_COL_SED)));
            result.setCategory(cursor.getString(cursor.getColumnIndex(CAT_COL_CAT)));
            result.setAvgPrice(cursor.getDouble(cursor.getColumnIndex(INV_COL_AVGP)));
            result.setNotes(cursor.getString(cursor.getColumnIndex(INV_COL_NOTE)));
            ArrayList<Item.itemData> data = new ArrayList<>();
            do {
                result.id.setItemId(cursor.getInt(cursor.getColumnIndex(TABLE_ITEM + '.' + KEY_ID)));
                result.id.setLocation(cursor.getString(cursor.getColumnIndex(LOC_COL_LOC)));
                result.id.setExpiration_date(cursor.getString(cursor.getColumnIndex(ITEM_COL_EXP)));
                result.id.setPurchase_date(cursor.getString(cursor.getColumnIndex(ITEM_COL_PDATE)));
                result.id.setQuantity(cursor.getInt(cursor.getColumnIndex(ITEM_COL_QTY)));
                result.id.setTotalPrice(cursor.getDouble(cursor.getColumnIndex(ITEM_COL_TOTCOST)));
                result.id.setUnitPrice(cursor.getDouble(cursor.getColumnIndex(ITEM_COL_UNITCOST)));
                data.add(result.id);
            } while (cursor.moveToNext());
            result.setData(data);
        }

        cursor.close();
        return result;
    }

    List<Item> getAllInventory() {
        List<Item> result = new ArrayList<>();

        return result;
    }

    List<String> getAllCategories() {
        List<String> result = new ArrayList<>();

        return result;
    }

    List<String> getAllLocations() {
        List<String> result = new ArrayList<>();

        return result;
    }

//    private int getForeignKey(String entry, String tableName, String columnName) {
//        int foreignKey = 0;
//        Cursor c = null;
//
//        String query = "SELECT * FROM " + tableName
//                + " WHERE " + columnName
//                + " = '" + entry + "';";
//
//        try {
//
//            c = itemDatabase.rawQuery(query, null);
//
//        } catch (SQLiteException e) {
//
//            String eStr = e.toString();
//            if(eStr.contains("no such column")) {
//
//                if(tableName.equals(ItemDatabase.TABLE_CATEGORIES)){
//                    addCategory(entry);
//                } else if(tableName.equals(ItemDatabase.TABLE_LOCATIONS)){
//                    addLocation(entry);
//                } else {
//                    throw e;
//                }
//
//                c = itemDatabase.rawQuery(query, null);
//
//            } else {
//                throw e;
//            }
//        }
//
//        if((c != null) && (c.moveToFirst())){
//            foreignKey = c.getInt(0);
//        } else {
//            ContentValues values = new ContentValues();
//            values.put(columnName, entry);
//            itemDatabase.insertOrThrow(tableName, null, values);
//
//            c = itemDatabase.rawQuery(query, null);
//
//            if (c.moveToFirst()) {
//                foreignKey = c.getInt(0);
//            } else {
//                foreignKey = -1;
//            }
//        }
//
//        c.close();
//
//
//        return foreignKey;
//    }

}
