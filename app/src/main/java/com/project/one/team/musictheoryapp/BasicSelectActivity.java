package com.project.one.team.musictheoryapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BasicSelectActivity extends AppCompatActivity {

    public static final String EXTRA_TOPIC = "topic";
    private static final String GREY_OUT_COLOUR = "#A0A0A0"; //Defines the greyed out colour
    String[] Topics = new String[]{"intro", "mnotes", "smpnotelen", "advnotelen"};
    int TOPIC_REACHED = 1; //How far the user has progressed TODO: save this externally
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_select);
        setTitle("Basic Topic Selection");

        for(int i = 0; i < Topics.length; i++)
        {
            LinearLayout l = (LinearLayout) findViewById(getResources().getIdentifier(Topics[i] + "Layout", "id", getPackageName()));
            ImageButton b = (ImageButton) findViewById(getResources().getIdentifier(Topics[i] + "Btn", "id", getPackageName()));
            if(i <= TOPIC_REACHED)
            {
                l.setOnClickListener(getListener(Topics[i]));
                b.setOnClickListener(getListener(Topics[i]));
            }
            else
            {
                //If the topic has not been reached (and therefore cannot be accessed)
                l.setBackgroundResource(R.drawable.cant_select_gradient);
                l.setClickable(false);
                TextView t = (TextView) findViewById(getResources().getIdentifier(Topics[i] + "Txt", "id", getPackageName()));
                t.setTextColor(Color.parseColor(GREY_OUT_COLOUR));
                b.setColorFilter(Color.parseColor(GREY_OUT_COLOUR));
            }
        }

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
