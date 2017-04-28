package com.project.one.team.musictheoryapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        int newValue = progression.getInt(difficulty, 0)+1;
        editor.putInt(difficulty, newValue);
        editor.commit();

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("users").child(userId).child("progression").child(difficulty).setValue(newValue);

        Toast.makeText(context.getApplicationContext(), "progression/"+difficulty+" is: "+progression.getInt(difficulty, 0), Toast.LENGTH_SHORT).show();
    }

    public void reset(String difficulty) {
        SharedPreferences progression = context.getSharedPreferences("progression", MODE_PRIVATE);
        SharedPreferences.Editor editor = progression.edit();

        editor.putInt(difficulty, 0);
        editor.commit();

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("users").child(userId).child("progression").child(difficulty).setValue(0);

        Toast.makeText(context.getApplicationContext(), "progression/"+difficulty+" is: "+progression.getInt(difficulty, 0), Toast.LENGTH_SHORT).show();
    }

    public void resetAll() {
        SharedPreferences prefs = context.getSharedPreferences("progression", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("basic", 0);
        editor.putInt("intermediate", 0);
        editor.putInt("advanced", 0);
        editor.commit();

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("users").child(userId).child("progression").child("basic").setValue(0);
        database.child("users").child(userId).child("progression").child("intermediate").setValue(0);
        database.child("users").child(userId).child("progression").child("advanced").setValue(0);

        Toast.makeText(context.getApplicationContext(), "Reset all progression!", Toast.LENGTH_SHORT).show();
    }
}
