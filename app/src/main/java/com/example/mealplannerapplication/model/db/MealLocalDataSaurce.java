package com.example.mealplannerapplication.model.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mealplannerapplication.model.Pojos.Meal;

@Database(entities = {Meal.class}, version = 1)
public abstract class MealLocalDataSaurce extends RoomDatabase {
    private static MealLocalDataSaurce instance = null;
    public abstract DAO mealDao();
    public static synchronized MealLocalDataSaurce getInstance(android.content.Context context){
        if (instance == null){
            instance = androidx.room.Room.databaseBuilder(context.getApplicationContext(), MealLocalDataSaurce.class, "mealdb")
                    .build();
        }
        return instance;
    }
}
