package com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;

import java.util.List;

public interface MealCallback {
    void onSuccess(List<Meal> mealDetail);
    void onFailure( Throwable t);
}
