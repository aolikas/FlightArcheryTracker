package com.example.flightarcherytracker.fragments;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.flightarcherytracker.R;
import com.example.flightarcherytracker.adapters.TrainingRecyclerViewAdapter;
import com.example.flightarcherytracker.entity.Training;
import com.example.flightarcherytracker.viewModel.TrainingViewModel;

import java.util.List;
import java.util.Objects;

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
                .getInstance(Objects.requireNonNull(getActivity()).getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity(), factory);

        mTrainingViewModel = viewModelProvider.get(TrainingViewModel.class);
        mTrainingViewModel.getAllTrainings().observe(getViewLifecycleOwner(), new Observer<List<Training>>() {
            @Override
            public void onChanged(List<Training> trainings) {
                adapter.setTrainings(trainings);
            }
        });

        adapter.setOnTrainingClickListener(new TrainingRecyclerViewAdapter.TrainingDetailAdapterListener() {
            @Override
            public void onShowMapTrainingClick(Training training) {

                ShootsMapFragment fragment = new ShootsMapFragment();
                long id = training.getId();
                double startLat = training.getTrainingLat();
                double startLng = training.getTrainingLng();

                Bundle args = new Bundle();
                args.putLong("id", id);
                args.putDouble("startLat", startLat);
                args.putDouble("startLng", startLng);
                fragment.setArguments(args);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_records, fragment, "ShowOnMapFragment")
                        .addToBackStack(null)
                        .commit();
                Log.d(TAG, "onShowMapTrainingClick: " + id);
            }

            @Override
            public void onDeleteTrainingClick(final int position) {

                View deleteTrainingView = Objects.requireNonNull(getActivity()).getLayoutInflater().inflate(R.layout.dialog_delete_training,
                        new LinearLayout(getActivity()), false);

                AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(getActivity());
                deleteDialogBuilder.setView(deleteTrainingView);
                deleteDialogBuilder.setCancelable(true);

                Button positive = deleteTrainingView.findViewById(R.id.dialog_delete_training_yes);
                Button negative = deleteTrainingView.findViewById(R.id.dialog_delete_training_no);

               final AlertDialog dialogDeleteTraining = deleteDialogBuilder.create();

               positive.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       mTrainingViewModel.deleteTraining(adapter.getTrainingAt(position));
                       dialogDeleteTraining.dismiss();
                   }
               });

               negative.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       dialogDeleteTraining.cancel();
                   }
               });


               dialogDeleteTraining.show();

            }

            @Override
            public void onSeeShootRecordsClick(Training training) {

                FragmentManager manager = getChildFragmentManager();
                // getFragmentManager();
                long id = training.getId();

                ShootsListFragment fragment = new ShootsListFragment();
                Bundle args = new Bundle();
                args.putLong("id", id);
                fragment.setArguments(args);
                fragment.show(manager, "ShootListFragment");
                Toast.makeText(getContext(), R.string.shots_list_toast, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
