package com.example.flightarcherytracker.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.flightarcherytracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainingsSessionFragment extends Fragment {


    public TrainingsSessionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_trainings_session, container, false);
        TextView tv = view.findViewById(R.id.session);
        tv.setText("Hello Session");
        return view;
    }

}
