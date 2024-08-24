package com.example.mealplannerapplication.view.activity2.Favourite;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;

import java.util.List;

public interface FavouriteInterface {
    void showFavProducts(List<Meal> mealDetail);
    void showErrMsg();
}
