package com.project.one.team.musictheoryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView testTextView = (TextView) findViewById(R.id.textView2);

        if (getIntent().hasExtra("topic")) {
            testTextView.setText(getIntent().getStringExtra("topic"));
        } else {
            testTextView.setText("No extra provided...");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right, R.anim.slide_left_out);
    }
}
