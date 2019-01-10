package com.example.userlistexample;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.google.firebase.FirebaseApp;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Citrusbug. 
 * User: Ishan Vyas 
 * Date: 10/01/19 
 * Time: 12:00 PM 
 * Title : App Controller File
 * Description : This file will manage application configuration.
 */


public class AppController extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        FirebaseApp.initializeApp(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }
}
