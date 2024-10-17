package com.example.mealplannerapplication.view.activity2.MealDetailView;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;

import java.util.List;

public interface MealDetailViewInterface {
    void onSuccess(List<Meal> MealList);
    void onFailure(Throwable t);

}
