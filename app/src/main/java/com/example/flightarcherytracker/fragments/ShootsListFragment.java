package com.example.flightarcherytracker.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flightarcherytracker.R;
import com.example.flightarcherytracker.adapters.ShootRecyclerViewAdapter;
import com.example.flightarcherytracker.entity.Shoot;
import com.example.flightarcherytracker.viewModel.ShootViewModel;

import java.util.List;


public class ShootsListFragment extends DialogFragment {


    public ShootsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shoots_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_shoots_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final ShootRecyclerViewAdapter adapter = new ShootRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        long trainingId = getArguments().getLong("id");

        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity(), factory);
        ShootViewModel shootViewModel = viewModelProvider.get(ShootViewModel.class);
        shootViewModel.getAllShootsByTrainingId(trainingId).observe(getViewLifecycleOwner(), new Observer<List<Shoot>>() {
            @Override
            public void onChanged(List<Shoot> shoots) {
                adapter.setShoots(shoots);
            }
        });



        getDialog();
        return view;
    }
}
