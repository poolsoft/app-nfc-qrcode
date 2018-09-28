package com.br.syncrename.Utils;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by matheus on 12/16/16.
 */
public class PreferenceHandler {

    public static final String SYNC_PREFS = "syncPrefs";

    public static String CORBACKGROUND = "corbackground_";
    public static String CORBOTAO = "corbotao_";

    // Shared Preferences
    public static SharedPreferences mSharedPreferences;

    public static void init (Activity main ){
        mSharedPreferences = main.getSharedPreferences(SYNC_PREFS,0);
    }

    public static void saveBackground(String cor){
        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.putString(CORBACKGROUND,cor);
        e.apply();
    }

    public static String getBackground(){
        return mSharedPreferences.getString(CORBACKGROUND,"9B9B9B");
    }

    public static void saveBotao(String cor){
        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.putString(CORBOTAO,cor);
        e.apply();
    }

    public static String getBotao(){
        return mSharedPreferences.getString(CORBOTAO,"F5A623");
    }
}

