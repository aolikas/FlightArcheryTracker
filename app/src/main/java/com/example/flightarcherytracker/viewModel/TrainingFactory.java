package com.example.flightarcherytracker.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TrainingFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final Application application;

    public TrainingFactory(@NonNull Application application) {
    this.application = application;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == TrainingViewModel.class) {
            return (T) new TrainingViewModel(application);
        }
        return null;
    }

}
