package com.example.flightarcherytracker;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.flightarcherytracker.dao.ShootDao;
import com.example.flightarcherytracker.dao.TrainingDao;
import com.example.flightarcherytracker.db.AppDatabase;
import com.example.flightarcherytracker.entity.Shoot;
import com.example.flightarcherytracker.entity.Training;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AppDatabaseTest {

    @Rule
    public TestRule rule =
            new InstantTaskExecutorRule();

    private TrainingDao trainingDao;
    private ShootDao shootDao;
    private AppDatabase db;
    private Training training1 = new Training(new Date(), 45.0, 45.0);
    private Training training2 = new Training(new Date(), 48.0, 95.0);
    private Training training3 = new Training(new Date(), 48.55, 95.78);

    @Before
    public void createDb() {

        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        trainingDao = db.getTrainingDao();
        shootDao = db.getShootDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertAndGetTraining() throws InterruptedException {
        long training_id = trainingDao.insertTrainingWithId(training1);
        Shoot shoot = new Shoot(34.9, 46.8, "this is shoot",
                45.9, training_id);
        shootDao.insertShoot(shoot);
        List<Training> allTrainings = LiveDataTestUtil.getValue(trainingDao.getAllTrainings());
        List<Shoot> allShoots = LiveDataTestUtil.getValue(shootDao.getAllShootsByTrainingId(training_id));

        assertEquals(allTrainings.get(0).getTrainingLat(), training1.getTrainingLat());
        assertEquals(allTrainings.get(0).getTrainingLng(), training1.getTrainingLng());
        assertEquals(allShoots.get(0).getShootDescription(), shoot.getShootDescription());
    }

    @Test
    public void getAllTrainings() throws InterruptedException {
        trainingDao.insertTraining(training2);
        trainingDao.insertTraining(training3);
        List<Training> allTrainings = LiveDataTestUtil.getValue(trainingDao.getAllTrainings());
        assertEquals(allTrainings.get(0).getTrainingLat(), training2.getTrainingLat());
        assertEquals(allTrainings.get(1).getTrainingLat(), training3.getTrainingLat());
        assertEquals(allTrainings.get(0).getTrainingLng(), training2.getTrainingLng());
        assertEquals(allTrainings.get(1).getTrainingLng(), training3.getTrainingLng());
    }

    public void deleteAll() throws InterruptedException {
        trainingDao.insertTraining(training1);
        trainingDao.insertTraining(training3);
        trainingDao.deleteAllTrainings();
        List<Training> allTrainings = LiveDataTestUtil.getValue(trainingDao.getAllTrainings());
        assertTrue(allTrainings.isEmpty());
    }
}
