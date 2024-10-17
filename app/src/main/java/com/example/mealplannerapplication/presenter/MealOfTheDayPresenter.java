package com.example.mealplannerapplication.presenter;

import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.MealCallback;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.TodaysPlanCallback;
import com.example.mealplannerapplication.view.activity2.MealOfTheDay.IMealOfTheDay;
import com.example.mealplannerapplication.view.activity2.MealOfTheDay.ViewPlanInterface;

import java.util.List;

public class MealOfTheDayPresenter {
    private IMealOfTheDay view;
    private ViewPlanInterface scheduleView;
    private Repository repo;

    public MealOfTheDayPresenter(IMealOfTheDay view, ViewPlanInterface scheduleView, Repository repo) {
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

    public void getSchedule(String chipText) {
        repo.getTodayMeals(chipText, new TodaysPlanCallback() {

            @Override
            public void onSuccess(List<Meal> todaysMeals) {
                scheduleView.getMealsForTheDay(todaysMeals);
            }

            @Override
            public void onFailure(Throwable t) {
                scheduleView.errorGettingSchedule(t);
            }
        });
    }

    public void deleteMealFromPlan(String idMeal) {
        repo.deleteMealFromPlan(idMeal);
    }
}
