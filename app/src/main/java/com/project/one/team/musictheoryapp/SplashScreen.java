package com.project.one.team.musictheoryapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Simple Splash Screen activity that is displayed when the app is first opened. This class displays
 * the app's logo for a few seconds before navigating the user to the {@link MainPageActivity}.
 *
 * @author Team One
 */

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timerThread = new Thread(){
            public void run(){
                try
                {
//                    sleep(3000); //Duration of splash screen
                    sleep(5); // Set to make boot up faster when developing.
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    Intent intent = new Intent(SplashScreen.this, MainPageActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    //Destroys activity so users can't access the splash screen by pressing the back button
    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }
}
