package com.example.frebfit;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class Survey extends AppCompatActivity {

    private ProgressBar progressBar;
    private int currentQuestionIndex = 1;

    private float height;
    private float weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        progressBar = findViewById(R.id.progress_bar);

        if (savedInstanceState == null) {
            loadQuestionFragment(getQuestionFragment(currentQuestionIndex));
            updateProgressBar();
        }
    }

    private Fragment getQuestionFragment(int index) {
        switch (index) {
            case 1:
                return new q1();
            case 2:
                return new q2();
            case 3:
                return new q3();
            case 4:
                return new q4();
            case 5:
                return new q5();
            case 6:
                return new q6();
            case 7:
                return new q7();
            case 8:
                return new q8();
            case 9:
                return new q9();
            case 10:
                return new q10();
            case 11:
                return new q11();
            case 12:
                return new q12();
            case 13:
                return new q13();
            case 14:
                return new Final_q();
            default:
                return new q1(); // Default to q1 if index is out of bounds
        }
    }

    private void loadQuestionFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,
                R.anim.slide_in_right, R.anim.slide_out_left);
        transaction.replace(R.id.survey_container, fragment);
        transaction.commit();
    }

    public void onNextButtonClicked() {
        currentQuestionIndex++;
        if (currentQuestionIndex > 14) {
            currentQuestionIndex = 1; // Restart from q1 if out of bounds
        }
        loadQuestionFragment(getQuestionFragment(currentQuestionIndex));
        updateProgressBar();
    }

    public void onPreviousButtonClicked() {
        currentQuestionIndex--;
        if (currentQuestionIndex < 1) {
            currentQuestionIndex = 15; // Cycle to final_q if out of bounds
        }
        loadQuestionFragment(getQuestionFragment(currentQuestionIndex));
        updateProgressBar();
    }

    private void updateProgressBar() {
        int totalQuestions = 15;
        int progress = (int) ((currentQuestionIndex / (float) totalQuestions) * 100);
        progressBar.setProgress(progress);
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public float calculateBMI() {
        return weight / (height * height);
    }
}
