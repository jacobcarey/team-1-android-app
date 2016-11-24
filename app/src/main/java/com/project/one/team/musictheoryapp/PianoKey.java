package com.project.one.team.musictheoryapp;

/**
 * Created by Cogythea on 23/11/2016.
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

    public void setKeyPressed(Boolean keyPressed) {
        this.keyPressed = keyPressed;
    }
    public Boolean getKeyPressed() {
        return keyPressed;
    }

    public PianoKey(float posx, float posy, float w, float h, String key, Colour col)
    {
        positionX = posx;
        positionY = posy;
        width = w;
        height = h;
        keyName = key;
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
