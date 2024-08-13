package com.example.frebfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    public ImageButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, surveyBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize ImageButtons
        btn1 = view.findViewById(R.id.program_btn1);
        btn2 = view.findViewById(R.id.Session1);
        btn3 = view.findViewById(R.id.Session2);
        btn4 = view.findViewById(R.id.Session3);
        btn5 = view.findViewById(R.id.Session4);
        btn6 = view.findViewById(R.id.Session5);
        btn7 = view.findViewById(R.id.Session6);
        btn8 = view.findViewById(R.id.Session7);
        btn9 = view.findViewById(R.id.settings_btn);
        btn10 = view.findViewById(R.id.not_btn);
        btn11 = view.findViewById(R.id.Activity_button);
        btn12 = view.findViewById(R.id.find_instructors_btn);
        surveyBtn = view.findViewById(R.id.survey_btn);

        // Set click listeners
        btn1.setOnClickListener(v -> navigateToProgramFragment());
        btn2.setOnClickListener(v -> navigateToProgramFragment());
        btn3.setOnClickListener(v -> navigateToProgramFragment());
        btn4.setOnClickListener(v -> navigateToProgramFragment());
        btn5.setOnClickListener(v -> navigateToProgramFragment());
        btn6.setOnClickListener(v -> navigateToProgramFragment());
        btn7.setOnClickListener(v -> navigateToProgramFragment());
        btn8.setOnClickListener(v -> navigateToProgramFragment());
        btn9.setOnClickListener(v -> startActivityWithAnimation(Settings.class));
        btn10.setOnClickListener(v -> startActivityWithAnimation(notifications.class));
        btn11.setOnClickListener(v -> navigateToActivityFragment());
        btn12.setOnClickListener(v -> navigateToInstructorsFragment());
        surveyBtn.setOnClickListener(v -> startSurveyActivity());

        return view;
    }

    private void navigateToProgramFragment() {
        Fragment programFrag = new ProgramFragment();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, programFrag)
                .commit();
    }

    private void navigateToActivityFragment() {
        Fragment activityFrag = new ActivityFragment();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, activityFrag)
                .commit();
    }

    private void navigateToInstructorsFragment() {
        Fragment instructorsFrag = new InstructorsFragment();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, instructorsFrag)
                .commit();
    }

    private void startActivityWithAnimation(Class<?> activityClass) {
        Intent intent = new Intent(getActivity(), activityClass);
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.card_enter, R.anim.card_exit);
    }

    private void startSurveyActivity() {
        Intent intent = new Intent(getActivity(), Survey.class);
        startActivity(intent);
    }
}
