package com.example.flightarcherytracker.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "shoots",
        foreignKeys = @ForeignKey(entity = Training.class,
                parentColumns = "id",
                childColumns = "training_id",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("training_id")})
public class Shoot {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "shoot_id")
    private Long shootId;

    @ColumnInfo(name = "shoot-description")
    private String shootDescription;

    @ColumnInfo(name = "shoot_lat")
    private Double shootLat;

    @ColumnInfo(name = "shoot_lng")
    private Double shootLng;

    @ColumnInfo(name = "shoot_distance")
    private Double shootDistance;

    @ColumnInfo(name = "training_id")
    private Long trainingId;

    public Shoot(Double shootLat, Double shootLng, String shootDescription,
                 Double shootDistance, Long trainingId) {
        this.shootLat = shootLat;
        this.shootLng = shootLng;
        this.shootDescription = shootDescription;
        this.shootDistance = shootDistance;
        this.trainingId = trainingId;
    }

    public Long getShootId() {
        return shootId;
    }

    public void setShootId(long shootId) {
        this.shootId = shootId;
    }

    public String getShootDescription() { return shootDescription; }

    public Double getShootLat() {
        return shootLat;
    }

    public Double getShootLng() {
        return shootLng;
    }

    public Double getShootDistance() {
        return shootDistance;
    }

    public Long getTrainingId() {
        return trainingId;
    }
}
