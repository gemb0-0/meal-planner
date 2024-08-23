package com.example.mealplannerapplication.presenter;

import com.example.mealplannerapplication.model.CategoryCallback;
import com.example.mealplannerapplication.model.IngredientsCallback;
import com.example.mealplannerapplication.model.MealDetailCallback;
import com.example.mealplannerapplication.model.Pojos.Ingredients;
import com.example.mealplannerapplication.model.Pojos.Regions;
import com.example.mealplannerapplication.model.Pojos.SingleRegionMeals;
import com.example.mealplannerapplication.model.RegionCallback;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.model.Pojos.Category;
import com.example.mealplannerapplication.model.SingleRegionCallBack;
import com.example.mealplannerapplication.view.SearchInterface;

import java.util.List;

public class SearchPresenter {
    SearchInterface view;
    Repository repo;

    public SearchPresenter(SearchInterface view, Repository repo) {
        this.view = view;
        this.repo = repo;
    }

    public void getDataForSearch() {
        repo.getCategories(new CategoryCallback() {
            @Override
            public void onSuccess(List<Category> categories) {

                view.showData(categories);
            }

            @Override
            public void onFailure(Throwable t) {
                view.onError(t.getMessage());
            }
        });
        repo.getRegions(new RegionCallback() {
            @Override
            public void onSuccess(List<Regions> regions) {
                view.showRegionData(regions);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        repo.getIngredients(new IngredientsCallback() {
            @Override
            public void onSuccess(List<Ingredients> ingredients) {

                view.showIngredients(ingredients);
            }

            @Override
            public void onFailure(Throwable t) {
                view.onError(t.getMessage());

            }
        });
    }




}
