package com.example.edje.studieoverzichttweepuntnul;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // na de delay moet er een intent gestart worden die navigeert naar de MainActivity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // sluit deze activity
                finish();
            }
        }, SPLASH_TIME_OUT); // na 2 seconde moet het splashscreen weer sluiten
    }
}