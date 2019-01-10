package com.example.userlistexample;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Citrusbug. 
 * User: Ishan Vyas 
 * Date: 10/01/19 
 * Time: 12:00 PM 
 * Title : Preferences setting screen
 * Description : This file have all preferences.
 */

public class AppPref {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    public static final String PREF_APP = "appPref";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_FIREBASE_TOKEN = "firebasetoken";
    public static final String KEY_LOGGED = "logged";
    public static final String KEY_TOKEN = "login_token";
    public static final String KEY_USER_TYPE = "UserType";

    public AppPref(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public String getId() {
        return mSharedPreferences.getString(KEY_ID, "");
    }

    public void setId(String id) {
        this.mEditor.putString(KEY_ID, id);
        this.mEditor.apply();
    }

    public String getName() {
        return mSharedPreferences.getString(KEY_NAME, "");
    }

    public void setName(String name) {
        this.mEditor.putString(KEY_NAME, name);
        this.mEditor.apply();
    }

    public String getEmail() {
        return mSharedPreferences.getString(KEY_EMAIL, "");
    }

    public void setEmail(String email) {
        this.mEditor.putString(KEY_EMAIL, email);
        this.mEditor.apply();
    }

    public String getFirebasetoken() {
        return mSharedPreferences.getString(KEY_FIREBASE_TOKEN, "");
    }

    public void setFirebasetoken(String firebasetoken) {
        this.mEditor.putString(KEY_FIREBASE_TOKEN, firebasetoken);
        this.mEditor.apply();
    }

    public boolean isLogged() {
        return mSharedPreferences.getBoolean(KEY_LOGGED, false);
    }

    public void setLogged(boolean logged) {
        this.mEditor.putBoolean(KEY_LOGGED, logged);
        this.mEditor.apply();
    }

    public String getImage() {
        return mSharedPreferences.getString(KEY_IMAGE, "");
    }

    public void setImage(String image) {
        this.mEditor.putString(KEY_IMAGE, image);
        this.mEditor.apply();
    }

    public String getAuthToken() {
        return mSharedPreferences.getString(KEY_TOKEN, "");
    }

    public void setAuthToken(String authToken) {
        this.mEditor.putString(KEY_TOKEN, authToken);
        this.mEditor.apply();
    }

    public String getUserType() {
        return mSharedPreferences.getString(KEY_USER_TYPE, "");
    }

    public void setUserType(String userType) {
        this.mEditor.putString(KEY_USER_TYPE, userType);
        this.mEditor.apply();
    }

    public void reset() {
        mEditor.clear();
        mEditor.commit();
    }
}
