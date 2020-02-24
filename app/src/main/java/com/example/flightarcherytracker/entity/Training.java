package com.example.flightarcherytracker.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.flightarcherytracker.helpers.TimestampConverter;

import java.util.Date;

@Entity(tableName = "trainings")
public class Training {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "training_id")
    private long training_id;

    @TypeConverters({TimestampConverter.class})
    private Date training_date;

    private Double training_lat;
    private Double training_lng;


    public Training(Date training_date, Double training_lat,
                    Double training_lng) {
        this.training_date = training_date;
        this.training_lat = training_lat;
        this.training_lng = training_lng;
    }

    public void setTrainingId(long id) {
        this.training_id = id;
    }

    public long getTrainingId() {
        return training_id;
    }

    public Date getTrainingDate() { return training_date; }

    public Double getTrainingLat() {
        return training_lat;
    }

    public Double getTrainingLng() {
        return training_lng;
    }
}
