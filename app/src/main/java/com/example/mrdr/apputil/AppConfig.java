package com.example.mrdr.apputil;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mrdr.R;

public class AppConfig {
    private Context context;
    private SharedPreferences sharedPreferences;

    public AppConfig(Context context) {
        this.context = context;

        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file_key),Context.MODE_PRIVATE);
    }

    public boolean isUserLogin(){
        return sharedPreferences.getBoolean(context.getString(R.string.pref_is_user_login),false);
    }
    public void updateUserLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_is_user_login),status);
        editor.apply();
    }

    public void saveNameofUser(String name,String age,String place){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_name_of_user),name);
        editor.putString("ageofuser",age);
        editor.putString("placeofuser",place);
        editor.apply();
    }

    public void saveImageUrl(String url1){
    SharedPreferences.Editor editor1 = sharedPreferences.edit();
    editor1.putString("imageurl",url1);
    editor1.apply();
    }
    public String getImageUrl(){
        return  sharedPreferences.getString("imageurl","Unknown");
    }

    public String getNameofUser(){
        return sharedPreferences.getString(context.getString(R.string.pref_name_of_user),"Unknown");
    }
    public String getAgeofUser(){
        return sharedPreferences.getString(context.getString(R.string.pref_age_of_user),"Unknown");
    }
    public String getPlaceofUser(){
        return sharedPreferences.getString(context.getString(R.string.pref_place_of_user),"Unknown");
    }

}
