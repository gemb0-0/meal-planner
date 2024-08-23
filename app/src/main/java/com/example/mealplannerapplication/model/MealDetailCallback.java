package com.example.mealplannerapplication.model;

import com.example.mealplannerapplication.model.Pojos.Meal;

import java.util.List;

public interface MealDetailCallback {
    void onSuccess(List<Meal> mealDetail);
    void onFailure( Throwable t);
}
