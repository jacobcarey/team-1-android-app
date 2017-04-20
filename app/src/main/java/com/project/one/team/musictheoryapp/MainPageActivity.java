package com.project.one.team.musictheoryapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.project.one.team.musictheoryapp.R.id.userName;

/**
 * Created by Jacob on 27/11/2016.
 */

public class MainPageActivity extends AppCompatActivity {

    private static final String TAG = "MainPageActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        boolean nightMode = ((Theoryously) getApplication()).getNightMode();
        if(nightMode){
            ((Theoryously) getApplication()).setNightMode(true);
        }

        setContentView(R.layout.activity_main_page);
        final TextView userNameField = (TextView) findViewById(userName);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    userNameField.setText("Logged in as: " + user.getDisplayName());
                    ((Theoryously) getApplication()).setSignedIn(true);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    userNameField.setText("");
                }
                // ...
            }
        };

        Typeface kozukaTF = Typeface.createFromAsset(getAssets(), "fonts/Kozuka Gothic Pro M.ttf");
        //Hide the action/title bar
        getSupportActionBar().hide();

//        Settings
        ImageButton settingsCog = (ImageButton) findViewById(R.id.settingsCog);
        settingsCog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainPageActivity.this, SettingsActivity.class); //Can use SettingsPreferenceActivity if we get it working
                startActivity(i);
                overridePendingTransition(R.anim.slide_left,
                        R.anim.slide_right_out);
            }
        });

//        Piano Roll button. To be changed!
        ImageButton pianoRoll = (ImageButton) findViewById(R.id.pianoRollBtn);
        pianoRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainPageActivity.this, PianoRollActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_right,
                        R.anim.slide_left_out);
            }
        });

//        Credits, when logo is pressed.
        ImageButton logo = (ImageButton) findViewById(R.id.main_Logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainPageActivity.this, CreditsActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up,
                        R.anim.slide_up_out);
            }
        });

//        Basic.
        Button basic = (Button) findViewById(R.id.basic);
        basic.setTypeface(kozukaTF);
        basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainPageActivity.this, BasicSelectActivityV2.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up,
                        R.anim.slide_up_out);
            }
        });

//        Intermediate.
        Button intermediate = (Button) findViewById(R.id.intermediate);
        intermediate.setTypeface(kozukaTF);
        intermediate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainPageActivity.this, BasicSelectActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_right,
                        R.anim.slide_left_out);
            }
        });

//        Advanced.
        Button advanced = (Button) findViewById(R.id.advanced);
        advanced.setTypeface(kozukaTF);
        advanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(MainPageActivity.this, AdvancedActivity.class);
//                startActivity(i);
//                overridePendingTransition(R.anim.slide_right,
//                        R.anim.slide_left_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(MainPageActivity.this, SettingsActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_left, R.anim.slide_right_out);
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
