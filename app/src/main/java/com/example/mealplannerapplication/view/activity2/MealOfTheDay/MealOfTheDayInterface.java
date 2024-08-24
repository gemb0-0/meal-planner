package com.example.mealplannerapplication.view.activity2.MealOfTheDay;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;

import java.util.List;

public interface MealOfTheDayInterface {
     void onSuccess(List <Meal> MealList) ;
    void onFailure( Throwable t);
}
