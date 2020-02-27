package com.example.flightarcherytracker;

import android.content.ContentValues;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.room.testing.MigrationTestHelper;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.flightarcherytracker.db.AppDatabase;
import com.example.flightarcherytracker.entity.Training;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class MigrationTest {

    private static final String TEST_DB_NAME = "migration_test";




    @Rule
    public MigrationTestHelper helper;

    public MigrationTest() {
        helper = new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                AppDatabase.class.getCanonicalName(),
        new FrameworkSQLiteOpenHelperFactory());
    }

    @Test
    public void migrateAll() throws IOException {
        //earliest version's db
        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB_NAME, 1);
        db.close();

      //  AppDatabase appDatabase = Room.databaseBuilder(
        //        InstrumentationRegistry.getInstrumentation().getTargetContext(),
          //      AppDatabase.class,
            //    TEST_DB_NAME)
              //  .addMigrations(ALL_MIGRATIONS).build();
     //   appDatabase.getOpenHelper().getWritableDatabase();
       // appDatabase.close();
    }




    //array of all future migration
   // private static final Migration[] ALL_MIGRATIONS  = new Migration[] {
            //MIGRATION_1_2, MIGRATION_2_3;
    //}


}
