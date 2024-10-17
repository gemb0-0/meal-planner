package com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;

import java.util.List;
import java.util.Map;

public interface TodaysPlanCallback {
    void onSuccess(List<Meal> todaysMeals);
    void onFailure( Throwable t);
}
