package com.example.mealplannerapplication.presenter;

import com.example.mealplannerapplication.model.Pojos.SingleRegionMeals;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.model.SingleRegionCallBack;
import com.example.mealplannerapplication.view.SearchDetailsInterface;

import java.util.List;

public class searchDetailPresenter {
    Repository repo;
    SearchDetailsInterface view;

    public searchDetailPresenter(Repository repo, SearchDetailsInterface view) {
        this.repo = repo;
        this.view = view;
    }

    public void getItemDetail(String id) {

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
