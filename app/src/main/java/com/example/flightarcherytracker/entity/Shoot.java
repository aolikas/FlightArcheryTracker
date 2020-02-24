package com.example.flightarcherytracker.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "shoots",
        foreignKeys = @ForeignKey(entity = Training.class,
                parentColumns = "shoot_id",
                childColumns = "training_id",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("training_id")})
public class Shoot {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "shoot_id")
    private long shoot_id;
    private String shoot_description;
    private double shoot_lat;
    private double shoot_lng;
    private double shoot_distance;
    @ColumnInfo(name = "training_id")
    private long training_id;

    public Shoot(double shoot_lat, double shoot_lng, String shoot_description,
                 double shoot_distance, long training_id) {
        this.shoot_lat = shoot_lat;
        this.shoot_lng = shoot_lng;
        this.shoot_description = shoot_description;
        this.shoot_distance = shoot_distance;
        this.training_id = training_id;
    }

    public long getShootId() {
        return shoot_id;
    }

    public void setShootId(long shoot_id) {
        this.shoot_id = shoot_id;
    }

    public String getShootDescription() { return shoot_description; }

    public double getShootLatitude() {
        return shoot_lat;
    }

    public double getShootLongitude() {
        return shoot_lng;
    }

    public double getShootDistance() {
        return shoot_distance;
    }

    public long getTrainingId() {
        return training_id;
    }
}
