package com.example.frebfit;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class WorkoutActivity extends AppCompatActivity {

    public static final String EXTRA_WORKOUT_TYPE = "workout_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.workout_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
                String workoutType = intent.getStringExtra(EXTRA_WORKOUT_TYPE);

                if (workoutType != null) {
                    loadFragment(workoutType);
                }
            }

            private void loadFragment(String workoutType) {
                Fragment fragment = null;

                switch (workoutType) {
                    case "chest":
                        fragment = new ChestFragment();
                        break;
                    case "arms":
                        fragment = new ArmsFragment();
                        break;
//                    case "abs":
//                        fragment = new AbsFragment();
//                        break;
//                    case "legs":
//                        fragment = new LegsFragment();
//                        break;
//                    case "back":
//                        fragment = new BackFragment();
//                        break;
//                    case "shoulders":
//                        fragment = new ShouldersFragment();
//                        break;
                }

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.commit();
                }
            }
        }