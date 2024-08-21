package com.example.mealplannerapplication.presenter;

import android.content.Context;

import com.example.mealplannerapplication.model.Meal;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.model.MealDetailCallback;
import com.example.mealplannerapplication.view.mealDetailViewInterface;

import java.util.List;

public class mealDetailPresenter {
    mealDetailViewInterface view;
    Repository repo;
    public mealDetailPresenter(mealDetailViewInterface m, Repository repo) {
        this.view = m;
        this.repo = repo;

    }

    public void getMealDetail(String mealId) {
        repo.fetchMealDetail(mealId, new MealDetailCallback() {
            @Override
            public void onSuccess(List<Meal> mealDetail) {
                view.onSuccess(mealDetail);
            }

            @Override
            public void onFailure(Throwable t) {
                view.onFailure(t);
            }
        });
    }

    public void saveMeal(String mealId) {
        repo.saveMeal(mealId);
    }

    public void getFromDb(String mealId) {
        repo.getFromDb(mealId, new MealDetailCallback() {
            @Override
            public void onSuccess(List<Meal> mealDetail) {
                view.onSuccess(mealDetail);
            }

            @Override
            public void onFailure(Throwable t) {
                view.onFailure(t);
            }
        });
    }
}
