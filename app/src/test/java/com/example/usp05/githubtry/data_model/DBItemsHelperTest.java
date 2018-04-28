package com.example.usp05.githubtry.data_model;

import com.example.usp05.githubtry.user_handling.User;
import com.example.usp05.githubtry.user_handling.UserHandler;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.internal.matchers.InstanceOf;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Created by nathan on 4/28/18.
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class DBItemsHelperTest extends DBItemsHelper{

    static String username = "testUsername";
    static String dbName = "TEST_DATABASE";
    static UserHandler UHT;
    static DBItemsHelper DBH;

    @Mock
    static User user = new User(username, null, null, null, null, null);

    @Mock
    static Context mMockContext;

    public DBItemsHelperTest() {
        super(mMockContext);
    }

    @BeforeClass
    public static void setUpDBItemsHelperTest() throws Exception {
        // User must be set up before setting up database
        setUpUserForTest();
        setUpDatabaseForTest();
    }

    static void setUpUserForTest() {
        UHT = UserHandler.getInstance(user);
    }

    static void setUpDatabaseForTest() {
        DBH = new DBItemsHelper(mMockContext);
    }

    @Before
    public void setUp() throws Exception {
        UHT = UserHandler.getInstance();
    }

    String itemName = "Item Name";
    String itemType = "Item Type";
    String itemLocation = "Item Location";
    int itemQuantity = 50;
    Date itemPurchaseDate = Calendar.getInstance().getTime();
    Date itemExpirationDate = Calendar.getInstance().getTime();
    String itemNotes = "Item Notes";


    @Test
    public void insertItem() throws Exception {
        Item item = new Item();

        item.setName(itemName);
        item.setType(itemType);
        item.setUsername(username);
        item.setPurchaseDate(itemPurchaseDate);
        item.setExpirationDate(itemExpirationDate);
        item.setQuantity(itemQuantity);
        item.setLocation(itemLocation);
        item.setNotes(itemNotes);

        DBH.insertItem(item);

        boolean itemFound = false;
        Cursor cursor = DBH.getItems(username);
        if(cursor.moveToFirst()){
            do {
                String dbItemName = cursor.getString(cursor.getColumnIndex(DBH.ITEM_COL_NAME));
                if(dbItemName.equals(itemName)) {
                    String dbItemType = cursor.getString(cursor.getColumnIndex(DBH.ITEM_COL_TYPE));
                    if(dbItemType.equals(itemType)) {
                        int dbItemQty = cursor.getInt(cursor.getColumnIndex(DBH.ITEM_COL_QUANTITY));
                        if (dbItemQty == itemQuantity) itemFound = true;
                    }
                }
            } while(cursor.moveToNext());
        } else {
            fail("No items exist in database with test username.");
        }
        assertTrue(itemFound);
    }

//    @Test
//    public void test_insertedToast() throws Exception {
//    }
//
//    @Test
//    public void test_searchItem() throws Exception {
//    }
//
//    @Test
//    public void test_deleteItem() throws Exception {
//    }
//
//    @Test
//    public void test_editItem() throws Exception {
//    }
//
//    @Test
//    public void test_getItems() throws Exception {
//    }
//
//    @Test
//    public void test_getItems1() throws Exception {
//    }
//
//    @Test
//    public void test_getItemLocationDetails() throws Exception {
//    }
//
//    @Test
//    public void test_getFilteredItems() throws Exception {
//    }
//
//    @Test
//    public void test_getTypes() throws Exception {
//    }
//
//    @Test
//    public void test_getLocations() throws Exception {
//    }
//
//    @Test
//    public void test_getUniqueFilteredItems() throws Exception {
//    }
//
//    @Test
//    public void test_getTotalQuantity() throws Exception {
//    }

}