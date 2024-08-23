package com.example.mealplannerapplication.view;

import com.example.mealplannerapplication.model.Pojos.Meal;

import java.util.List;

public interface MealOfTheDayInterface {
     void onSuccess(List <Meal> MealList) ;
    void onFailure( Throwable t);
}
