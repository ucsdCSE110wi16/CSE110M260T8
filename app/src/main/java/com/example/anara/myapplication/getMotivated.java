package com.example.anara.myapplication;

import android.content.Context;

import com.firebase.client.Firebase;

/**
 * Created by anara on 3/3/2016.
 */
public class getMotivated extends android.app.Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        getMotivated.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return getMotivated.context;
    }
}
