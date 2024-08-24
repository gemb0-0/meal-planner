package com.example.mealplannerapplication.model.CallbackInterfaces;

import com.example.mealplannerapplication.model.Pojos.Meal;

import java.util.List;

public interface MealCallback {
    void onSuccess(List<Meal> mealDetail);
    void onFailure( Throwable t);
}
