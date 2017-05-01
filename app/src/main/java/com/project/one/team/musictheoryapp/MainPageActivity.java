package com.project.one.team.musictheoryapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.project.one.team.musictheoryapp.R.id.userName;

/**
 * <p>As the Main Page of the application, the user is directed here when the app is first opened
 * (after viewing the {@link SplashScreen}).</p>
 * <p>
 * <p>This activity acts as a main hub, allowing the user access to the rest of the app, including
 * the topic pages, the settings and the piano roll.</p>
 *
 * @author Team One
 * @see BasicSelectActivityV2
 * @see IntermediateSelectActivity
 * @see SettingsActivity
 * @see PianoRollActivity
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
        final MediaPlayer buttonMP = MediaPlayer.create(this, R.raw.menu_click);


        if (nightMode) {
            ((Theoryously) getApplication()).setNightMode(true);
        }


        setContentView(R.layout.activity_main_page);
        final TextView userNameField = (TextView) findViewById(userName);
        // Upon the status change on whether a user is signed in their name will appear on the main page.
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

        final Toast advancedToast = Toast.makeText(MainPageActivity.this,
                "Advanced Topics coming soon!", Toast.LENGTH_SHORT);

        Typeface kozukaTF = Typeface.createFromAsset(getAssets(), "fonts/Kozuka Gothic Pro M.ttf");
        //Hide the action/title bar
        getSupportActionBar().hide();

//        Settings
        ImageButton settingsCog = (ImageButton) findViewById(R.id.settingsCog);
        settingsCog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonMP.start();
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
                buttonMP.start();
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
                buttonMP.start();
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
                buttonMP.start();
                Intent i = new Intent(MainPageActivity.this, IntermediateSelectActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up,
                        R.anim.slide_up_out);
            }
        });

//        Advanced.
        Button advanced = (Button) findViewById(R.id.advanced);
        advanced.setTypeface(kozukaTF);
        advanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonMP.start();
                Intent i = new Intent(MainPageActivity.this, AdvancedSelectActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up,
                        R.anim.slide_up_out);
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
