package com.example.mealplannerapplication.model;

import com.example.mealplannerapplication.model.Pojos.Ingredients;

import java.util.List;

public interface IngredientsCallback {
    void onSuccess(List<Ingredients> ingredients);
    void onFailure(Throwable t);
}
