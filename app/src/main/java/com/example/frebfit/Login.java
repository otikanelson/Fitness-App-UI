package com.example.frebfit;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    public static final String CHANNEL_ID = "My channel";
    public static final int NOTIFICATION_ID = 1;
    private NotificationManagerCompat notificationManager;
    private DBHelper dbHelper;
    private SharedPreferences sharedPreferences;

    public static final String KEY_USERNAME = "username"; // Key for username in SharedPreferences

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_login);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_layout), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

        notificationManager = NotificationManagerCompat.from(this);
        dbHelper = new DBHelper(this);
        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);

        boolean loggedIn = sharedPreferences.getBoolean("logged_in", false);
        if (loggedIn) {
            navigateToMain();
            return;
        }

        TextView signupLink = findViewById(R.id.Signup_link);
        signupLink.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Signup.class));
        });

        EditText usernameEditText = findViewById(R.id.Username);
        EditText passwordEditText = findViewById(R.id.Password);
        ImageButton loginButton = findViewById(R.id.login_btn);
        CheckBox rememberMeCheckbox = findViewById(R.id.remember_me_checkbox);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(username, password, rememberMeCheckbox.isChecked());
            }
        });
    }

    private void loginUser(String username, String password, boolean rememberMe) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        if (cursor.moveToFirst()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("logged_in", true);

            if (rememberMe) {
                editor.putBoolean("remember_me", true);
                editor.putString("username", username);
                editor.putString("password", password);
            } else {
                editor.remove("remember_me");
                editor.remove("username");
                editor.remove("password");
            }
            editor.apply();

            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            cursor.close();

            sendNotification();

            navigateToMain();
        } else {
            Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }

    private void sendNotification() {
        createNotificationChannel();

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.frebson_icon)
                .setContentTitle("User Logged In")
                .setContentText("Keep At It!")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My channel";
            String description = "My channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void navigateToMain() {
        startActivity(new Intent(Login.this, MainActivity.class));
        finish();
    }
}
