package com.project.one.team.musictheoryapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BasicSelectActivityV2 extends AppCompatActivity {

    public static final String EXTRA_TOPIC = "topic";
    private static final String GREY_OUT_COLOUR = "#888888"; //Defines the greyed out colour
    String[] Topics = new String[]{"intro", "mnotes", "smpnotelen", "advnotelen", "scaleconmaj", "scaleconmin"};
    int TOPIC_REACHED = 7; //How far the user has progressed TODO: save this externally
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_select_v2);
        getSupportActionBar().hide();

        Typeface kozukaTF = Typeface.createFromAsset(getAssets(), "fonts/Kozuka Gothic Pro M.ttf");

        TextView header = (TextView) findViewById(getResources().getIdentifier("headerText", "id", getPackageName()));
        //header.setTypeface(kozukaTF);

        for(int i = 0; i < Topics.length; i++)
        {
            LinearLayout l = (LinearLayout) findViewById(getResources().getIdentifier(Topics[i] + "Layout", "id", getPackageName()));
            ImageButton b = (ImageButton) findViewById(getResources().getIdentifier(Topics[i] + "Btn", "id", getPackageName()));
            TextView t = (TextView) findViewById(getResources().getIdentifier(Topics[i] + "Txt", "id", getPackageName()));
            //t.setTypeface(kozukaTF);
            if(i <= TOPIC_REACHED)
            {
                l.setOnClickListener(getListener("basic/content/" + Topics[i]));
                b.setOnClickListener(getListener("basic/content/" + Topics[i]));
            }
            else
            {
                //If the topic has not been reached (and therefore cannot be accessed)
                t.setTextColor(Color.parseColor(GREY_OUT_COLOUR));
            }
        }

        final ImageView headerLogo = (ImageView) findViewById(R.id.headerLogo);
        headerLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_down, R.anim.slide_down_out);
        NavUtils.navigateUpFromSameTask(this);
    }
}
