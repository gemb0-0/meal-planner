package com.example.mealplannerapplication.model;

import java.util.Map;

public interface TodaysPlanCallback {
    void onSuccess(Map<String,Meal> mealoftheday);
    void onFailure( Throwable t);
}
