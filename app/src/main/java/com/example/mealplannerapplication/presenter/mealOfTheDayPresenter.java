package com.example.mealplannerapplication.presenter;

import com.example.mealplannerapplication.model.Meal;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.model.mealofthedayCallback;
import com.example.mealplannerapplication.view.MealOfTheDayInterface;

import java.util.List;

public class mealOfTheDayPresenter {
    MealOfTheDayInterface view;
    Repository repo;

    public mealOfTheDayPresenter(MealOfTheDayInterface view, Repository repo) {
        this.view = view;
        this.repo = repo;
    }

    public void getMealOfTheDay() {
        repo.fetchMealoftheday(new mealofthedayCallback() {
            @Override
            public void onSuccess(List<Meal> mealoftheday) {
               view.onSuccess(mealoftheday);
            }

            @Override
            public void onFailure(Throwable t) {
                view.onFailure(t);
            }
        });
    }
}
