package com.example.pizzaapplication.share;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private static final String MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES";
    //Environment variables
    private Context mContext;

    public MySharedPreferences(Context mContext) {
        this.mContext = mContext;
    }

    public void  putStringValue(String key,String value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public String getStringValue(String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }

    public  void putBooleanValue(String key,boolean value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public boolean getBooleanValue(String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }

    public void removeValue(String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public void putLong(String key, long value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key,value);
        editor.apply();
    }
    public long getLong(String key, long defaultValue){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key,defaultValue);
    }
}
