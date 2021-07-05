package com.example.aislechallenge.util;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 *  Preference helper class to store api key and logged in status
 */
public class AppPreferencesHelper {

    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    private static final String PREF_LOGIN_STATUS= "PREF_LOGIN_STATUS";
    private static final String PREF_FILE_NAME = "PREF_FILE";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context) {
        mPrefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public String getAccessToken() {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    public void setAccessToken(String accessToken) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    public boolean isLoggedIn() {
        return mPrefs.getBoolean(PREF_LOGIN_STATUS, false);
    }

    public void setLoggedIn(boolean loginStatus) {
        mPrefs.edit().putBoolean(PREF_LOGIN_STATUS, loginStatus).apply();
    }
}
