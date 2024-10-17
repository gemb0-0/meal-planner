package com.example.mealplannerapplication.presenter;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealInfo;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.MealInfoCallback;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.MealCallback;
import com.example.mealplannerapplication.view.activity2.MealDetailView.MealDetailViewInterface;

import java.util.List;

public class MealDetailPresenter {
    MealDetailViewInterface view;
    Repository repo;
    public MealDetailPresenter(MealDetailViewInterface m, Repository repo) {
        this.view = m;
        this.repo = repo;

    }
    //online
    public void getMealDetail(String mealId) {
        repo.getMealDetail(mealId, new MealCallback() {
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

    public void saveToFavourites(String mealId) {
        repo.saveFavToDb(mealId);
    }

    public void getFromDb(String mealId) {
        repo.getMealDetailDb(mealId, new MealCallback() {
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
