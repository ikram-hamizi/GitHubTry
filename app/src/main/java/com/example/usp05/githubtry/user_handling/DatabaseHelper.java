package com.example.usp05.githubtry.user_handling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    private SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table users (username text primary key, password text not null, " +
            "password2 text not null, secQuestion1 text not null, secQuestion2 not null, secQuestion3 not null);";

    public DatabaseHelper(Context context) {
        super(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseHelper.TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + DatabaseHelper.TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public void insertUser(User a) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USERNAME, a.getUsername());
        values.put(DatabaseHelper.COLUMN_PASSWORD, a.getPassword());
        values.put(DatabaseHelper.COLUMN_PASSWORD2, a.getPassword2());
        values.put(DatabaseHelper.COLUMN_SECQUESTION1, a.getSecQuestion1());
        values.put(DatabaseHelper.COLUMN_SECQUESTION2, a.getSecQuestion2());
        values.put(DatabaseHelper.COLUMN_SECQUESTION3, a.getSecQuestion3());

        db.insert(DatabaseHelper.TABLE_NAME, null, values);
        db.close();
    }

    @SuppressWarnings("BreakStatement")
    public String searchUsername(String username) {
        db = getReadableDatabase();
        String query = "select username from " + DatabaseHelper.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String result = "not found";
        if(cursor.moveToFirst()) {
            do {
                String a = cursor.getString(0);
                if(a.equals(username)) {
                    result = "found";
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    @SuppressWarnings("BreakStatement")
    public String searchUsernameAndPassword(String username, String password) {
        db = getReadableDatabase();
        String query = "select username, password from " + DatabaseHelper.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String result = "not found";
        if(cursor.moveToFirst()) {
            do {
                // finds username
                String a = cursor.getString(0);
                if(a.equals(username)) {
                    // gets password of username holder
                    String b = cursor.getString(1);
                    // if input password and password var is same
                    if (b.equals(password)) {
                        result = "found";
                        break;
                    }
                }
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public Cursor getSecAnswers(String username) {
        db = getReadableDatabase();
        String query = "select " + COLUMN_SECQUESTION1 + ", " + COLUMN_SECQUESTION2 + ", " + COLUMN_SECQUESTION3 +
                " from " + TABLE_NAME +
                " where " + COLUMN_USERNAME + " = '" + username + "';";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void updatePassword(String username, String password) {
        db = getWritableDatabase();
        String query = "update " + TABLE_NAME +
                " set " + COLUMN_PASSWORD + " = '" + password +
                "' where " + COLUMN_USERNAME + " = '" + username + "';";
        db.execSQL(query);
        db.close();
    }

    public User getUser(String username){
        db = getReadableDatabase();
        String query = "select * from " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        User result = new User();
        if(cursor.moveToFirst()) {
            result.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
            result.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
            result.setSecQuestion1(cursor.getString(cursor.getColumnIndex(COLUMN_SECQUESTION1)));
            result.setSecQuestion2(cursor.getString(cursor.getColumnIndex(COLUMN_SECQUESTION2)));
            result.setSecQuestion3(cursor.getString(cursor.getColumnIndex(COLUMN_SECQUESTION3)));
        }
        cursor.close();
        return result;
    }
}

