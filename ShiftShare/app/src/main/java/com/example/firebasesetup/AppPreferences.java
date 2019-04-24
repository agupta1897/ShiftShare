package com.example.firebasesetup;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreferences {

    private SharedPreferences preferences;

    public AppPreferences(){}

    public AppPreferences(Context context){

        preferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public Boolean getLoginPref(){

        return preferences.getBoolean("login", false);

    }

    public String getId(){

        return preferences.getString("id", null);

    }

    public String getDb(){

        return preferences.getString("db", null);

    }

//    public String getPhone(){
//
//        return preferences.getString("number", null);
//
//    }

    public void setLoginPref(Boolean login){

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("login", login);
        editor.apply();

    }

    public void setId(String id){

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", id);
        editor.apply();

    }

    public void setDb(String db){

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("db", db);
        editor.apply();

    }

}
