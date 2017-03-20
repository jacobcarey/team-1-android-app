package com.project.one.team.musictheoryapp;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by Jacob on 20/03/2017.
 */

public class Theoryously extends Application {

    private boolean nightMode = false;

    public boolean getNightMode() {
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
    }

}

