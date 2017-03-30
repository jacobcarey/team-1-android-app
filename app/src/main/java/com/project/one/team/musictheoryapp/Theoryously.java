package com.project.one.team.musictheoryapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by Jacob on 20/03/2017.
 */

public class Theoryously extends Application {

    private boolean nightMode = false;
    private String userName = "";

    public boolean getNightMode() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        nightMode = preferences.getBoolean("nightMode", false);
        return nightMode;
    }

    public void setNightMode(boolean nightMode) {
        this.nightMode = nightMode;
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("nightMode", nightMode);
        editor.apply();
    }

    public String getUserName() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userName = preferences.getString("userName", "");
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userName", userName);
        editor.apply();
    }
}

