package com.lollipop.uprint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.lollipop.uprint.activity.LoginActivity;

import java.util.HashMap;

public class Config {
    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    public static final String SHARED_PREF_NAME = "myloginapp";
    public static final String LOGGEDIN = "loggedin";
    public static final String KEY_USER = "user";
    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_NUM = "number";

    public Config(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(SHARED_PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String id, String user){
        editor.putBoolean(LOGGEDIN, true);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_USER, user);
        editor.commit();
    }

    public void createBroadcastSession(String message, String number){
        editor.putBoolean(LOGGEDIN, true);
        editor.putString(KEY_MESSAGE, message);
        editor.putString(KEY_NUM, number);
        editor.commit();
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);
        }
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_USER, pref.getString(KEY_USER, null));

        return user;
    }

    public HashMap<String, String> getSessionDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_MESSAGE, pref.getString(KEY_MESSAGE, null));
        user.put(KEY_NUM, pref.getString(KEY_NUM, null));

        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(LOGGEDIN, false);
    }
}
