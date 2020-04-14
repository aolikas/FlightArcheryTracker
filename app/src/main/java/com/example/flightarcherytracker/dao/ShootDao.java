package com.example.flightarcherytracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import com.example.flightarcherytracker.entity.Shoot;

import java.util.List;

@Dao
public interface ShootDao {
    @Query("select * from shoots")
    LiveData<List<Shoot>> getAllShoots();

    @Query("select * from shoots where training_id = :training_id")
    LiveData<List<Shoot>> getAllShootsByTrainingId(long training_id);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("select shoot_lat,shoot_lng from shoots where training_id = :training_id")
    LiveData<List<Shoot>> getAllShootLatLndByTrainingId(long training_id);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("select shoot_lat, shoot_lng, shoot_distance from shoots where training_id = :training_id")
    LiveData<List<Shoot>> getAllShootLatLngDistByTrainingId(long training_id);

    @Insert
    void insertShoot(Shoot shoot);

    @Update
    void updateShoot(Shoot shoot);

    @Delete
    void deleteShoot(Shoot shoot);

    @Query("delete from shoots")
    void deleteAllShoots();
}
