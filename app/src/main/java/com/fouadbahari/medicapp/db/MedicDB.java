package com.fouadbahari.medicapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MedicData.class},version = 2, exportSchema = false)
public abstract class MedicDB extends RoomDatabase {


    private static MedicDB database;

    public synchronized static MedicDB getInstance(Context context){

        if (database == null){
            database = Room.databaseBuilder(context.getApplicationContext()
            ,MedicDB.class,"medicament_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract MedicDAO medicDAO();

}
