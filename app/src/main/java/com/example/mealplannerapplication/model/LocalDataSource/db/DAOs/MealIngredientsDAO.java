package com.example.mealplannerapplication.model.LocalDataSource.db.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealIngredients;

import java.util.List;

@Dao

public interface MealIngredientsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIngredient(MealIngredients ingredients);

    @Query("DELETE FROM meal_ingredients where idMeal = :mealId")
    void delete(String mealId);

    @Query("SELECT * FROM meal_ingredients where idMeal = :mealId")
    MealIngredients getIngredientsById(String mealId);

    @Query("SELECT * FROM meal_ingredients")
    List<MealIngredients> getAllIngredients();


    @Query("SELECT * FROM meal_ingredients where idMeal = :mealId")
    MealIngredients getingbtid(String mealId);

}
