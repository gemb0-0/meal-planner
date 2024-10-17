package com.example.mealplannerapplication.view.activity2.Favourite;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealInfo;

import java.util.List;

public interface FavouriteInterface {
   // void showFavProductsOld(List<Meal> mealDetail);
    void showErrMsg();

    void showFavProducts(List<MealInfo> meals);
}
