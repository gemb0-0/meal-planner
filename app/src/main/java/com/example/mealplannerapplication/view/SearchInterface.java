package com.example.mealplannerapplication.view;

import com.example.mealplannerapplication.model.Pojos.Category;
import com.example.mealplannerapplication.model.Pojos.Ingredients;
import com.example.mealplannerapplication.model.Pojos.Meal;
import com.example.mealplannerapplication.model.Pojos.Regions;

import java.util.List;

public interface SearchInterface {


    void showCategoryData(List<Category> categories);

    void onError(String message);

    void showRegionData(List<Regions> regions);

    void showIngredientsData(List<Ingredients> ingredients);

    void SearchData(List<List<Object>> motherList);

    void showMealData(List<Meal> mealDetail);
}
