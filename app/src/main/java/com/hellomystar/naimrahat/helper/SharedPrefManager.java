package com.hellomystar.naimrahat.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.hellomystar.naimrahat.bhabnamentalhospital.LoginActivity;

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "naim_rahat";
    private static final String KEY_NAME = "keyname";
    private static final String KEY_age = "keyage";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "keyid";
    // appointment constants

    private static final String KEY_id ="keyid";
    private static final String KEY_drname ="keydrname";
    private static final String KEY_expertise ="keyexpertise";
    private static final String KEY_name ="keyname";
    private static final String KEY_username ="keyusername";
    private static final String KEY_email ="keyemail";
    private static final String KEY_date ="keydate";


    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    /*public void appointmentData(Appointment appointment){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_id,appointment.getId());
        editor.putString(KEY_drname,appointment.getDrname());
        editor.putString(KEY_expertise,appointment.getExpertise());
        editor.putString(KEY_name,appointment.getName());
        editor.putString(KEY_username,appointment.getUsername());
        editor.putString(KEY_email,appointment.getEmail());
        editor.apply();

    }*/

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_NAME,user.getName());
        editor.putString(KEY_age,user.getAge());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_GENDER, user.getGender());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }


    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_age,null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_GENDER, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }


}
