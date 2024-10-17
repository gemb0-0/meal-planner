package com.example.mealplannerapplication.model.LocalDataSource.db.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealInfo;

import java.util.List;
@Dao
public interface MealInfoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(MealInfo info );

    @Query("SELECT * FROM meal_info where idMeal = :mealId")
    MealInfo getMealById(String mealId);

    @Query("SELECT * FROM meal_info where inFav == 1 ")
    LiveData<List<MealInfo>> getAllFavourites();

    @Query("SELECT * FROM meal_info where inPlan == 1 ")
    List<MealInfo> getTodayMeals();

    @Query("SELECT * FROM meal_info ")
    List<MealInfo> getAllMeals();

    @Delete
    void delete(MealInfo meal);

    @Query("DELETE FROM meal_info")
    void deleteAll();

    @Query("DELETE FROM meal_info WHERE idMeal = :idMeal")
    void deleteMeal(String idMeal);

    @Update
    void update(MealInfo meal);
}
