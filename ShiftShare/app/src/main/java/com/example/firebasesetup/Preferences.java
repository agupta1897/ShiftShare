package com.example.firebasesetup;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

public class Preferences {

    public static SharedPreferences getPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLogin(Context context, boolean login){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean("LOGIN_PREF", login);
        editor.apply();
    }

    public static boolean getLogin(Context context){
        return getPreferences(context).getBoolean("LOGIN_PREF", false);
    }

    public static void setId(Context context, String id){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("USER_ID", id);
        editor.apply();
    }

    public static String getId(Context context){
        return getPreferences(context).getString("USER_ID", null);
    }

    public static void setDB(Context context, String db){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("USER_DB", db);
        editor.apply();
    }

    public static String getDB(Context context){
        return getPreferences(context).getString("USER_DB", null);
    }

}
