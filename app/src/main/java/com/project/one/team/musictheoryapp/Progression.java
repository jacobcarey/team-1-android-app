package com.project.one.team.musictheoryapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            database.child("users").child(userId).child("progression").child(difficulty).setValue(newValue);
        }

        Toast.makeText(context.getApplicationContext(), "progression/"+difficulty+" is: "+progression.getInt(difficulty, 0), Toast.LENGTH_SHORT).show();
    }

    public void reset(String difficulty) {
        SharedPreferences progression = context.getSharedPreferences("progression", MODE_PRIVATE);
        SharedPreferences.Editor editor = progression.edit();

        editor.putInt(difficulty, 0);
        editor.commit();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            database.child("users").child(userId).child("progression").child(difficulty).setValue(0);
        }

        Toast.makeText(context.getApplicationContext(), "progression/"+difficulty+" is: "+progression.getInt(difficulty, 0), Toast.LENGTH_SHORT).show();
    }

    public void resetAll() {
        SharedPreferences prefs = context.getSharedPreferences("progression", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("basic", 0);
        editor.putInt("intermediate", 0);
        editor.putInt("advanced", 0);
        editor.commit();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            database.child("users").child(userId).child("progression").child("basic").setValue(0);
            database.child("users").child(userId).child("progression").child("intermediate").setValue(0);
            database.child("users").child(userId).child("progression").child("advanced").setValue(0);
        }

        Toast.makeText(context.getApplicationContext(), "Reset all progression!", Toast.LENGTH_SHORT).show();
    }

    public int getProgression(final String difficulty) {
        final SharedPreferences progression = context.getSharedPreferences("progression", MODE_PRIVATE);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

            database.child("users").child(userId).child("progression").child(difficulty).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Long progressionData = dataSnapshot.getValue(Long.class);
                    if (progressionData == null)
                        progressionData = new Long(0); // If they have no data stored, default to 0.

                    int retrievedValue = progressionData.intValue();
                    SharedPreferences.Editor editor = progression.edit();
                    editor.putInt(difficulty, retrievedValue);
                    editor.commit();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

        }

        //Toast.makeText(context.getApplicationContext(), "Retrieved progression is: " + progression.getInt(difficulty, 0), Toast.LENGTH_SHORT).show();
        return progression.getInt(difficulty, 0);
    }
}
