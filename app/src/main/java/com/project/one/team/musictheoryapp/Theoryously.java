package com.project.one.team.musictheoryapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDelegate;

/**
 * <p>{@link Application} descendant allowing the getting and setting of app-wide global variables.</p>
 *
 * <p>Currently used to maintain logged in, and night mode state across the entire app. In the
 * case of night mode, the class makes use of Android's {@link SharedPreferences} framework to persist
 * the night mode state across app restarts.</p>
 *
 * @author Team One
 */

public class Theoryously extends Application {

    private boolean nightMode = false;
    private boolean signedIn = false;

    public boolean getSignedIn() {
        return signedIn;
    }

    public void setSignedIn(boolean signedIn) {
        this.signedIn = signedIn;
    }

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

}

