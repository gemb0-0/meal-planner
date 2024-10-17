package com.example.mealplannerapplication.presenter;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealInfo;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.CategoryCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.IngredientsCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.MealCallback;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Ingredients;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Regions;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.MealInfoCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.RegionCallback;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Category;
import com.example.mealplannerapplication.view.activity2.Search.SearchInterface;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter {
    SearchInterface view;
    Repository repo;

    public SearchPresenter(SearchInterface view, Repository repo) {
        this.view = view;
        this.repo = repo;
    }

    public void getDataForSearch() {
        List<List<Object>> motherList = new ArrayList<>();

        repo.getCategories(new CategoryCallback() {
            @Override
            public void onSuccess(List<Category> categories) {
                motherList.add(new ArrayList<Object>(categories));
                view.showCategoryData(categories);
            }

            @Override
            public void onFailure(Throwable t) {
                view.onError(t.getMessage());
            }
        });
        repo.getRegions(new RegionCallback() {
            @Override
            public void onSuccess(List<Regions> regions) {
                motherList.add(new ArrayList<Object>(regions));
                view.showRegionData(regions);
            }

            @Override
            public void onFailure(Throwable t) {
                view.onError(t.getMessage());
            }
        });
        repo.getIngredients(new IngredientsCallback() {
            @Override
            public void onSuccess(List<Ingredients> ingredients) {
                motherList.add(new ArrayList<Object>(ingredients));
                view.showIngredientsData(ingredients);
            }

            @Override
            public void onFailure(Throwable t) {
                view.onError(t.getMessage());

            }
        });
        view.SearchData(motherList);

    }


    public void searchMealName(String newText) {
        repo.SearchMealByName(newText, new MealInfoCallback() {
                    @Override
                    public void onSuccess(List<MealInfo> mealInfo) {
                        view.showMealData(mealInfo);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        view.onError(t.getMessage());
                    }
                });


//                new MealCallback() {
//            @Override
//            public void onSuccess(List<Meal> mealDetail) {
//                view.showMealData(mealDetail);
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                view.onError(t.getMessage());
//            }
//        });

    }
}
