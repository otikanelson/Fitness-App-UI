package com.example.frebfit;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class Signup extends AppCompatActivity {
    public static final String CHANNEL_ID = "MyChannel";
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager notificationManager;
    private DBHelper dbHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        boolean loggedIn = sharedPreferences.getBoolean("logged_in", false);
        if (loggedIn) {
            Intent intent = new Intent(Signup.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_signup);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Signup_Layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);
        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();

        TextView view = findViewById(R.id.Log_in_Link);
        view.setOnClickListener(v -> {
            Intent intent = new Intent(Signup.this, Login.class);
            startActivity(intent);
        });

        ImageButton signupButton = findViewById(R.id.Signup_btn);
        signupButton.setOnClickListener(v -> {
            EditText emailAddress = findViewById(R.id.Email_address);
            EditText username = findViewById(R.id.Username);
            EditText password = findViewById(R.id.Password);

            String emailAddressInput = emailAddress.getText().toString();
            String usernameInput = username.getText().toString();
            String passwordInput = password.getText().toString();

            if (emailAddressInput.isEmpty() || usernameInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(Signup.this, "Please input your info", Toast.LENGTH_SHORT).show();
            } else {
                registerUser(emailAddressInput, usernameInput, passwordInput);
            }
        });
    }

    private void registerUser(String email, String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query(DBHelper.TABLE_USERS,
                new String[]{DBHelper.COLUMN_USERNAME, DBHelper.COLUMN_EMAIL},
                DBHelper.COLUMN_USERNAME + "=? OR " + DBHelper.COLUMN_EMAIL + "=?",
                new String[]{username, email},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Toast.makeText(this, "Username or Email already exists", Toast.LENGTH_SHORT).show();
            cursor.close();
        } else {
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_USERNAME, username);
            values.put(DBHelper.COLUMN_PASSWORD, password);
            values.put(DBHelper.COLUMN_EMAIL, email);

            long newRowId = db.insert(DBHelper.TABLE_USERS, null, values);

            if (newRowId != -1) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("logged_in", true);
                editor.putString("username", username);
                editor.apply();

                Toast.makeText(this, "Sign Up Successful :> Welcome " + username, Toast.LENGTH_SHORT).show();
                saveNotification();

                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
                finish();
                showNotification();
            } else {
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        }
        db.close();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "MyChannel";
            String channelDescription = "My channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription(channelDescription);

            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.frebson_icon) // Make sure this drawable exists
                    .setContentTitle("New User")
                    .setContentText("Your fitness journey has begun!")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        } else {
            builder = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.frebson_icon)
                    .setContentTitle("New User")
                    .setContentText("Your fitness journey has begun!")
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        }

        Notification notification = builder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    @SuppressLint("MutatingSharedPrefs")
    private void saveNotification() {
        SharedPreferences notificationsPrefs = getSharedPreferences("notifications_prefs", Context.MODE_PRIVATE);
        Set<String> notificationsSet = notificationsPrefs.getStringSet("notifications", new HashSet<>());
        notificationsSet.add("New User" + "::" + "Your fitness journey has begun!");
        notificationsPrefs.edit().putStringSet("notifications", notificationsSet).apply();
    }
}