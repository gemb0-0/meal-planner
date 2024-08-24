package com.example.mealplannerapplication.presenter;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.MealCallback;
import com.example.mealplannerapplication.view.activity2.MealDetailView.mealDetailViewInterface;

import java.util.List;

public class mealDetailPresenter {
    mealDetailViewInterface view;
    Repository repo;
    public mealDetailPresenter(mealDetailViewInterface m, Repository repo) {
        this.view = m;
        this.repo = repo;

    }

    public void getMealDetail(String mealId) {
        repo.fetchMealDetail(mealId, new MealCallback() {
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
        repo.saveToFav(mealId);
    }

    public void getFromDb(String mealId) {
        repo.getFromDb(mealId, new MealCallback() {
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

    public void saveMealToPlan(String mealId, String checkedChipDay, String checkedChipMeal) {
        repo.saveToPlan(mealId, checkedChipDay, checkedChipMeal);
    }
}
