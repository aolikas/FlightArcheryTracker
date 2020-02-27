package com.example.flightarcherytracker.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.flightarcherytracker.dao.TrainingDao;
import com.example.flightarcherytracker.db.AppDatabase;
import com.example.flightarcherytracker.entity.Training;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static android.content.ContentValues.TAG;

public class TrainingRepository {

    private TrainingDao mTrainingDao;
    private LiveData<List<Training>> mAllTrainings;
    CountDownLatch mLatch;
    long rowId = -1;

    public TrainingRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        mTrainingDao = database.getTrainingDao();
        mAllTrainings = mTrainingDao.getAllTrainings();
    }

    public void insertTraining(Training training) {
        new InsertTrainingAsyncTask(mTrainingDao).execute(training);
    }

    public long insertTrainingWithId(Training training) {
        mLatch = new CountDownLatch(1);
        new InsertTrainingWithId(mTrainingDao).execute(training);
        try {
            mLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, String.valueOf(rowId));
        return rowId;
    }

    public void updateTraining(Training training) {
        new UpdateTrainingAsyncTask(mTrainingDao).execute(training);
    }

    public void deleteTraining(Training training) {
        new DeleteTrainingAsyncTask(mTrainingDao).execute(training);
    }

    public void deleteAllTrainings() {
        new DeleteAllTrainingsAsyncTask(mTrainingDao).execute();
    }

    public LiveData<List<Training>> getAllTrainings() {
        return mAllTrainings;
    }

    public long getTrainingById(long id) {
        return this.mTrainingDao.getTrainingById(id);
    }


    /////*******************AsyncTask Classes*****************************************


    class InsertTrainingWithId extends AsyncTask<Training, Void, Void> {
        private TrainingDao trainingDao;

        private InsertTrainingWithId(TrainingDao trainingDao) {
            this.trainingDao = trainingDao;
        }

        @Override
        protected Void doInBackground(Training... trainings) {
            rowId = trainingDao.insertTrainingWithId(trainings[0]);
            mLatch.countDown();
            return null;
        }
    }

    private static class InsertTrainingAsyncTask extends AsyncTask<Training, Void, Void> {
        private TrainingDao trainingDao;

        private InsertTrainingAsyncTask(TrainingDao trainingDao) {
            this.trainingDao = trainingDao;
        }

        @Override
        protected Void doInBackground(Training... trainings) {
            trainingDao.insertTraining(trainings[0]);
            return null;
        }
    }

    private static class UpdateTrainingAsyncTask extends AsyncTask<Training, Void, Void> {
        private TrainingDao trainingDao;

        private UpdateTrainingAsyncTask(TrainingDao trainingDao) {
            this.trainingDao = trainingDao;
        }

        @Override
        protected Void doInBackground(Training... trainings) {
            trainingDao.updateTraining(trainings[0]);
            return null;
        }
    }

    private static class DeleteTrainingAsyncTask extends AsyncTask<Training, Void, Void> {
        private TrainingDao trainingDao;

        private DeleteTrainingAsyncTask(TrainingDao trainingDao) {
            this.trainingDao = trainingDao;
        }

        @Override
        protected Void doInBackground(Training... trainings) {
            trainingDao.deleteTraining(trainings[0]);
            return null;
        }
    }

    private static class DeleteAllTrainingsAsyncTask extends AsyncTask<Void, Void, Void> {
        private TrainingDao trainingDao;

        private DeleteAllTrainingsAsyncTask(TrainingDao trainingDao) {
            this.trainingDao = trainingDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            trainingDao.deleteAllTrainings();
            return null;
        }
    }
}
