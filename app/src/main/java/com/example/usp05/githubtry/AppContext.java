package com.example.usp05.githubtry;

import android.app.Application;
import android.content.Context;

/**
 * Created by nathan on 4/26/18.
 */

public class AppContext extends Application {
    private static AppContext instance;

    public AppContext(){
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
}
