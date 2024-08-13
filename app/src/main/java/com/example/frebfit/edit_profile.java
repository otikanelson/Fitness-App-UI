package com.example.frebfit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class edit_profile extends AppCompatActivity {

    private ImageView profileImageView;
    private EditText bioEditText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sharedPreferences = getSharedPreferences("user_profile", Context.MODE_PRIVATE);

        int[] imageButtonIds = {R.id.profile_1, R.id.profile_2, R.id.profile_3, R.id.profile_4,
                R.id.profile_5, R.id.profile_6, R.id.profile_7, R.id.profile_8};

        for (int id : imageButtonIds) {
            ImageView imageView = findViewById(id);
            imageView.setOnClickListener(view -> {
                profileImageView.setImageDrawable(imageView.getDrawable());

                String profileImageName = getResources().getResourceEntryName(imageView.getId());
                saveProfileImage(profileImageName);
            });
        }

        profileImageView = findViewById(R.id.pic);
        bioEditText = findViewById(R.id.Bio);
        sharedPreferences = getSharedPreferences("user_profile", Context.MODE_PRIVATE);

        // Load existing bio data if available
        loadBioData();

        // Save button to save changes
        MaterialButton saveButton = findViewById(R.id.save_btn);
        saveButton.setOnClickListener(view -> {
            saveBioChanges();
            setResult(RESULT_OK);
        });
    }
    public void onProfileImageClick(View view) {
        ImageView selectedImageView = (ImageView) view;
        profileImageView.setImageDrawable(selectedImageView.getDrawable());

        // Save the selected profile image to SharedPreferences
        saveProfileImage(selectedImageView);
    }

    private void saveProfileImage(String profileImageName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profile_image", profileImageName);
        editor.apply();
    }

    private void saveProfileImage(ImageView imageView) {
        String profileImageName = getResources().getResourceEntryName(imageView.getId());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profile_image", profileImageName);
        editor.apply();
    }

    private void loadBioData() {
        String bio = sharedPreferences.getString("bio", "");
        bioEditText.setText(bio);
    }

    private void saveBioChanges() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("bio", bioEditText.getText().toString());
        editor.apply();
    }
}
