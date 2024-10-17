package com.example.mealplannerapplication.view.activity2.Favourite;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealInfo;

public interface DeleteFavCallback {

    void deleteFav(MealInfo mealInfo);
}
