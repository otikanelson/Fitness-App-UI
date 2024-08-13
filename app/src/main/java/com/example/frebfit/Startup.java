package com.example.frebfit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.frebfit.Login;
import com.example.frebfit.Signup;

public class Startup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Start), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int launchCount = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getInt("launchCount", 0);
        if (launchCount == 3) {
            finish();
        }


    ImageButton login_button = findViewById(R.id.login_btn);
        login_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Startup.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        ImageButton Signup_btn = findViewById(R.id.Signup_btn);
        Signup_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Startup.this, Signup.class);
                startActivity(intent);
                finish();
            }
        });

    }
}