package com.example.mealplannerapplication.view.activity2.Search;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Category;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Ingredients;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealInfo;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Regions;

import java.util.List;

public interface SearchInterface {

    void showCategoryData(List<Category> categories);

    void onError(String message);

    void showRegionData(List<Regions> regions);

    void showIngredientsData(List<Ingredients> ingredients);

    void SearchData(List<List<Object>> motherList);

    void showMealData(List<MealInfo> mealDetail);

}
