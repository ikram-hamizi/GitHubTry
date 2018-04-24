package com.example.usp05.githubtry.temp_backup;

/**
 * Created by nathan on 4/21/18.
 */

public class ItemDatabase {
    
    /*
    *
    * IMPORTANT! If any changes are made to the structure
    * of the database (e.g. changing the name of a column),
    * the database version MUST be updated!!
    *
    * */

    // Database information
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "USER_INVENTORY_DATABASE";
    public static final String ENABLE_FOREIGN_KEYS = "PRAGMA foreign_keys = ON;";


    // Database tables
     public static final String TABLE_INVENTORY = "INVENTORY";
     public static final String TABLE_ITEM = "ITEMS";
     public static final String TABLE_LOCATIONS = "LOCATIONS";
     public static final String TABLE_CATEGORIES = "CATEGORIES";

    /***** COLUMN NAMES FOR TABLES *****/
    // Common column names
    public static final String KEY_ID = "ID";
    public static final String KEY_CREATED_AT = "ENTRY_CREATION_DATE";

    // Inventory table -- column names
     public static final String INV_COL_NAME = "ITEM_NAME";
     public static final String INV_COL_QTY = "TOTAL_QUANTITY";
     public static final String INV_COL_SED = "SOONEST_EXPIRATION_DATE";
     public static final String INV_COL_CAT = "ITEM_CATEGORY";
     public static final String INV_COL_AVGP = "AVERAGE_PRICE";
     public static final String INV_COL_NOTE = "INVENTORY_NOTES";

    // ItemHandler table -- column names
     public static final String ITEM_COL_INV = "INVENTORY_ID";
     public static final String ITEM_COL_QTY = "ITEM_QUANTITY";
     public static final String ITEM_COL_EXP = "EXPIRATION_DATE";
     public static final String ITEM_COL_PDATE = "PURCHASE_DATE";
     public static final String ITEM_COL_TOTCOST = "TOTAL_COST";
     public static final String ITEM_COL_UNITCOST = "UNIT_COST";
     public static final String ITEM_COL_LOC = "ITEM_LOCATION";
     public static final String ITEM_COL_NOTE = "ITEM_NOTES";


    // Location table -- column names
     public static final String LOC_COL_LOC = "LOCATION";


    // Category table -- column names
    public static final String CAT_COL_CAT = "CATEGORY";


    /***** SQL STATEMENTS *****/
     public static final String CREATE_INVENTORY_TABLE = "CREATE TABLE "
            + TABLE_INVENTORY + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + INV_COL_NAME + " TEXT NOT NULL UNIQUE, "
            + INV_COL_QTY + " INTEGER NOT NULL, "
            + INV_COL_SED + " DATETIME, "
            + INV_COL_CAT + " INTEGER NOT NULL DEFAULT 0, "
            + INV_COL_AVGP + " REAL, "
            + INV_COL_NOTE + " REAL, "
            + KEY_CREATED_AT + " DATETIME, "
            + " FOREIGN KEY ("
            + INV_COL_CAT + ") REFERENCES "
            + TABLE_CATEGORIES + " ("
            + KEY_ID + ") ON DELETE SET DEFAULT"
            + ");";

     public static final String CREATE_ITEM_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_ITEM + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + ITEM_COL_INV + " INTEGER NOT NULL, "
            + ITEM_COL_QTY + " INTEGER NOT NULL, "
            + ITEM_COL_EXP + " DATETIME, "
            + ITEM_COL_PDATE + " DATETIME, "
            + ITEM_COL_TOTCOST + " REAL, "
            + ITEM_COL_UNITCOST + " REAL, "
            + ITEM_COL_LOC + " INTEGER NOT NULL DEFAULT 0, "
            + ITEM_COL_NOTE + " TEXT, "
            + KEY_CREATED_AT + " DATETIME, "
            + " FOREIGN KEY ("
            + ITEM_COL_INV + ") REFERENCES "
            + TABLE_INVENTORY + " ("
            + KEY_ID + ") ON DELETE CASCADE "
            + " FOREIGN KEY ("
            + ITEM_COL_LOC + ") REFERENCES "
            + TABLE_LOCATIONS + " ("
            + KEY_ID + ") ON DELETE SET DEFAULT"
            + ");";

     public static final String CREATE_LOCATION_TABLE = "CREATE TABLE "
            + TABLE_LOCATIONS + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + LOC_COL_LOC + " TEXT NOT NULL UNIQUE, "
            + KEY_CREATED_AT + " DATETIME" + ");";

     public static final String CREATE_CATEGORY_TABLE = "CREATE TABLE "
            + TABLE_CATEGORIES + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + CAT_COL_CAT + " TEXT NOT NULL UNIQUE, "
            + KEY_CREATED_AT + " DATETIME" + ");";

     public static final String DROP_INVENTORY_TABLE = "DROP TABLE IF EXISTS "
            + TABLE_INVENTORY + ";";

     public static final String DROP_ITEM_TABLE = "DROP TABLE IF EXISTS "
            + TABLE_ITEM + ";";

     public static final String DROP_LOCATION_TABLE = "DROP TABLE IF EXISTS "
            + TABLE_LOCATIONS + ";";

     public static final String DROP_CATEGORY_TABLE = "DROP TABLE IF EXISTS "
            + TABLE_CATEGORIES + ";";
}
