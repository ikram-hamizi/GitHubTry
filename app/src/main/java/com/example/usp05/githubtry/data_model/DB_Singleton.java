package com.example.usp05.githubtry.data_model;

/**
 * Created by nathan on 4/19/18.
 */

class DB_Singleton {
    private static final DB_Singleton ourInstance = new DB_Singleton();

    static DB_Singleton getInstance() {
        return ourInstance;
    }

    private DB_Singleton() {
    }
}
