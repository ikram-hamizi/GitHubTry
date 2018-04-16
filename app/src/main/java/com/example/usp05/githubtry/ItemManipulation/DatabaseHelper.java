package com.example.usp05.githubtry.ItemManipulation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.usp05.githubtry.UserHandling.User;

/**
 * Created by minh on 3/24/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PASSWORD2 = "password2";
    private static final String COLUMN_SECQUESTION1 = "secQuestion1";
    private static final String COLUMN_SECQUESTION2 = "secQuestion2";
    private static final String COLUMN_SECQUESTION3 = "secQuestion3";

    SQLiteDatabase db;

    public static final String TABLE_CREATE = "create table users (username text primary key, password text not null, " +
            "password2 text not null, secQuestion1 text not null, secQuestion2 not null, secQuestion3 not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public void insertUser(User a) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, a.getUsername());
        values.put(COLUMN_PASSWORD, a.getPassword());
        values.put(COLUMN_PASSWORD2, a.getPassword2());
        values.put(COLUMN_SECQUESTION1, a.getSecQuestion1());
        values.put(COLUMN_SECQUESTION2, a.getSecQuestion2());
        values.put(COLUMN_SECQUESTION3, a.getSecQuestion3());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String searchUsername(String username) {
        String a, result = "not found";
        db = this.getReadableDatabase();
        String query = "select username from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if(a.equals(username)) {
                    result = "found";
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        return result;
    }

    public String searchUsernameAndPassword(String username, String password) {
        String a, b, result = "not found";
        db = this.getReadableDatabase();
        String query = "select username, password from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                // finds username
                a = cursor.getString(0);
                if(a.equals(username)) {
                    // gets password of username holder
                    b = cursor.getString(1);
                    // if input password and password var is same
                    if (b.equals(password)) {
                        result = "found";
                        break;
                    }
                }
            }
            while(cursor.moveToNext());
        }
        return result;
    }
}

