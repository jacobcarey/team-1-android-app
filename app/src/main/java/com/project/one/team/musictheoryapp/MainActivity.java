package com.project.one.team.musictheoryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find button by ID.
        testButton = (Button) findViewById(R.id.testButtonId);
        testButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TextView txtView = (TextView) findViewById(R.id.testTextViewId);
                txtView.setText("Would you look at that!");
//                Test...
                System.out.println("Jacob");

            }
        });

    }


}
