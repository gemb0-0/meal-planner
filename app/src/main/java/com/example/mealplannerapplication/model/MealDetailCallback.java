package com.example.mealplannerapplication.model;

import java.util.List;

public interface MealDetailCallback {
    void onSuccess(List<Meal> mealDetail);
    void onFailure( Throwable t);
}
