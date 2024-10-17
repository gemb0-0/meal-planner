package com.example.mealplannerapplication.presenter;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.SingleRegionMeals;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.SingleRegionCallBack;
import com.example.mealplannerapplication.view.activity2.SearchDetails.SearchDetailsInterface;

import java.util.List;

public class SearchDetailsPresenter {
    Repository repo;
    SearchDetailsInterface view;

    public SearchDetailsPresenter(Repository repo, SearchDetailsInterface view) {
        this.repo = repo;
        this.view = view;
    }

    public void getMealsByRegion(String regionName) {
        repo.getMealsByRegion(regionName, new SingleRegionCallBack() {
            @Override
            public void onSuccess(List<SingleRegionMeals> meals) {
                view.showData(meals);
            }

            @Override
            public void onFailure(Throwable t) {
                view.onError(t.getMessage());
            }
        });

    }

    public void getMealsByIngredient(String id) {
        repo.getMealsByIngredient(id, new SingleRegionCallBack() {
            @Override
            public void onSuccess(List<SingleRegionMeals> meals) {
                view.showData(meals);
            }

            @Override
            public void onFailure(Throwable t) {
                view.onError(t.getMessage());
            }
        });
    }

    public void getMealsByCategory(String id) {
        repo.getMealsByCategory(id, new SingleRegionCallBack() {
            @Override
            public void onSuccess(List<SingleRegionMeals> meals) {
                view.showData(meals);
            }

            @Override
            public void onFailure(Throwable t) {
                view.onError(t.getMessage());
            }
        });
    }
}
