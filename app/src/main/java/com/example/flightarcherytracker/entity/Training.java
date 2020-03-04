package com.example.flightarcherytracker.entity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.flightarcherytracker.helpers.TimestampConverter;

import java.util.Date;

@Entity(tableName = "trainings")
@SuppressLint("ParcelCreator")
public class Training {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @TypeConverters({TimestampConverter.class})
    @ColumnInfo(name = "training_date")
    private Date trainingDate;

    @ColumnInfo(name = "training_lat")
    private Double trainingLat;
    @ColumnInfo(name = "training_lng")
    private Double trainingLng;


    public Training(Date trainingDate, Double trainingLat,
                    Double trainingLng) {
        this.trainingDate = trainingDate;
        this.trainingLat = trainingLat;
        this.trainingLng = trainingLng;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Date getTrainingDate() { return trainingDate; }

    public Double getTrainingLat() {
        return trainingLat;
    }

    public Double getTrainingLng() {
        return trainingLng;
    }
}
