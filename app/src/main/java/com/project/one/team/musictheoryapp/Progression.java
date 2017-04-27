package com.project.one.team.musictheoryapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by oliver on 26/04/17.
 */

public class Progression {
    private static Progression ourInstance;
    private Context context;

    public static Progression getInstance(Context ct) {
        if (ourInstance == null)
            ourInstance = new Progression(ct);

        return ourInstance;
    }

    private Progression(Context ct) {
        context = ct;
    }

    public void increment(String difficulty) {
        SharedPreferences progression = context.getSharedPreferences("progression", MODE_PRIVATE);
        SharedPreferences.Editor editor = progression.edit();
        editor.putInt(difficulty, progression.getInt(difficulty, 0)+1);
        editor.commit();
        Toast.makeText(context.getApplicationContext(), "progression/"+difficulty+" is: "+progression.getInt(difficulty, 0), Toast.LENGTH_SHORT).show();
    }

    public void reset(String difficulty) {
        SharedPreferences progression = context.getSharedPreferences("progression", MODE_PRIVATE);
        SharedPreferences.Editor editor = progression.edit();
        editor.putInt(difficulty, 0);
        editor.commit();
        Toast.makeText(context.getApplicationContext(), "progression/"+difficulty+" is: "+progression.getInt(difficulty, 0), Toast.LENGTH_SHORT).show();
    }

    public void resetAll() {
        SharedPreferences prefs = context.getSharedPreferences("progression", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("basic", 0);
        editor.putInt("intermediate", 0);
        editor.putInt("advanced", 0);
        editor.commit();
        Toast.makeText(context.getApplicationContext(), "Reset all progression!", Toast.LENGTH_SHORT).show();
    }
}
