package com.project.one.team.musictheoryapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jacob on 27/11/2016.
 */

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_down, R.anim.slide_down_out);
    }
}
