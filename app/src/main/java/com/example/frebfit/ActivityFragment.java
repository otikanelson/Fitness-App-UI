package com.example.frebfit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Calendar;
import java.util.HashSet;

public class ActivityFragment extends Fragment {

    private TextView textHeight, textWeight, textBMI;
    private CalendarView calendarView;
    private NumberPicker weightPicker, heightPicker;
    private HashSet<String> workoutDays;
    private BroadcastReceiver highlightReceiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);

        textHeight = view.findViewById(R.id.text_height);
        textWeight = view.findViewById(R.id.text_weight);
        textBMI = view.findViewById(R.id.text_bmi);
        calendarView = view.findViewById(R.id.calendar_view);
        weightPicker = view.findViewById(R.id.weight_picker);
        heightPicker = view.findViewById(R.id.height_picker);

        workoutDays = new HashSet<>();
        workoutDays.add("2023-06-22"); // Example date
        // Add more dates to workoutDays as needed

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            String date = year + "-" + (month + 1) + "-" + dayOfMonth;
            if (workoutDays.contains(date)) {
                // Perform an action if the date is a workout day
            }
        });

        weightPicker.setMinValue(30);
        weightPicker.setMaxValue(200);
        weightPicker.setValue(70); // Default value

        heightPicker.setMinValue(100);
        heightPicker.setMaxValue(250);
        heightPicker.setValue(170); // Default value

        highlightReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                highlightCurrentDay();
            }
        };
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(highlightReceiver, new IntentFilter("com.example.frebfit.HIGHLIGHT_DAY"));

        if (getActivity() instanceof Survey) {
            Survey activity = (Survey) getActivity();
            if (activity != null) {
                textHeight.setText("Height: " + activity.getHeight() + " cm");
                textWeight.setText("Weight: " + activity.getWeight() + " kg");
                textBMI.setText("BMI: " + activity.calculateBMI());
            }
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(highlightReceiver);
    }

    private void highlightCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        long today = calendar.getTimeInMillis();
        calendarView.setDate(today, true, true);
    }
}
