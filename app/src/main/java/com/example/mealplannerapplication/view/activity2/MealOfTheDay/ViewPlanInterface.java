package com.example.mealplannerapplication.view.activity2.MealOfTheDay;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;

import java.util.List;
import java.util.Map;

public interface ViewPlanInterface {
    void getMealsForTheDay(List<Meal> todaysMeals);
    void errorGettingSchedule(Throwable t);
}
