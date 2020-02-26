package com.example.flightarcherytracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;


import java.util.Date;

public class SqliteDatabaseTestHelper {
    public static void insertTraining(int id, Date date, double lat, double lng, SqliteTestDbOpenHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("training_id", id);


    }
}
