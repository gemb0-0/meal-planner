package com.example.mealplannerapplication.view;

import com.example.mealplannerapplication.model.Pojos.Meal;

import java.util.List;

public interface FavouriteInterface {
    void showFavProducts(List<Meal> mealDetail);
    void showErrMsg();
}
