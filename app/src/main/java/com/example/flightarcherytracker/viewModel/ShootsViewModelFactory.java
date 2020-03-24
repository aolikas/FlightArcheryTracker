package com.example.flightarcherytracker.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ShootsViewModelFactory  extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final Application application;

    public ShootsViewModelFactory(@NonNull Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == ShootViewModel.class) {
            return (T) new ShootViewModel(application);
        }
        return null;
    }

}
