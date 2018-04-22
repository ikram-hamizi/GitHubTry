package com.example.usp05.githubtry;

import com.example.usp05.githubtry.data_model.ItemDatabaseSingleton;
import com.example.usp05.githubtry.user_handling.UserDatabaseSingleton;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by nathan on 4/22/18.
 */

public class ItemDatabaseUnitTest {

    private static ItemDatabaseSingleton IDS;

    @BeforeClass
    public static void setUpDatabaseConnection_BeforeRunningTests(){
        IDS = ItemDatabaseSingleton.getInstance();
    }





    @AfterClass
    public static void cleanUpDatabaseConnection_BeforeRunningTests(){
        IDS.shutdownDatabase();
    }
}
