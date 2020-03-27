package com.example.flightarcherytracker.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.flightarcherytracker.R;
import com.example.flightarcherytracker.adapters.TrainingRecyclerViewAdapter;
import com.example.flightarcherytracker.entity.Training;
import com.example.flightarcherytracker.viewModel.TrainingViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainingsRecordsFragment extends Fragment {

    private static final String TAG = "TrainingsRecordFragment";

    private TrainingViewModel mTrainingViewModel;

    public TrainingsRecordsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "TrainingRecords: onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trainings_records, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_records_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final TrainingRecyclerViewAdapter adapter = new TrainingRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity(), factory);
        mTrainingViewModel = viewModelProvider.get(TrainingViewModel.class);
        mTrainingViewModel.getAllTrainings().observe(getViewLifecycleOwner(), new Observer<List<Training>>() {
            @Override
            public void onChanged(List<Training> trainings) {
                adapter.setTrainings(trainings);
            }
        });

        return view;
    }

}
