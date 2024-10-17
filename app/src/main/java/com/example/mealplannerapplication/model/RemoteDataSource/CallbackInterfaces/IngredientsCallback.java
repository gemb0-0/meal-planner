package com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Ingredients;

import java.util.List;

public interface IngredientsCallback {
    void onSuccess(List<Ingredients> ingredients);
    void onFailure(Throwable t);
}
