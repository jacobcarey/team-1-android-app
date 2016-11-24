package com.project.one.team.musictheoryapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Bitmap;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Cogythea on 23/11/2016.
 */

public class Piano extends View {


    Bitmap whitekey, blackkey, whitekeydown, blackkeydown;
    Paint paint = new Paint();
    int screenWidth;
    int screenHeight;
    int WHITE_KEY_WIDTH = 120;
    int BLACK_KEY_WIDTH = 70;
    int octaves = 2;
    int octaveWidth;

    ArrayList<PianoKey> whiteKeys = new ArrayList<>();
    ArrayList<PianoKey> blackKeys = new ArrayList<>();


    public Piano (Context context)
    {
        super(context);
        whitekey = BitmapFactory.decodeResource(getResources(), R.drawable.piano_white);
        whitekeydown = BitmapFactory.decodeResource(getResources(), R.drawable.piano_white_down);
        blackkey = BitmapFactory.decodeResource(getResources(), R.drawable.piano_black);
        blackkeydown = BitmapFactory.decodeResource(getResources(), R.drawable.piano_black_down);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        whitekey = Bitmap.createScaledBitmap(whitekey, WHITE_KEY_WIDTH, screenHeight, false);
        blackkey = Bitmap.createScaledBitmap(blackkey, BLACK_KEY_WIDTH, screenHeight/2, false);

        octaveWidth = 7 * WHITE_KEY_WIDTH;
        octaves = (screenWidth / octaveWidth) + 1;

        for(int o = 0; o < octaves; o++)
        {
            for(int i = 0; i < 7; i++)
            {
                whiteKeys.add(new PianoKey(
                        (i * WHITE_KEY_WIDTH) + (o * octaveWidth),                      //Posx
                        0,                                                              //Posy
                        WHITE_KEY_WIDTH,                                                //Width
                        screenHeight,                                                   //Height
                        keyIndexToPitch(i, PianoKey.Colour.White) + String.valueOf(o),  //Key value
                        PianoKey.Colour.White));

                if (i == 0 || i == 3) continue;
                blackKeys.add(new PianoKey(
                        (i * WHITE_KEY_WIDTH) - (BLACK_KEY_WIDTH / 2) + (o * octaveWidth),
                        0,
                        BLACK_KEY_WIDTH,
                        screenHeight / 2,
                        keyIndexToPitch(i, PianoKey.Colour.Black) + String.valueOf(o),
                        PianoKey.Colour.Black));
            }
        }
    }

    public void draw(Canvas canvas)
    {
        for(int i = 0; i < whiteKeys.size(); i++)
        {
            canvas.drawBitmap((whiteKeys.get(i).getKeyPressed() ? whitekeydown : whitekey), whiteKeys.get(i).getPositionX(), whiteKeys.get(i).getPositionY(), paint);
        }
        for(int i = 0; i < blackKeys.size(); i++)
        {
            canvas.drawBitmap((blackKeys.get(i).getKeyPressed() ? blackkeydown : blackkey), blackKeys.get(i).getPositionX(), blackKeys.get(i).getPositionY(), paint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                int touchedKey = -1;
                //Give priority to black keys
                for(int i = 0; i < blackKeys.size(); i++)
                {
                    if(blackKeys.get(i).pointIsOnKey(event.getX(), event.getY()))
                    {
                        touchedKey = i;
                        blackKeys.get(i).setKeyPressed(true);
                        Toast.makeText(getContext(), "Key pressed: " + blackKeys.get(i).getKeyName(), Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                //If a black key hasn't been touched
                if(touchedKey < 0)
                {
                    for(int i = 0; i < whiteKeys.size(); i++)
                    {
                        if(whiteKeys.get(i).pointIsOnKey(event.getX(), event.getY()))
                        {
                            touchedKey = i;
                            whiteKeys.get(i).setKeyPressed(true);
                            Toast.makeText(getContext(), "Key pressed: " + whiteKeys.get(i).getKeyName(), Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            }
        }
        return true;
    }

    private String keyIndexToPitch(int index, PianoKey.Colour keyColour)
    {
        switch(keyColour)
        {
            case White:
                switch (index)
                {
                    case 0:
                        return "C";
                    case 1:
                        return "D";
                    case 2:
                        return "E";
                    case 3:
                        return "F";
                    case 4:
                        return "G";
                    case 5:
                        return "A";
                    case 6:
                        return "B";
                    default:
                        return "Invalid index";
                }
            case Black:
                switch (index)
                {
                    case 1:
                        return "C#";
                    case 2:
                        return "D#";
                    case 4:
                        return "F#";
                    case 5:
                        return "G#";
                    case 6:
                        return "A#";
                    default:
                        return "Invalid index";
                }
        }
        return "No key colour supplied";
    }
}
