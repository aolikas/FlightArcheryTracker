package com.example.flightarcherytracker.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.flightarcherytracker.entity.Shoot;
import com.example.flightarcherytracker.repository.ShootRepository;

import java.util.List;


public class ShootViewModel extends AndroidViewModel {

    private ShootRepository mShootRepository;
    private LiveData<List<Shoot>> mAllShoots;

    public ShootViewModel(@NonNull Application application) {
        super(application);
        mShootRepository = new ShootRepository(application);
        mAllShoots = mShootRepository.getAllShoots();
    }

    public void insertShoot(Shoot shoot) {
        mShootRepository.insertShoot(shoot);
    }

    public void updateShoot(Shoot shoot) {
        mShootRepository.updateShoot(shoot);
    }

    public void deleteShoot(Shoot shoot) {
        mShootRepository.deleteShoot(shoot);
    }

    public void deleteAllShoots() {
        mShootRepository.deleteAllShoots();
    }

    public LiveData<List<Shoot>> getAllShoots() {
        return mAllShoots;
    }

    public LiveData<List<Shoot>> getAllShootsByTrainingId(long trainingId) {
        return mShootRepository.getAllShootsByTrainingId(trainingId);
    }

    public LiveData<List<Shoot>> getAllShootsLatLngByTrainingId(long trainingId) {
        return mShootRepository.getAllShootLatLngByTrainingId(trainingId);
    }

    public LiveData<List<Shoot>> getAllShootLatLngDistByTrainingId(long trainingId) {
        return mShootRepository.getAllShootLatLngDistByTrainingId(trainingId);
    }
}