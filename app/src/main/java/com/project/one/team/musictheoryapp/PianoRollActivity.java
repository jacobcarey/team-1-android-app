package com.project.one.team.musictheoryapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * <p>Activity providing access to the interactive Piano Roll.</p>
 *
 * @author Team One
 *
 * @see Piano
 * @see PianoKey
 */

public class PianoRollActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Piano piano = new Piano(this);
        setContentView(piano);
    }
}
