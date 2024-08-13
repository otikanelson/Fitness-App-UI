package com.example.frebfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class program extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_program);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.programs_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Select Training level");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageButton btn1 = findViewById(R.id.beginner_chest_btn);
        btn1.setOnClickListener(v -> navigateToBeg_chest_activity());

        ImageButton btn2 = findViewById(R.id.beginner_Abs_btn);
        btn2.setOnClickListener(v -> navigateToBeg_abs_activity());

        ImageButton btn3 = findViewById(R.id.beginner_Arm_btn);
        btn3.setOnClickListener(v -> navigateToBeg_arm_activity());

        ImageButton btn4 = findViewById(R.id.beginner_Leg_btn);
        btn4.setOnClickListener(v -> navigateToBeg_leg_activity());

        ImageButton btn5 = findViewById(R.id.beginner_Shoulder_Back_btn);
        btn5.setOnClickListener(v -> navigateToBeg_chest_activity());

        ImageButton btn6 = findViewById(R.id.intermediate_chest_btn);
        btn6.setOnClickListener(v -> navigateToBeg_chest_activity());

        ImageButton btn7 = findViewById(R.id.inter_Abs_btn);
        btn7.setOnClickListener(v -> navigateToBeg_chest_activity());

        ImageButton btn8 = findViewById(R.id.intermediate_Arm_btn);
        btn8.setOnClickListener(v -> navigateToBeg_chest_activity());

        ImageButton btn9 = findViewById(R.id.intermediate_Leg_btn);
        btn9.setOnClickListener(v -> navigateToBeg_chest_activity());

        ImageButton btn10 = findViewById(R.id.intermediate_Shoulder_Back_btn);
        btn10.setOnClickListener(v -> navigateToBeg_chest_activity());

        ImageButton btn11 = findViewById(R.id.advanced_chest_btn);
        btn11.setOnClickListener(v -> navigateToBeg_chest_activity());

        ImageButton btn12 = findViewById(R.id.advanced_Abs_btn);
        btn12.setOnClickListener(v -> navigateToBeg_chest_activity());

        ImageButton btn13 = findViewById(R.id.advanced_Arm_btn);
        btn13.setOnClickListener(v -> navigateToBeg_chest_activity());

        ImageButton btn14 = findViewById(R.id.advanced_Leg_btn);
        btn14.setOnClickListener(v -> navigateToBeg_chest_activity());

        ImageButton btn15 = findViewById(R.id.advanced_Shoulder_Back_btn);
        btn15.setOnClickListener(v -> navigateToBeg_chest_activity());

    }

    private void navigateToBeg_chest_activity() {
        Intent intent = new Intent(program.this, beg_chest.class);
        startActivity(intent);
    }
    private void navigateToBeg_abs_activity() {
        Intent intent = new Intent(program.this, beg_abs.class);
        startActivity(intent);
    }
    private void navigateToBeg_arm_activity() {
        Intent intent = new Intent(program.this, beg_Arm.class);
        startActivity(intent);
    }
    private void navigateToBeg_leg_activity() {
        Intent intent = new Intent(program.this, beg_leg.class);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            overridePendingTransition(R.anim.card_enter, R.anim.card_exit);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.card_enter, R.anim.card_exit);
    }
}