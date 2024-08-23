package com.example.mealplannerapplication.model;

import com.example.mealplannerapplication.model.Pojos.Meal;

import java.util.List;

public interface mealofthedayCallback {
    void onSuccess(List <Meal> mealoftheday);
    void onFailure( Throwable t);
}
