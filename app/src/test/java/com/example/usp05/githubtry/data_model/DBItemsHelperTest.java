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
public class DBItemsHelperTest {

    @Mock
    Context mMockContext;


    String username = "testUsername";

    @Mock
    User user = new User(username, null, null, null, null, null);

    private static final Object[] EMPTY = {};

    // Reflection is used to get private field/method access in certain classes (User class in this case)
//    public void reflect() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//
//        // Print all the method names & execution result
//        Method methods[] = userClass.getDeclaredMethods();
//        System.out.println("Access all the methods");
//        for (Method method : methods) {
//            System.out.println("Method Name: " + method.getName());
//            System.out.println("Return type: " + method.getReturnType());
//            method.setAccessible(true);
//            System.out.println(method.invoke(user, EMPTY) + "\n");
//        }
//
//        // Print all the field names & values
//        Field fields[] = userClass.getDeclaredFields();
//        System.out.println("Access all the fields");
//        for (Field field : fields) {
//            System.out.println("Field Name: " + field.getName());
//            field.setAccessible(true);
//            System.out.println(field.get(user) + "\n");
//        }
//    }

    static UserHandler UHT;
//    static User testUser;

//    User user = new User();
//    Class<?> userClass = user.getClass();

    @BeforeClass
    public static void setUpUserForTesting() throws Exception {
//        DBItemsHelperTest userHack = new DBItemsHelperTest();
//        userHack.reflect();


//        testUser.setUsername(username);
//        UHT = UserHandler.getInstance(testUser);
    }

    @Before
    public void setUp() throws Exception {
        UHT = UserHandler.getInstance();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreate() throws Exception {
        assertEquals(username, UHT.getUsername());
    }

    @Test
    public void onUpgrade() throws Exception {
    }

    @Test
    public void insertItem() throws Exception {
    }

    @Test
    public void insertedToast() throws Exception {
    }

    @Test
    public void searchItem() throws Exception {
    }

    @Test
    public void deleteItem() throws Exception {
    }

    @Test
    public void editItem() throws Exception {
    }

    @Test
    public void getItems() throws Exception {
    }

    @Test
    public void getItems1() throws Exception {
    }

    @Test
    public void getItemLocationDetails() throws Exception {
    }

    @Test
    public void getFilteredItems() throws Exception {
    }

    @Test
    public void getTypes() throws Exception {
    }

    @Test
    public void getLocations() throws Exception {
    }

    @Test
    public void getUniqueFilteredItems() throws Exception {
    }

    @Test
    public void getTotalQuantity() throws Exception {
    }

    @Test
    public void getDatabaseName() throws Exception {
    }

    @Test
    public void setWriteAheadLoggingEnabled() throws Exception {
    }

    @Test
    public void getWritableDatabase() throws Exception {
    }

    @Test
    public void getReadableDatabase() throws Exception {
    }

    @Test
    public void close() throws Exception {
    }

    @Test
    public void onConfigure() throws Exception {
    }

    @Test
    public void onCreate1() throws Exception {
    }

    @Test
    public void onUpgrade1() throws Exception {
    }

    @Test
    public void onDowngrade() throws Exception {
    }

    @Test
    public void onOpen() throws Exception {
    }

}