package com.example.abim.lks_hotel4_mobile_3_2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    SharedPreferences prefs;

    public Session(Context ctx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public void setUser(int id, String name){
        prefs.edit().putInt("id", id).commit();
        prefs.edit().putString("name", name).commit();
    }

    public int getId(){
        return prefs.getInt("id", 0);
    }

    public String getName(){
        return prefs.getString("name", "");
    }
}
