package com.example.mealplannerapplication.model.CallbackInterfaces;

import com.example.mealplannerapplication.model.Pojos.Meal;

import java.util.Map;

public interface TodaysPlanCallback {
    void onSuccess(Map<String, Meal> mealoftheday);
    void onFailure( Throwable t);
}
