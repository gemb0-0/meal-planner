package com.example.mealplannerapplication.presenter;

import com.example.mealplannerapplication.model.Meal;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.model.MealDetailCallback;
import com.example.mealplannerapplication.view.mealDetailViewInterface;

import java.util.List;

public class mealDetailPresenter {
    mealDetailViewInterface view;
    Repository repo = Repository.getInstance();

    public mealDetailPresenter(mealDetailViewInterface m) {
        this.view = m;

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
}
