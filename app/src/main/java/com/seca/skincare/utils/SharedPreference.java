package com.seca.skincare.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    private static final String KEY_NAME = "DATABASE";
    private static SharedPreferences sharedPreference;
    private static SharedPreferences.Editor editor;
    private static final String AUTH_DATA = "AUTH_DATA";


    public static void storeData(Context context, String key, String value) {
        sharedPreference = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String retrieveData(Context context, String Key) {
        sharedPreference = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        return sharedPreference.getString(Key, null);
    }

    public static void removeAll(Context context) {
        sharedPreference = context.getSharedPreferences(AUTH_DATA, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
        editor.clear();
        editor.commit();
        editor.apply();

    }

    public static void removeKey(Context context, String Key) {
        sharedPreference = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
        editor.remove(Key);
        editor.commit();
    }

    public static void savePreferenceData(Context context, String Key, String val) {
        if(context != null) {
            sharedPreference = context.getSharedPreferences(AUTH_DATA, Context.MODE_PRIVATE);
            editor = sharedPreference.edit();
            editor.putString(Key, val);
            editor.commit();
        }
    }

    public static void savePreferenceData(Context context, String Key, int val) {
        sharedPreference = context.getSharedPreferences(AUTH_DATA, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
        editor.putInt(Key, val);
        editor.commit();
        editor.apply();
    }
    public static void savePreferenceData(Context context, String Key, Boolean val) {
        sharedPreference = context.getSharedPreferences(AUTH_DATA, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
        editor.putBoolean(Key, val);
        editor.commit();
        editor.apply();
    }
    public static String fetchPrefenceData(Context context, String key) {
        sharedPreference = context.getSharedPreferences(AUTH_DATA, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
        return sharedPreference.getString(key, "");
    }
    public static Boolean fetchPrefenceBoolData(Context context, String key) {
        sharedPreference = context.getSharedPreferences(AUTH_DATA, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
        return sharedPreference.getBoolean(key, false);
    }
    public static int fetchPrefenceIntData(Context context, String key) {
        sharedPreference = context.getSharedPreferences(AUTH_DATA, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
        return sharedPreference.getInt(key, 0);
    }

}


