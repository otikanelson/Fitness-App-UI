package com.example.frebfit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.frebfit.Login;
import com.example.frebfit.Startup;

public class Splash extends AppCompatActivity {


    private static final int SPLASH_TIME_OUT = 5000 ;
    private static final String PREFS_NAME = "preference";
    private static final String LAUNCH_COUNT_KEY = "Launch_Count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splash), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                int launchCount = preferences.getInt(LAUNCH_COUNT_KEY, 0);

                if (launchCount < 3) {
                    Intent intent = new Intent(Splash.this, Startup.class);
                    startActivity(intent);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt(LAUNCH_COUNT_KEY, launchCount + 1);
                    editor.apply();
                } else {
                    Intent intent = new Intent(Splash.this, Signup.class);
                    startActivity(intent);
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
