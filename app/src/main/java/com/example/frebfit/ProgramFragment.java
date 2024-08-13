package com.example.frebfit;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class ProgramFragment extends Fragment {
    ImageButton btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_program, container, false);


        btn = view.findViewById(R.id.start_btn);
        btn.setOnClickListener(v -> startActivity(new Intent(getActivity(), program.class)));

        Intent intent = new Intent("com.example.frebfit.HIGHLIGHT_DAY");
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        return view;
    }
}
