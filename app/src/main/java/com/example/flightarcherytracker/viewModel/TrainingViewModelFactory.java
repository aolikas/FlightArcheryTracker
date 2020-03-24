package com.example.flightarcherytracker.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TrainingViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final Application application;

    private final long id;

    public TrainingViewModelFactory(@NonNull Application application, long id) {
        this.application = application;
        this.id = id;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == TrainingViewModel.class) {
            return (T) new TrainingViewModel(application, id);
        }
        return null;
    }


}
