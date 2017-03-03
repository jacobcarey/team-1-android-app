package com.project.one.team.musictheoryapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
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

    MediaPlayer mediaPlayer;

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
        //octaves = (screenWidth / octaveWidth) + 1;

        int startOctave = 3;
        int endOctave = 5; //Magic numbers :^)
        octaves = endOctave - startOctave;

        for(int o = startOctave; o < endOctave; o++)
        {
            for(int i = 0; i < 7; i++)
            {
                whiteKeys.add(new PianoKey(
                        (i * WHITE_KEY_WIDTH) + ((o - startOctave) * octaveWidth),      //Posx (a bit gory while I haven't got sounds for all octaves
                        0,                                                              //Posy
                        WHITE_KEY_WIDTH,                                                //Width
                        screenHeight,                                                   //Height
                        keyIndexToPitch(i, PianoKey.Colour.White) + String.valueOf(o),  //Key value
                        PianoKey.Colour.White));

                if (i == 0 || i == 3) continue;
                blackKeys.add(new PianoKey(
                        (i * WHITE_KEY_WIDTH) - (BLACK_KEY_WIDTH / 2) + ((o - startOctave) * octaveWidth),
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
                String touchedKeyName = "";
                //Give priority to black keys
                for(int i = 0; i < blackKeys.size(); i++)
                {
                    if(blackKeys.get(i).pointIsOnKey(event.getX(), event.getY()))
                    {
                        touchedKeyName = blackKeys.get(i).getKeyName();
                        blackKeys.get(i).setKeyPressed(true);
                        Toast.makeText(getContext(), "Key pressed: " + blackKeys.get(i).getKeyName(), Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                //If a black key hasn't been touched
                if(touchedKeyName == "")
                {
                    for(int i = 0; i < whiteKeys.size(); i++)
                    {
                        if(whiteKeys.get(i).pointIsOnKey(event.getX(), event.getY()))
                        {
                            touchedKeyName = whiteKeys.get(i).getKeyName();
                            whiteKeys.get(i).setKeyPressed(true);
                            Toast.makeText(getContext(), "Key pressed: " + whiteKeys.get(i).getKeyName(), Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                if(touchedKeyName != "")
                {
                    mediaPlayer = MediaPlayer.create(getContext(), getResources().getIdentifier(touchedKeyName, "raw", getContext().getPackageName()));
                    mediaPlayer.start();
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
                        return "c";
                    case 1:
                        return "d";
                    case 2:
                        return "e";
                    case 3:
                        return "f";
                    case 4:
                        return "g";
                    case 5:
                        return "a";
                    case 6:
                        return "b";
                    default:
                        return "Invalid index";
                }
            case Black:
                switch (index)
                {
                    case 1:
                        return "cs";
                    case 2:
                        return "ds";
                    case 4:
                        return "fs";
                    case 5:
                        return "gs";
                    case 6:
                        return "as";
                    default:
                        return "Invalid index";
                }
        }
        return "No key colour supplied";
    }
}
