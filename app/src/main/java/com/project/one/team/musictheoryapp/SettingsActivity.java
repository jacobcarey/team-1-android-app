package com.project.one.team.musictheoryapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.getDefaultNightMode();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();

        ToggleButton nmButton = (ToggleButton) findViewById(R.id.nightModeToggle);
        if (((Theoryously) getApplication()).getNightMode()) {
            nmButton.setChecked(true);
        } else {
            nmButton.setChecked(false);
        }
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

        final EditText userNameInput = (EditText) findViewById(R.id.userName);
        userNameInput.setText(((Theoryously) getApplication()).getUserName());

        userNameInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameInput.setText("");
            }
        });

        Button userNameButton = (Button) findViewById(R.id.userNameButton);
        userNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userNameInput.getText().toString();
                ((Theoryously) getApplication()).setUserName(user);

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SettingsActivity.this, MainPageActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_left_out);
    }
}
