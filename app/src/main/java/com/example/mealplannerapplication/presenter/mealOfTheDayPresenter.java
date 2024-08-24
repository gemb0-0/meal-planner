package com.example.mealplannerapplication.presenter;

import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.MealCallback;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.TodaysPlanCallback;
import com.example.mealplannerapplication.view.activity2.MealOfTheDay.MealOfTheDayInterface;
import com.example.mealplannerapplication.view.activity2.MealOfTheDay.viewPlanInterface;

import java.util.List;
import java.util.Map;

public class mealOfTheDayPresenter {
    private MealOfTheDayInterface view;
    private viewPlanInterface scheduleView;
    private Repository repo;

    public mealOfTheDayPresenter(MealOfTheDayInterface view, viewPlanInterface scheduleView, Repository repo) {
        this.view = view;
        this.repo = repo;
        this.scheduleView = scheduleView;
    }

    public void getMealOfTheDay() {
        repo.fetchMealoftheday(new MealCallback() {
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

    public void getMealsForTheDay(String chipText) {
        repo.getTodayMeals(chipText, new TodaysPlanCallback() {
            @Override
            public void onSuccess(Map<String,Meal> ToadysMeals) {
                scheduleView.getMealsForTheDay(ToadysMeals);
            }

            @Override
            public void onFailure(Throwable t) {
                scheduleView.errorGettingSchedule(t);
            }
        });
    }

 }
