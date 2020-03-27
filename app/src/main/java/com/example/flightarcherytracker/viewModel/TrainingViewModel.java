package com.example.flightarcherytracker.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.flightarcherytracker.entity.Training;
import com.example.flightarcherytracker.repository.TrainingRepository;

import java.util.List;

public class TrainingViewModel extends AndroidViewModel {

    private TrainingRepository mTrainingRepository;
    private LiveData<List<Training>> mAllTrainings;
    private final long id;

    public TrainingViewModel(@NonNull Application application, final long id) {
        super(application);
        this.id = id;
        mTrainingRepository = new TrainingRepository(application);
        mAllTrainings = mTrainingRepository.getAllTrainings();
    }

    public void insertTraining(Training training) {
        mTrainingRepository.insertTraining(training);
    }

    public void updateTraining(Training training) {
        mTrainingRepository.updateTraining(training);
    }

    public long insertTrainingWithId(Training training) {
        return mTrainingRepository.insertTrainingWithId(training);
    }

    public void deleteTraining(Training training) {
        mTrainingRepository.deleteTraining(training);
    }

    public void deleteAllTrainings() {
        mTrainingRepository.deleteAllTrainings();
    }

    public LiveData<List<Training>> getAllTrainings() {
        return mAllTrainings;
    }

    public long getTrainingById(long id) {
        return mTrainingRepository.getTrainingById(id);
    }
}
