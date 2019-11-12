package com.perspective.tinaguisgdl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.perspective.tinaguisgdl.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Intent mainIntent;

                    mainIntent = new Intent().setClass(SplashScreenActivity.this, ActivityLoing.class);

                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();

                    startActivity(mainIntent);
            }
        };
        timer.schedule(task,1500);
    }
}
