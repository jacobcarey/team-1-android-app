package com.project.one.team.musictheoryapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDelegate;

/**
 * <p>{@link Application} descendant allowing the getting and setting of app-wide global variables.</p>
 * <p>
 * <p>Currently used to maintain logged in, and night mode state across the entire app. In the
 * case of night mode, the class makes use of Android's {@link SharedPreferences} framework to persist
 * the night mode state across app restarts.</p>
 *
 * @author Team One
 */

public class Theoryously extends Application {

    private boolean nightMode = false;
    private boolean signedIn = false;

    /**
     * Get user's current signed in status.
     *
     * @return Returns <code>true</code> if user is signed in, <code>false</code> otherwise.
     */
    public boolean getSignedIn() {
        return signedIn;
    }

    /**
     * Set the user's signed-in status.
     *
     * @param signedIn <code>true</code> if the user has been signed in, <code>false</code> if
     *                 user is not signed in.
     */
    public void setSignedIn(boolean signedIn) {
        this.signedIn = signedIn;
    }

    /**
     * Get the current night mode status of the app.
     *
     * @return Returns <code>true</code> if night mode is enabled, <code>false</code> otherwise.
     */
    public boolean getNightMode() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        nightMode = preferences.getBoolean("nightMode", false);
        return nightMode;
    }

    /**
     * Set the app-wide night mode toggle.
     *
     * @param nightMode <code>true</code> if night mode is to be enabled, <code>false</code> if
     *                  disabled.
     */
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

