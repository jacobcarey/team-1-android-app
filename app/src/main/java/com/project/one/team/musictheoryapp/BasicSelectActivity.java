package com.project.one.team.musictheoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class BasicSelectActivity extends AppCompatActivity {

    public static final String EXTRA_TOPIC = "topic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_select);
        setTitle("Basic");

        // Messy code to set up click actions for all buttons/textviews
        ImageButton introBtn = (ImageButton) findViewById(R.id.introBtn);
        introBtn.setOnClickListener(getListener("intro"));
        TextView introTxt = (TextView) findViewById(R.id.introTxt);
        introTxt.setOnClickListener(getListener("intro"));
        ImageButton mnotesBtn = (ImageButton) findViewById(R.id.mnotesbtn);
        mnotesBtn.setOnClickListener(getListener("mnotes"));
        TextView mnotesTxt = (TextView) findViewById(R.id.mnotesTxt);
        mnotesTxt.setOnClickListener(getListener("mnotes"));
//        ImageButton smpnotelenBtn = (ImageButton) findViewById(R.id.smpnotelenBtn);
//        smpnotelenBtn.setOnClickListener(getListener("smpnotelen"));
//        TextView smpnotelenTxt = (TextView) findViewById(R.id.smpnotelenTxt);
//        smpnotelenTxt.setOnClickListener(getListener("smpnotelen"));
//        ImageButton advnotelenBtn = (ImageButton) findViewById(R.id.advnotelenBtn);
//        advnotelenBtn.setOnClickListener(getListener("advnotelen"));
//        TextView advnotelenTxt = (TextView) findViewById(R.id.advnotelenTxt);
//        advnotelenTxt.setOnClickListener(getListener("advnotelen"));
    }

    private View.OnClickListener getListener(final String topic) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ContentActivity.class);
                i.putExtra(EXTRA_TOPIC, topic);
                startActivity(i);
            }
        };
    }
}
