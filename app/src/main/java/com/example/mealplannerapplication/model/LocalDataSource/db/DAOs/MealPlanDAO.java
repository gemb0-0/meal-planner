package com.example.mealplannerapplication.model.LocalDataSource.db.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealPlan;

import java.util.List;

@Dao
public interface MealPlanDAO {
    @Insert
    void insertMealPlan(MealPlan mealPlan);

    @Query("SELECT * FROM meal_plan where day = :day")
    LiveData<List<MealPlan>> getMealPlanByDay(String day);

    @Query("SELECT * FROM meal_plan")
    List<MealPlan> getAllPlans();

    @Query("SELECT * FROM meal_plan where idMeal = :id")
    MealPlan getMealPlanById(String id);

    @Query("DELETE FROM meal_plan where idMeal = :idMeal")
    void deleteMealPlan(String idMeal);
}

