package com.tubes.kouveepetshop.Java;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.tubes.kouveepetshop.Activity.LoginActivity;
import com.tubes.kouveepetshop.Activity.MenuCustomerActivity;
import com.tubes.kouveepetshop.Activity.MenuCustomerServiceActivity;
import com.tubes.kouveepetshop.Activity.MenuOwnerActivity;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String BIRTHDATE = "BIRTHDATE";
    public static final String ADDRESS = "ADDRESS";
    public static final String ROLE = "ROLE";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String PHONENUMBER = "PHONENUMBER";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String id, String name, String birthdate, String address, String role, String username, String password, String phonenumber){

        editor.putBoolean(LOGIN, true);
        editor.putString(ID, id);
        editor.putString(NAME, name);
        editor.putString(BIRTHDATE, birthdate);
        editor.putString(ADDRESS, address);
        editor.putString(ROLE, role);
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);
        editor.putString(PHONENUMBER, phonenumber);
        editor.commit();

    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void loginOwner(){

        if (!this.isLoggin()){
            Intent i = new Intent(context, MenuCustomerActivity.class);
            context.startActivity(i);
            ((MenuOwnerActivity) context).finish();
        }
    }

    public void loginCS(){

        if (!this.isLoggin()){
            Intent i = new Intent(context, MenuCustomerActivity.class);
            context.startActivity(i);
            ((MenuCustomerServiceActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(BIRTHDATE, sharedPreferences.getString(BIRTHDATE, null));
        user.put(ADDRESS, sharedPreferences.getString(ADDRESS, null));
        user.put(ROLE, sharedPreferences.getString(ROLE, null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        user.put(PHONENUMBER, sharedPreferences.getString(PHONENUMBER, null));
        return user;
    }

    public void logoutOwner(){

        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MenuCustomerServiceActivity.class);
        context.startActivity(i);
        ((MenuOwnerActivity) context).finish();

    }

    public void logoutCS(){

        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MenuCustomerServiceActivity.class);
        context.startActivity(i);
        ((MenuCustomerServiceActivity) context).finish();

    }

}