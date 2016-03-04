package com.example.anara.myapplication;

import com.firebase.client.Firebase;

/**
 * Created by anara on 3/3/2016.
 */
public class getMotivated extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
