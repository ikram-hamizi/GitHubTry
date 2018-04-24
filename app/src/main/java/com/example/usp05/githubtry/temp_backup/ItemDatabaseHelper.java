package com.example.usp05.githubtry.temp_backup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by nathan on 4/20/18.
 */

class ItemDatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase itemDatabase;
    SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.y");
    DateHandler DH = new DateHandler();

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
        createTables(db);

        itemDatabase = db;

        itemDatabase = getWritableDatabase();

        StringBuffer sb = new StringBuffer();

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

    @Override
    public SQLiteDatabase getWritableDatabase(){

        if (itemDatabase == null) {
            itemDatabase = super.getWritableDatabase();
        }
        else if (itemDatabase.isOpen()){
            if (itemDatabase.isReadOnly()) {
                itemDatabase.close();
                itemDatabase = super.getWritableDatabase();
            }
        } else {
            itemDatabase = super.getWritableDatabase();
        }

        return itemDatabase;
    }

    @Override
    public SQLiteDatabase getReadableDatabase(){
        if (itemDatabase == null) {
            itemDatabase = super.getReadableDatabase();
        }
        else if(!itemDatabase.isOpen()){
            itemDatabase = super.getReadableDatabase();
        }
        return itemDatabase;
    }

    public void closeDatabaseIfOpen(){
        if(itemDatabase.isOpen()) {
            itemDatabase.close();
        }
    }

    protected void deleteDatabase(){
        itemDatabase.getPath();

    }

    private Cursor doQuery(String query){
        return itemDatabase.rawQuery(query, null);
    }

    protected void addItem(ItemHandler itemHandler) throws ParseException {
        itemDatabase = getWritableDatabase();

        int foreignKey = getForeignKey(itemHandler.getName(), ItemDatabase.TABLE_INVENTORY, ItemDatabase.INV_COL_NAME);

        // Add the itemHandler to the itemHandler table
        ContentValues itemValues = new ContentValues();
        itemValues.put(ItemDatabase.ITEM_COL_INV, foreignKey);
        itemValues.put(ItemDatabase.ITEM_COL_QTY, itemHandler.getQuantity());
        itemValues.put(ItemDatabase.ITEM_COL_EXP,
                dateFormat.format(itemHandler.getExpiration_date()));
        itemValues.put(ItemDatabase.ITEM_COL_PDATE,
                dateFormat.format(itemHandler.getPurchase_date()));
        itemValues.put(ItemDatabase.ITEM_COL_TOTCOST, itemHandler.getTotalPrice());
        itemValues.put(ItemDatabase.ITEM_COL_UNITCOST, itemHandler.getUnitPrice());
        itemValues.put(ItemDatabase.ITEM_COL_LOC,
                getForeignKey(itemHandler.getLocation(),
                        ItemDatabase.TABLE_LOCATIONS, ItemDatabase.LOC_COL_LOC));
        itemValues.put(ItemDatabase.ITEM_COL_NOTE, itemHandler.getNotes());
        itemDatabase.insertOrThrow(ItemDatabase.TABLE_ITEM,
                null, itemValues);

        updateQuantity(foreignKey);
        updateSoonestExpirationDate(foreignKey);
        appendInventoryNotes(foreignKey);
    }

    protected void addNewItem(ItemHandler itemHandler) {
        itemDatabase = getWritableDatabase();

        // Add the itemHandler to the inventory table
        ContentValues inventoryValues = new ContentValues();
        inventoryValues.put(ItemDatabase.INV_COL_NAME, itemHandler.getName());
        inventoryValues.put(ItemDatabase.INV_COL_QTY, itemHandler.getQuantity());
        inventoryValues.put(ItemDatabase.INV_COL_SED, getFormattedDate(itemHandler.getExpiration_date()));
        inventoryValues.put(ItemDatabase.INV_COL_CAT,
                getForeignKey(itemHandler.getCategory(),
                        ItemDatabase.TABLE_CATEGORIES, ItemDatabase.CAT_COL_CAT));
        inventoryValues.put(ItemDatabase.INV_COL_AVGP, itemHandler.getUnitPrice());
        inventoryValues.put(ItemDatabase.INV_COL_NOTE, itemHandler.getNotes());
        itemDatabase.insertOrThrow(ItemDatabase.TABLE_INVENTORY,
                null, inventoryValues);

        // Add the itemHandler to the itemHandler table
        ContentValues itemValues = new ContentValues();
        itemValues.put(ItemDatabase.ITEM_COL_INV,
                getForeignKey(itemHandler.getName(),
                        ItemDatabase.TABLE_INVENTORY, ItemDatabase.INV_COL_NAME));
        itemValues.put(ItemDatabase.ITEM_COL_QTY, itemHandler.getQuantity());
        itemValues.put(ItemDatabase.ITEM_COL_EXP, getFormattedDate(itemHandler.getExpiration_date()));
        itemValues.put(ItemDatabase.ITEM_COL_PDATE, getFormattedDate(itemHandler.getPurchase_date()));
        itemValues.put(ItemDatabase.ITEM_COL_TOTCOST, itemHandler.getTotalPrice());
        itemValues.put(ItemDatabase.ITEM_COL_UNITCOST, itemHandler.getUnitPrice());
        itemValues.put(ItemDatabase.ITEM_COL_LOC,
                getForeignKey(itemHandler.getLocation(),
                        ItemDatabase.TABLE_LOCATIONS, ItemDatabase.LOC_COL_LOC));
        itemValues.put(ItemDatabase.ITEM_COL_NOTE, itemHandler.getNotes());
        itemDatabase.insertOrThrow(ItemDatabase.TABLE_ITEM,
                null, itemValues);


    }

    protected void editItem(int invID, ContentValues values) {

        itemDatabase = getWritableDatabase();

        ContentValues next = updateTableWithCV(ItemDatabase.TABLE_INVENTORY, invID, values);
        next = updateTableWithCV(ItemDatabase.TABLE_ITEM, invID, next);
        next = updateTableWithCV(ItemDatabase.TABLE_LOCATIONS, invID, next);
        next = updateTableWithCV(ItemDatabase.TABLE_CATEGORIES, invID, next);

        if(next.size()>0) {
            // TODO: add error handling here
        }



    }

    protected void deleteItem(int itemID){
        itemDatabase = getWritableDatabase();

        String query = "DELETE FROM "
                + ItemDatabase.TABLE_INVENTORY + " WHERE "
                + ItemDatabase.KEY_ID + " = "
                + itemID + ";";

        itemDatabase.execSQL(query);

        closeDatabaseIfOpen();
    }

    protected ArrayList<ItemDetails> returnMatchingItems(ItemHelper.BaseItem baseItem){
        ArrayList<ItemDetails> result = new ArrayList<>();

        return result;
    }


    @Nullable
    protected ItemHandler searchItem(int search_id){

        itemDatabase = getReadableDatabase();

        StringBuffer sb = new StringBuffer();

        sb.append("SELECT * FROM ");
        sb.append(ItemDatabase.TABLE_INVENTORY);
        sb.append(" INNER JOIN ");
        sb.append(ItemDatabase.TABLE_CATEGORIES);
        sb.append(" ON ");
        sb.append(ItemDatabase.INV_COL_CAT);
        sb.append(" = ");
        sb.append(ItemDatabase.TABLE_CATEGORIES).append('.').append(ItemDatabase.KEY_ID);
        sb.append(" WHERE ");
        sb.append(ItemDatabase.TABLE_INVENTORY).append('.').append(ItemDatabase.KEY_ID);
        sb.append(" = ").append(search_id).append(';');


        String get_row_query = "SELECT * FROM "
                + ItemDatabase.TABLE_INVENTORY + " WHERE " + ItemDatabase.KEY_ID + " = ?";
//        Cursor c = itemDatabase.rawQuery(get_row_query, new String[] {String.valueOf(search_id)});
        Cursor c = itemDatabase.rawQuery(sb.toString(),null);


        if ((c != null) && c.moveToFirst())
        {
            do {
                String id = c.getString(0);
                if(search_id == Integer.parseInt(id))
                {
                    ItemHandler result = new ItemHandler();

                    String cName = c.getString(c.getColumnIndex(ItemDatabase.INV_COL_NAME));
                    int cQty = c.getInt(c.getColumnIndex(ItemDatabase.INV_COL_QTY));
                    String expString = c.getString(c.getColumnIndex(ItemDatabase.INV_COL_SED));
                    Date cExpDate = null;
                    if((expString != null) && (!expString.isEmpty())) {
                        DH.itemStringToDate(expString);
                    }
                    String cCat = c.getString(c.getColumnIndex(ItemDatabase.CAT_COL_CAT));
                    float cPrice = c.getFloat(c.getColumnIndex(ItemDatabase.INV_COL_AVGP));
                    String cNotes = c.getString(c.getColumnIndex(ItemDatabase.INV_COL_NOTE));

                    result.setName(cName);
                    result.setQuantity(cQty);
                    result.setExpiration_date(cExpDate);
                    result.setCategory(cCat);
                    result.setUnitPrice(cPrice);
                    result.setNotes(cNotes);

                    return result;
                }
            }while(c.moveToNext());
        }
        c.close();

        return null;
    }






    private void updateQuantity(int inventoryKey){

        itemDatabase = getWritableDatabase();

        int newQuantity = 0;

        String query = "SELECT "
                + ItemDatabase.ITEM_COL_QTY + " FROM "
                + ItemDatabase.TABLE_ITEM + " WHERE "
                + ItemDatabase.ITEM_COL_INV + " = "
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
        values.put(ItemDatabase.INV_COL_QTY, newQuantity);

        query = ItemDatabase.KEY_ID + " = ?";
        String whereArgs[] = {String.valueOf(inventoryKey)};

        itemDatabase.update(ItemDatabase.TABLE_INVENTORY, values, query, whereArgs);

    }

    private void updateSoonestExpirationDate(int inventoryKey) throws ParseException {

        itemDatabase = getWritableDatabase();

        Date oldestDate = null;
        Date d = null;

        String query = "SELECT "
                + ItemDatabase.ITEM_COL_EXP + " FROM "
                + ItemDatabase.TABLE_ITEM + " WHERE "
                + ItemDatabase.ITEM_COL_INV + " = "
                + String.valueOf(inventoryKey)
                + ";";

        Cursor c = itemDatabase.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                if(oldestDate == null){
                    oldestDate = DH.itemStringToDate(c.getString(0));
//                    dateFormat.format(oldestDate);
                } else {
                    d = DH.itemStringToDate(c.getString(0));
//                    dateFormat.format(d);

                    if(d.before(oldestDate)){
                        oldestDate = d;
                    }
                }
            } while(c.moveToNext());
        }
        c.close();

        ContentValues values = new ContentValues();
        values.put(ItemDatabase.INV_COL_SED, dateFormat.format(oldestDate));

        query = ItemDatabase.KEY_ID + " = ?";
        String whereArgs[] = {String.valueOf(inventoryKey)};

        itemDatabase.update(ItemDatabase.TABLE_INVENTORY, values, query, whereArgs);

    }

    private void appendInventoryNotes(int inventoryKey){

        itemDatabase = getWritableDatabase();

        StringBuilder sb = new StringBuilder();

        String query = "SELECT "
                + ItemDatabase.ITEM_COL_NOTE + " FROM "
                + ItemDatabase.TABLE_ITEM + " WHERE "
                + ItemDatabase.ITEM_COL_INV + " = "
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
        values.put(ItemDatabase.INV_COL_NOTE, sb.toString());

        query = ItemDatabase.KEY_ID + " = ?";
        String whereArgs[] = {String.valueOf(inventoryKey)};

        itemDatabase.update(ItemDatabase.TABLE_INVENTORY, values, query, whereArgs);

    }



    private String getFormattedDate(Date date){
        String result = null;

        if (date != null) {
            result = dateFormat.format(date);
        }

        return result;
    }



    private void addCategory(String entry) {
        itemDatabase = getWritableDatabase();

        String query = "INSERT INTO "
                + ItemDatabase.TABLE_CATEGORIES + '('
                + ItemDatabase.CAT_COL_CAT + ") VALUES ('"
                + entry + "');";

        itemDatabase.execSQL(query);
    }

    private void addLocation(String entry) {
        itemDatabase = getWritableDatabase();

        String query = "INSERT INTO "
                + ItemDatabase.TABLE_LOCATIONS + '('
                + ItemDatabase.LOC_COL_LOC + ") VALUES ('"
                + entry + "');";

        itemDatabase.execSQL(query);
    }

    private int getForeignKey(String entry, String tableName, String columnName) {
        int foreignKey = 0;
        Cursor c = null;

        String query = "SELECT * FROM " + tableName
                + " WHERE " + columnName
                + " = '" + entry + "';";

        try {

            c = itemDatabase.rawQuery(query, null);

        } catch (SQLiteException e) {

            String eStr = e.toString();
            if(eStr.contains("no such column")) {

                if(tableName.equals(ItemDatabase.TABLE_CATEGORIES)){
                    addCategory(entry);
                } else if(tableName.equals(ItemDatabase.TABLE_LOCATIONS)){
                    addLocation(entry);
                } else {
                    throw e;
                }

                c = itemDatabase.rawQuery(query, null);

            } else {
                throw e;
            }
        }

        if((c != null) && (c.moveToFirst())){
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
            sb.append(ItemDatabase.KEY_ID);
            sb.append(" = ");
            sb.append(invID).append(';');
            itemDatabase.execSQL(sb.toString());
        }

        c.close();

        return values;
    }

    public Cursor getItems(){
        itemDatabase = getReadableDatabase();

        StringBuffer sb = new StringBuffer();

        sb.append("SELECT * FROM ");
        sb.append(ItemDatabase.TABLE_INVENTORY);
        sb.append(" INNER JOIN ");
        sb.append(ItemDatabase.TABLE_CATEGORIES);
        sb.append(" ON ");
        sb.append(ItemDatabase.INV_COL_CAT);
        sb.append(" = ");
        sb.append(ItemDatabase.TABLE_CATEGORIES).append('.').append(ItemDatabase.KEY_ID);
        sb.append(';');

        return itemDatabase.rawQuery(sb.toString(),null);
    }

    protected Cursor getItems(Collection<String> typeFilters, Collection<String> locationFilters){
        itemDatabase = getReadableDatabase();
        Cursor c = null;
        StringBuffer query = new StringBuffer();

        // FIXME: Fix checking for filters being null
        if ((typeFilters == null) || (locationFilters == null)) {
            c = getItems();
        }
        else if((typeFilters.isEmpty()) && (locationFilters.isEmpty())) {
            c = getItems();
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

    protected List<String> getCategories(){
        itemDatabase = getReadableDatabase();

        String query = "SELECT "
                + ItemDatabase.CAT_COL_CAT + " FROM "
                + ItemDatabase.TABLE_CATEGORIES + ';';

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
                + ItemDatabase.LOC_COL_LOC + " FROM "
                + ItemDatabase.TABLE_LOCATIONS + ';';

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
