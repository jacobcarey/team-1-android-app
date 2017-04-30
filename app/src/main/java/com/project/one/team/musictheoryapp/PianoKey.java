package com.project.one.team.musictheoryapp;

import android.media.MediaPlayer;

import java.io.IOException;

import static java.security.AccessController.getContext;

/**
 * <p>Represents an interactive piano key used in the {@link Piano} Roll.</p>
 *
 * @author Team One
 *
 * @see PianoRollActivity
 */

public class PianoKey {

    public enum Colour
    {
        White,
        Black
    }
    private float positionX;
    private float positionY;
    private float width;
    private float height;
    private String keyName;
    private MediaPlayer mPlayer;
    public Boolean keyPressed;
    private Colour colour;

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public String getKeyName() {
        return keyName;
    }

    public void playSound()
    {
        mPlayer.start();
    }

    public void setKeyPressed(Boolean keyPressed) {
        this.keyPressed = keyPressed;
    }
    public Boolean getKeyPressed() {
        return keyPressed;
    }

    public PianoKey(float posx, float posy, float w, float h, String keyname, MediaPlayer player, Colour col)
    {
        positionX = posx;
        positionY = posy;
        width = w;
        height = h;
        keyName = keyname;
        mPlayer = player;
        try{mPlayer.prepare();}catch(Exception e) {e.printStackTrace();};
        colour = col;
        keyPressed = false;
    }

    public Boolean pointIsOnKey(float posx, float posy)
    {
        if(posx > positionX && posx < positionX + width
                && posy > positionY && posy < positionY + height)
            return true;

        return false;
    }
}
