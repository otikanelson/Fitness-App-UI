package com.example.frebfit;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Random;

public class forgot_password extends AppCompatActivity {

    private EditText emailEditText;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.forgot_password_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);

        emailEditText = findViewById(R.id.email);
        Button sendOtpButton = findViewById(R.id.forgot_password_button);
        TextView backToLogin = findViewById(R.id.back_to_login);

        sendOtpButton.setOnClickListener(v -> sendOtp());

        backToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(forgot_password.this, Login.class);
            startActivity(intent);
        });
    }

    private void sendOtp() {
        String email = emailEditText.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("users", new String[]{"email"}, "email=?", new String[]{email}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Email exists in the database, generate and store OTP
            String otp = generateOtp();
            storeOtp(email, otp);

            // Logic to send OTP to the provided email (simulated)
            Toast.makeText(this, "OTP sent to " + email, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(forgot_password.this, reset_password.class);
            intent.putExtra("email", email);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Email not registered", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        return String.valueOf(otp);
    }

    private void storeOtp(String email, String otp) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("otp", otp);
        db.insert("otp_table", null, values);
        db.close();
    }
}
