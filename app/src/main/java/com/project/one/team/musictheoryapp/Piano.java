package com.project.one.team.musictheoryapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Bitmap;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * <p>Main Piano View that handles {@link PianoKey} touch events.</p>
 *
 * <p>The Piano view creates multiple PianoKeys, both Black and White keys, and draws them onto the screen.
 * Each key is given a name and a corresponding sound file that is in line with an actual piano.</p>
 *
 * <p>In order to play the correct sound, the Piano view gets the co-ordinates of the user's tap location
 * and compares it against the co-ordinates stored by each PianoKey using the
 * {@link PianoKey#pointIsOnKey(float, float) pointIsOnKey} method in the PianoKey objects. If the
 * co-ordinates match up, that particular key's sound file is played and a Toast notification is
 * displayed to the user with the name of the key they pressed.</p>
 *
 * <p>Unfortunately, due to the limitations of the MediaPlayer class used for playing the sound files,
 * multiple sound files cannot be played simultaneously.</p>
 *
 * @author Team One
 *
 * @see PianoRollActivity
 */

public class Piano extends View {


    Bitmap whitekey, blackkey, whitekeydown, blackkeydown;
    Paint paint = new Paint();
    int screenWidth;
    int screenHeight;
    int WHITE_KEY_WIDTH = 150;
    int BLACK_KEY_WIDTH = 90;
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
                        keyIndexToPitch(i, PianoKey.Colour.White) + String.valueOf(o),  //Key name (string concatenation)
                        MediaPlayer.create(getContext(), getResources().getIdentifier(keyIndexToPitch(i, PianoKey.Colour.White) + String.valueOf(o), "raw", getContext().getPackageName())),
                        PianoKey.Colour.White));

                if (i == 0 || i == 3) continue; //Skip adding a black key if it's b# or e#

                blackKeys.add(new PianoKey(
                        (i * WHITE_KEY_WIDTH) - (BLACK_KEY_WIDTH / 2) + ((o - startOctave) * octaveWidth),
                        0,
                        BLACK_KEY_WIDTH,
                        screenHeight / 2,
                        keyIndexToPitch(i, PianoKey.Colour.Black) + String.valueOf(o),
                        MediaPlayer.create(getContext(), getResources().getIdentifier(keyIndexToPitch(i, PianoKey.Colour.Black) + String.valueOf(o), "raw", getContext().getPackageName())),
                        PianoKey.Colour.Black));
            }
        }
    }

    public void draw(Canvas canvas)
    {
        super.draw(canvas);
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
            case MotionEvent.ACTION_MOVE:
            {
                String touchedKeyName = "";
                PianoKey touchedKey = null;
                //Give priority to black keys
                for(int i = 0; i < blackKeys.size(); i++)
                {
                    if(blackKeys.get(i).pointIsOnKey(event.getX(), event.getY()))
                    {
                        touchedKeyName = blackKeys.get(i).getKeyName();
                        blackKeys.get(i).setKeyPressed(true);
                        touchedKey = blackKeys.get(i);
                        Toast.makeText(getContext(), "Key pressed: " + blackKeys.get(i).getKeyName(), Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                //If a black key hasn't been touched
                if(touchedKeyName == "" && touchedKey == null)
                {
                    for(int i = 0; i < whiteKeys.size(); i++)
                    {
                        if(whiteKeys.get(i).pointIsOnKey(event.getX(), event.getY()))
                        {
                            touchedKeyName = whiteKeys.get(i).getKeyName();
                            touchedKey = whiteKeys.get(i);
                            whiteKeys.get(i).setKeyPressed(true);
                            Toast.makeText(getContext(), "Key pressed: " + whiteKeys.get(i).getKeyName(), Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                if(touchedKeyName != "")
                {
                    touchedKey.playSound();
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
