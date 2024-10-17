package com.example.mealplannerapplication.model.LocalDataSource.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mealplannerapplication.model.LocalDataSource.db.DAOs.MealInfoDAO;
import com.example.mealplannerapplication.model.LocalDataSource.db.DAOs.MealIngredientsDAO;
import com.example.mealplannerapplication.model.LocalDataSource.db.DAOs.MealPlanDAO;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealInfo;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealIngredients;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealPlan;

@Database(entities = { MealInfo.class, MealIngredients.class, MealPlan.class}, version = 1)
public abstract class MealLocalDataSaurce extends RoomDatabase {
    private static MealLocalDataSaurce instance = null;
   // public abstract DAO mealDao();
    public abstract MealInfoDAO mealInfoDao();
    public abstract MealPlanDAO mealPlanDao();
    public abstract MealIngredientsDAO mealIngredientsDao();
    public static synchronized MealLocalDataSaurce getInstance(android.content.Context context){
        if (instance == null){
            instance = androidx.room.Room.databaseBuilder(context.getApplicationContext(), MealLocalDataSaurce.class, "mealdb")
                    .build();
        }
        return instance;
    }
}
