package com.example.flightarcherytracker.helpers;

import androidx.room.TypeConverter;

import java.util.Date;

public class TimestampConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {

        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date value) {

        return value == null ? null : value.getTime();
    }
}
