package com.example.vasuchand.feedgen.Login;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Vasu Chand on 10/8/2016.
 */

public class Session {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "androidhive-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private String Module_Pref= "SharedPreference";

    public Session(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }
    public void setName(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences(Module_Pref, Context.MODE_PRIVATE).edit();
        editor.putString(key,value);
        editor.commit();

    }
    public void setEmail(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences(Module_Pref, Context.MODE_PRIVATE).edit();
        editor.putString(key,value);
        editor.commit();

    }
    public void setLogin(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences(Module_Pref, Context.MODE_PRIVATE).edit();
        editor.putString(key,value);
        editor.commit();

    }
    public void setToken(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences(Module_Pref, Context.MODE_PRIVATE).edit();
        editor.putString(key,value);
        editor.commit();

    }
    public  String getPreferences(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences(Module_Pref,	Context.MODE_PRIVATE);
        String position = prefs.getString(key, "...");
        return position;
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
