package com.kplar.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.kplar.R;

public class MyPrefernces {

    private SharedPreferences mSharedPrefrences;
    private Context context;


    public MyPrefernces(Context context) {
        this.mSharedPrefrences = context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);
        this.context = context;
    }


    public void writeLoginStatus(boolean status) {

        SharedPreferences.Editor editor = mSharedPrefrences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status), status);
        editor.commit();


    }

    public boolean readLoginStatus() {

        return mSharedPrefrences.getBoolean(context.getString(R.string.pref_login_status), false);
    }

    public void writeName(String name) {
        SharedPreferences.Editor editor = mSharedPrefrences.edit();
        editor.putString(context.getString(R.string.pref_user_name), name);
        editor.commit();


    }

    public String readName() {

        return mSharedPrefrences.getString(context.getString(R.string.pref_user_name), "Guest");


    }

    public String readUserId() {


        return mSharedPrefrences.getString(context.getString(R.string.pref_user_id), "UserId");
    }


    public void writeUserId(String userId) {

        SharedPreferences.Editor editor = mSharedPrefrences.edit();
        editor.putString(context.getString(R.string.pref_user_id), userId);
        editor.commit();

    }

    public void displayToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }
}
