package com.DesignQuads.AssistanceFinder;

/**
 * Created by yodhbir singh on 9/16/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashScreen extends Activity {
    private PrefManager prefManager;

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       // prefManager = (PrefManager) getPreferences(Context.MODE_PRIVATE);
        prefManager = new PrefManager(this);



        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i;

                if (prefManager.isFirstTimeLaunch()) {
                    i = new Intent(SplashScreen.this, WelcomeActivity.class);
                    prefManager.setFirstTimeLaunch(false);
                } else {
                    i = new Intent(SplashScreen.this, MainActivity.class);
                }

                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
