package com.project.one.team.musictheoryapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Simple Activity for displaying team member names.
 *
 * @author Team One
 */

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        getSupportActionBar().hide();

        Typeface kozukaTF = Typeface.createFromAsset(getAssets(), "fonts/Kozuka Gothic Pro M.ttf");

        TextView header = (TextView) findViewById(getResources().getIdentifier("title", "id", getPackageName()));
        header.setTypeface(kozukaTF);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_down, R.anim.slide_down_out);
    }
}
