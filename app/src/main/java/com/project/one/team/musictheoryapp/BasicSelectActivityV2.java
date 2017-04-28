package com.project.one.team.musictheoryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BasicSelectActivityV2 extends AppCompatActivity {

    public static final String EXTRA_TOPIC = "topic";
    private static final String GREY_OUT_COLOUR = "#888888"; //Defines the greyed out colour
    private SharedPreferences progression; //How far the user has progressed
    String[] topics = new String[]{"intro", "mnotes", "smpnotelen", "advnotelen", "scaleconmaj", "scaleconmin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_select_v2);
        getSupportActionBar().hide();

        progression = getSharedPreferences("progression", MODE_PRIVATE);

        Typeface kozukaTF = Typeface.createFromAsset(getAssets(), "fonts/Kozuka Gothic Pro M.ttf");

        TextView header = (TextView) findViewById(getResources().getIdentifier("headerText", "id", getPackageName()));
        //header.setTypeface(kozukaTF);

        final ImageView headerLogo = (ImageView) findViewById(R.id.headerLogo);
        headerLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        int topicReached = Progression.getInstance(this).getProgression("basic");
        Toast.makeText(getApplicationContext(), "progression/basic is: "+topicReached, Toast.LENGTH_SHORT).show();

        for(int i = 0; i < topics.length; i++)
        {
            LinearLayout l = (LinearLayout) findViewById(getResources().getIdentifier(topics[i] + "Layout", "id", getPackageName()));
            ImageButton b = (ImageButton) findViewById(getResources().getIdentifier(topics[i] + "Btn", "id", getPackageName()));
            TextView t = (TextView) findViewById(getResources().getIdentifier(topics[i] + "Txt", "id", getPackageName()));
            //t.setTypeface(kozukaTF);

            l.setOnClickListener(getListener("basic/content/" + topics[i]));
            b.setOnClickListener(getListener("basic/content/" + topics[i]));

            if(i <= topicReached)
            {
                // Set the colours and styles to 'enabled'
                l.setBackgroundResource(R.drawable.can_select_background);
                l.setClickable(true);
                b.setClickable(true);
                b.setColorFilter(getResources().getColor(android.R.color.transparent));
                t.setTextColor(getResources().getColor(android.R.color.black));
            }
            else
            {
                //If the topic has not been reached (and therefore cannot be accessed)
                l.setBackgroundResource(R.drawable.cant_select_gradient);
                l.setClickable(false);
                b.setClickable(false);
                b.setColorFilter(Color.parseColor(GREY_OUT_COLOUR));
                t.setTextColor(Color.parseColor(GREY_OUT_COLOUR));
            }
        }
    }
    private View.OnClickListener getListener(final String topic) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ContentActivity.class);
                i.putExtra(ContentActivity.EXTRA_TOPIC, topic);
                startActivity(i);
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_down, R.anim.slide_down_out);
        NavUtils.navigateUpFromSameTask(this);
    }
}
