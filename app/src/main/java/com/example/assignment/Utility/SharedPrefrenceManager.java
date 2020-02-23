package com.example.assignment.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

public class SharedPrefrenceManager {
    public static final String IS_FIRST_API_CALL = "isFirstApiCall";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    private Context context = null;

    public SharedPrefrenceManager(Context context) {
        this.context = context;
    }
    private SharedPreferences.Editor getEditor() {
        return PreferenceManager.getDefaultSharedPreferences(context).edit();
    }

    public void saveValue(String key, String value) {
        getEditor().putString(key, value).commit();
    }

    public String getValue(String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null);
    }

    public void clearSharedPreference() {
        getEditor().clear().commit();
    }

    public void removeValue(String key) {
        getEditor().remove(key).commit();
    }

    public boolean isTrue(String key) {
        return !isNull(key) && getValue(key).equalsIgnoreCase(TRUE);
    }

    public boolean isNull(String key) {
        return getValue(key) == null ;
    }

}
