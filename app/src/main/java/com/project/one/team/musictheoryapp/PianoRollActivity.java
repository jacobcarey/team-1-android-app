package com.project.one.team.musictheoryapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Cogythea on 23/11/2016.
 */

public class PianoRollActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Piano piano = new Piano(this);
        setContentView(piano);
//        LinearLayout l = new LinearLayout(this);
//        l.setOrientation(LinearLayout.HORIZONTAL);
//        l.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        l.addView(piano);
    }
}
