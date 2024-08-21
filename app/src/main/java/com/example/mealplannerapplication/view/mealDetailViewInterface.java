package com.example.mealplannerapplication.view;

import com.example.mealplannerapplication.model.Meal;

import java.util.List;

public interface mealDetailViewInterface {
    void onSuccess(List<Meal> MealList);
    void onFailure(Throwable t);

}
