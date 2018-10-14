package com.br.syncrename.Utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.br.syncrename.R;

/**
 * Created by matheus on 12/16/16.
 */
public class PreferenceHandler {

    public static final String SYNC_PREFS = "syncPrefs";

    public static String CORBACKGROUND = "corbackground_";
    public static String CORBOTAO = "corbotao_";
    public static String MODOLEITURA = "modo_leitura_";
    public static String FUNDOCOR = "fundo_cor_";

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

    public static void saveIdLeitura(int id){
        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.putInt(MODOLEITURA,id);
        e.apply();
    }

    public static int getIdLeitura(){
        return mSharedPreferences.getInt(MODOLEITURA,Constantes.VALUE_LEITURA);
    }

    public static void saveFundoCor(boolean fundo){
        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.putBoolean(FUNDOCOR,fundo);
        e.apply();
    }

    public static boolean getFundoCor(){
        return mSharedPreferences.getBoolean(FUNDOCOR,true);
    }

}

