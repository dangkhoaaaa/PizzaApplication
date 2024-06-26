package com.example.pizzaapplication.share;
import android.content.Context;

public class DataLocalManager {
    private static final String TOKEN = "TOKEN";
    private static final String USER_ID = "USER_ID";
    private static final String TOKEN_SAVE_TIME = "TOKEN_SAVE_TIME";
    private static DataLocalManager instance;
    private MySharedPreferences mySharedPreferences;

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static DataLocalManager getInstance() {
        if (instance == null) {
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setToken(String token) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(TOKEN, token);
        DataLocalManager.getInstance().mySharedPreferences.putLong(TOKEN_SAVE_TIME, System.currentTimeMillis());
        scheduleTokenRemoval();
    }
    public static String getToken() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(TOKEN);
    }

    public static void setUserId(String id){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(USER_ID,id);
    }
    public static String getUserId(){
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(USER_ID);
    }

    public static void removeToken() {
        DataLocalManager.getInstance().mySharedPreferences.removeValue(TOKEN);
        DataLocalManager.getInstance().mySharedPreferences.removeValue(TOKEN_SAVE_TIME);
    }

    private static void scheduleTokenRemoval() {
        //Một phút có 60 giây và một giây có 1000 milliseconds, do đó 20 phút sẽ là:
        long delay = 12000000;
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                long saveTime =  DataLocalManager.getInstance().mySharedPreferences.getLong("TOKEN_SAVE_TIME", -1);
                if (saveTime != -1 && System.currentTimeMillis() - saveTime >= delay) {
                    removeToken();
                }
            }
        }, delay);
    }

}
