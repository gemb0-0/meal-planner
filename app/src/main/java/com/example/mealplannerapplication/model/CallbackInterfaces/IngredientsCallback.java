package com.example.mealplannerapplication.model.CallbackInterfaces;

import com.example.mealplannerapplication.model.Pojos.Ingredients;

import java.util.List;

public interface IngredientsCallback {
    void onSuccess(List<Ingredients> ingredients);
    void onFailure(Throwable t);
}
