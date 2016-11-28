package com.project.one.team.musictheoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.test.PerformanceTestCase;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Jacob on 27/11/2016.
 */

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        setTitle("Main Page");


//        Settings
        ImageButton settingsCog = (ImageButton) findViewById(R.id.settingsCog);
        settingsCog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainPageActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });

//        Credits, when logo is pressed.
        ImageButton logo = (ImageButton) findViewById(R.id.main_Logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainPageActivity.this, CreditsActivity.class);
                startActivity(i);
            }
        });

//        Basic.
        Button basic = (Button) findViewById(R.id.basic);
        basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(MainPageActivity.this, Basic.class);
//                startActivity(i);
            }
        });

//        Intermediate.
        Button intermediate = (Button) findViewById(R.id.intermediate);
        intermediate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(MainPageActivity.this, IntermediateActivity.class);
//                startActivity(i);
            }
        });

//        Advanced.
        Button advanced = (Button) findViewById(R.id.advanced);
        advanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(MainPageActivity.this, AdvancedActivity.class);
//                startActivity(i);
            }
        });
    }

}
