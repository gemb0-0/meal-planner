package com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;

import java.util.Map;

public interface TodaysPlanCallback {
    void onSuccess(Map<String, Meal> mealoftheday);
    void onFailure( Throwable t);
}
