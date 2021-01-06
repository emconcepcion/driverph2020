package com.cav.DriverphTruckerlearningPH2020;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(com.cav.DriverphTruckerlearningPH2020.SplashScreen.this, com.cav.DriverphTruckerlearningPH2020.Login.class);
                com.cav.DriverphTruckerlearningPH2020.SplashScreen.this.startActivity(mainIntent);
                com.cav.DriverphTruckerlearningPH2020.SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
//
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//
//                Intent mainIntent = new Intent(SplashScreen.this, Dashboard.class);
//                SplashScreen.this.startActivity(mainIntent);
//                SplashScreen.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);

    }
}