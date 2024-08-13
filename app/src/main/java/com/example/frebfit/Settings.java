package com.example.frebfit;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    public static final String PREFS_NAME = "prefs";
    private static final String LOGIN_PREFS_NAME = "LoginPrefs";
    private static final String PREF_LOGGED_IN = "logged_in";
    private DBHelper dbHelper;
    private ImageView profileImageView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Settings_activity_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        SharedPreferences sharedPreferences1 = getSharedPreferences("user_profile", Context.MODE_PRIVATE);

        // Initialize TextView to display bio
        TextView bioTextView = findViewById(R.id.bio_text);

        // Retrieve bio from SharedPreferences
        String bio = sharedPreferences1.getString("bio", "");

        // Set bio to TextView
        bioTextView.setText(bio);

        profileImageView = findViewById(R.id.profile);
        sharedPreferences = getSharedPreferences("user_profile", Context.MODE_PRIVATE);

        // Load and display the profile image from SharedPreferences
        loadProfileImage();



        dbHelper = new DBHelper(this);
        ImageView profileImageView = findViewById(R.id.profile);
        TextView usernameTextView = findViewById(R.id.usernameTextView);
        TextView bioText = findViewById(R.id.bio_text);
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);

        dbHelper = new DBHelper(this);

        profileImageView.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.this, edit_profile.class);
            startActivityForResult(intent, 100);
        });

        findViewById(R.id.edit_btn).setOnClickListener(view -> {
            Intent intent = new Intent(Settings.this, edit_profile.class);
            startActivityForResult(intent, 100);
        });

        // Retrieve username from database
        String username = getUsernameFromDatabase();

        // Set the retrieved username to the TextView
        usernameTextView.setText(username);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isDarkModeEnabled = preferences.getBoolean("dark_mode", false);
        AppCompatDelegate.setDefaultNightMode(isDarkModeEnabled ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Settings");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SwitchCompat switchCompat = findViewById(R.id.switch_dark_mode);
        switchCompat.setChecked(isDarkModeEnabled);

        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("dark_mode", isChecked);
            editor.apply();
            AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            recreate();
        });

        ImageButton logOutButton = findViewById(R.id.log_out_btn);
        logOutButton.setOnClickListener(v -> {
            SharedPreferences loginPreferences = getSharedPreferences(LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor loginEditor = loginPreferences.edit();
            loginEditor.putBoolean(PREF_LOGGED_IN, false);
            loginEditor.apply();

            Intent intent = new Intent(Settings.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadProfileImage() {
        String profileImageName = sharedPreferences.getString("profile_image", "");

        if (!profileImageName.isEmpty()) {
            int imageResId = getResources().getIdentifier(profileImageName, "drawable", getPackageName());
            if (imageResId != 0) {
                profileImageView.setImageResource(imageResId);
            }
        } else {
            // Handle case when no profile image is selected (optional)
            // profileImageView.setImageResource(R.drawable.default_profile_image);
        }
    }

    @SuppressLint("Range")
    private String getUsernameFromDatabase() {
        String username = "Username"; // Default value or error handling if not found
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to fetch username from your users table
        Cursor cursor = db.rawQuery("SELECT username FROM users LIMIT 1", null);

        // Check if cursor has data
        if (cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex("username"));
        } else {
            Toast.makeText(this, "Username not found in database", Toast.LENGTH_SHORT).show();
        }

        // Close cursor and database connection
        cursor.close();
        db.close();

        return username;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
