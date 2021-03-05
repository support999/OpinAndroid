package com.amit.opinverse;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class CurrentUser {
    static User user;
    static SharedPreferences sharedPreferences;
    static Context context;

    public static void setContext(Context context) {
        CurrentUser.context = context;
        sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
    }

    static void saveUser(){
        Gson gson = new Gson();
        String user_data = gson.toJson(user);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_data", user_data);
        editor.commit();
    }

    static void loadUser(){
        String user_data = sharedPreferences.getString("user_data", "");
        Gson gson = new Gson();
        user = gson.fromJson(user_data, User.class);
    }
}
