package com.example.flightarcherytracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.flightarcherytracker.entity.Training;

import java.util.List;

@Dao
public interface TrainingDao {
    @Query("select * from trainings order by training_date DESC")
    LiveData<List<Training>> getAllTrainings();

    @Insert
    void insertTraining(Training training);

    @Insert
    long insertTrainingWithId(Training training);

    @Update
    void updateTraining(Training training);

    @Delete
    void deleteTraining(Training training);

    @Query("delete from trainings")
    void deleteAllTrainings();

    @Query("select * from trainings where training_id =:training_id")
    long getTrainingById(long training_id);
}
