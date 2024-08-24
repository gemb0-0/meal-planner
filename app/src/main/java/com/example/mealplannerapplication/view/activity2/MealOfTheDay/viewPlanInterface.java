package com.example.mealplannerapplication.view.activity2.MealOfTheDay;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;

import java.util.Map;

public interface viewPlanInterface {
    void getMealsForTheDay(Map<String,Meal> mealList);
    void errorGettingSchedule(Throwable t);
}
