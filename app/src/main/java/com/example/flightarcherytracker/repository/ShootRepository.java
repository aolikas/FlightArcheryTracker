package com.example.flightarcherytracker.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.flightarcherytracker.dao.ShootDao;
import com.example.flightarcherytracker.db.AppDatabase;
import com.example.flightarcherytracker.entity.Shoot;

import java.util.List;

public class ShootRepository {

    private ShootDao mShootDao;
    private LiveData<List<Shoot>> mAllShoots;

    public ShootRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        mShootDao = database.getShootDao();
        mAllShoots = mShootDao.getAllShoots();
    }

    public void insertShoot(Shoot shoot) {
        new InsertShootAsyncTask(mShootDao).execute(shoot);
    }

    public void updateShoot(Shoot shoot) {
        new UpdateShootAsyncTask(mShootDao).execute(shoot);
    }

    public void deleteShoot(Shoot shoot) {
        new DeleteShootAsyncTask(mShootDao).execute(shoot);
    }

    public void deleteAllShoots() {
        new DeleteAllShootsAsyncTask(mShootDao).execute();
    }

    public LiveData<List<Shoot>> getAllShoots() {
        return mAllShoots;
    }

    public LiveData<List<Shoot>> getAllShootsByTrainingId(long trainingId) {
        return this.mShootDao.getAllShootsByTrainingId(trainingId);
    }

    public LiveData<List<Shoot>> getAllShootLatLngByTrainingId(long trainingId) {
        return this.mShootDao.getAllShootLatLndByTrainingId(trainingId);
    }

    public LiveData<List<Shoot>> getAllShootLatLngDistByTrainingId(long trainingId) {
        return this.mShootDao.getAllShootLatLngDistByTrainingId(trainingId);
    }


    /////*******************AsyncTask Classes*****************************************


    private static class InsertShootAsyncTask extends AsyncTask<Shoot, Void, Void> {
        private ShootDao shootDao;

        private InsertShootAsyncTask(ShootDao shootDao) {
            this.shootDao = shootDao;
        }

        @Override
        protected Void doInBackground(Shoot... shoots) {
            shootDao.insertShoot(shoots[0]);
            return null;
        }
    }

    private static class UpdateShootAsyncTask extends AsyncTask<Shoot, Void, Void> {
        private ShootDao shootDao;

        private UpdateShootAsyncTask(ShootDao shootDao) {
            this.shootDao = shootDao;
        }

        @Override
        protected Void doInBackground(Shoot... shoots) {
            shootDao.updateShoot(shoots[0]);
            return null;
        }
    }

    private static class DeleteShootAsyncTask extends AsyncTask<Shoot, Void, Void> {
        private ShootDao shootDao;

        private DeleteShootAsyncTask(ShootDao shootDao) {
            this.shootDao = shootDao;
        }

        @Override
        protected Void doInBackground(Shoot... shoots) {
            shootDao.deleteShoot(shoots[0]);
            return null;
        }
    }

    private static class DeleteAllShootsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ShootDao shootDao;

        private DeleteAllShootsAsyncTask(ShootDao shootDao) {
            this.shootDao = shootDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            shootDao.deleteAllShoots();
            return null;
        }
    }
}