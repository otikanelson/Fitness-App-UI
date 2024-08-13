package com.example.frebfit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class q2 extends Fragment {

    private Survey surveyActivity; // Reference to the parent activity

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        surveyActivity = (Survey) requireActivity(); // Initialize reference to Survey activity
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_q2, container, false);

        Button nextButton = view.findViewById(R.id.btn_next);
        nextButton.setOnClickListener(v -> {
            surveyActivity.onNextButtonClicked(); // Call Survey activity method for next button click
        });
        Button backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            surveyActivity.onPreviousButtonClicked();
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}
