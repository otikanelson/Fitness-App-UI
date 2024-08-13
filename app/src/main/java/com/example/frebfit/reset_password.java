package com.example.frebfit;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class reset_password extends AppCompatActivity {

    private EditText otpEditText, newPasswordEditText, confirmPasswordEditText;
    private String email;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.reset_password_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);

        otpEditText = findViewById(R.id.reset_password_otp);
        newPasswordEditText = findViewById(R.id.reset_password_new);
        confirmPasswordEditText = findViewById(R.id.reset_password_confirm);
        Button resetPasswordButton = findViewById(R.id.reset_password_button);

        email = getIntent().getStringExtra("email");

        resetPasswordButton.setOnClickListener(v -> resetPassword());
    }

    private void resetPassword() {
        String otp = otpEditText.getText().toString();
        String newPassword = newPasswordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (otp.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("otp_table", new String[]{"otp"}, "email=? AND otp=?", new String[]{email, otp}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();

            ContentValues values = new ContentValues();
            values.put("password", newPassword);
            db.update("users", values, "email=?", new String[]{email});

            // Remove the used OTP
            db.delete("otp_table", "email=?", new String[]{email});

            Toast.makeText(this, "Password reset successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(reset_password.this, Login.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }
}
