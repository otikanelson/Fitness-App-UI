package com.example.frebfit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.frebfit.ActivityFragment;
import com.example.frebfit.R;

public class Final_q extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_final_q, container, false);

            Button doneButton = view.findViewById(R.id.btn_done);
            doneButton.setOnClickListener(v -> navigateToActivityFragment());

            return view;
        }

    private void navigateToActivityFragment() {Fragment activityFrag = new ActivityFragment();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, activityFrag)
                .commit();
    }
}