package com.project.one.team.musictheoryapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Jacob on 27/11/2016.
 */

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.getDefaultNightMode();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        Typeface kozukaTF = Typeface.createFromAsset(getAssets(), "fonts/Kozuka Gothic Pro M.ttf");
        //Hide the action/title bar
        getSupportActionBar().hide();

//        Night Mode Button
        ImageButton nmButton = (ImageButton) findViewById(R.id.nightModeButton);
        nmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((Theoryously) getApplication()).getNightMode()) {
                    ((Theoryously) getApplication()).setNightMode(false);
                } else {
                    ((Theoryously) getApplication()).setNightMode(true);
                }
                recreate();
            }
        });

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

}
