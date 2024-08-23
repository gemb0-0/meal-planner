package com.example.mealplannerapplication.view;

import com.example.mealplannerapplication.model.Pojos.Meal;

import java.util.Map;

public interface viewPlanInterface {
    void getMealsForTheDay(Map<String,Meal> mealList);
    void errorGettingSchedule(Throwable t);
}
