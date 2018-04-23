//package com.example.usp05.githubtry.data_model;
//
//import com.example.usp05.githubtry.AppContext;
//import com.example.usp05.githubtry.data_model.ItemDatabaseSingleton;
//import com.example.usp05.githubtry.user_handling.UserDatabaseSingleton;
//
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.lang.reflect.Method;
//import java.text.ParseException;
//import java.util.Collection;
//import java.util.Date;
//
//import static com.example.usp05.githubtry.data_model.ItemDatabase.*;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.CoreMatchers.*;
//import static org.mockito.Mockito.*;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//import org.mockito.Mock;
//import org.mockito.internal.matchers.InstanceOf;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//
///**
// * Created by nathan on 4/22/18.
// */
//
//@RunWith(MockitoJUnitRunner.class)
//public class ItemDatabaseUnitTest extends SQLiteOpenHelper{
////
////    private static ItemDatabaseHelper helper;
////    private DateHandler DH = new DateHandler();
//
////    @Mock
////    Context mMockContext;
////
////    SQLiteDatabase DB;
////
////    public ItemDatabaseUnitTest() {
////        super(getApplicationContext(), "TEST_db", null, 1);
////    }
////
////    @Override
////    public void onCreate(SQLiteDatabase db) {
////        db.execSQL(CREATE_INVENTORY_TABLE);
////        DB = db;
////    }
////
////    @Override
////    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
////
////    }
//
//
////    @BeforeClass
////    public static void setUpDatabaseConnection_BeforeRunningTests(){
////        helper = new ItemDatabaseHelper(mMockContext, "TEST_db", null, DATABASE_VERSION);
////    }
//
//
//    @Test
//    public void testSimpleDatabaseCreation() throws Exception {
//        DB = getWritableDatabase();
//        assertNotNull(DB);
//        DB.close();
//    }
//
//    @Ignore
//    public void addItemToDatabase_SuccessIfItemReturns() throws ParseException {
//
//        ItemDatabaseHelper helper = new ItemDatabaseHelper(mMockContext, "TEST_db", null, DATABASE_VERSION);
//        DateHandler DH = new DateHandler();
//
//        ItemHandler item = new ItemHandler();
//
//        String name = "Milk";
//        int quantity = 10;
//        double price = 25.75;
//        Date expDate = DH.intToDate(7, 7, 2020);
//        Date purchDate = DH.itemStringToDate("4/20/2018");
//        String category = "Dairy";
//        String location = "Refrigerator";
//
//        item.setName(name);
//        item.setQuantity(quantity);
//        item.setTotalPrice(price);
//        item.setExpiration_date(expDate);
//        item.setPurchase_date(purchDate);
//        item.setCategory(category);
//        item.setLocation(location);
//        helper.addItem(item);
//
//        Cursor cursor = helper.getItems();
//        assertEquals(1, cursor.getCount());
//
//        cursor.moveToFirst();
//        assertEquals(name, cursor.getString(cursor.getColumnIndex(ItemDatabase.INV_COL_NAME)));
//        assertEquals(quantity, cursor.getInt(cursor.getColumnIndex(ItemDatabase.INV_COL_QTY)));
//
//
//        helper.closeDatabaseIfOpen();
//        helper.dropTables();
//    }
//
//
//
////    @AfterClass
////    public static void cleanUpDatabaseConnection_AfterRunningTests(){
////        helper.closeDatabaseIfOpen();
////        helper.dropTables();
////    }
//}
