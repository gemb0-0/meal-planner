package com.example.mealplannerapplication.presenter;

import androidx.lifecycle.Observer;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealInfo;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.view.activity2.Favourite.FavouriteInterface;
import com.example.mealplannerapplication.view.activity2.Favourite.Favourite;

import java.util.List;

public class FavouritePresenter {
    Repository repo;
    FavouriteInterface view;

    public FavouritePresenter(Repository repo, FavouriteInterface view) {
        this.repo = repo;
        this.view = view;
    }

    public void retrieveFavourites() {
        repo.fetchFavourites().observe((Favourite) view, new Observer<List<MealInfo>>() {
            @Override
            public void onChanged(List<MealInfo> meals) {
                if (!meals.isEmpty() && meals != null) {
                    view.showFavProducts(meals);
                } else {
                    view.showErrMsg();
                }
            }
        });

    }

    public void deleteFav(MealInfo meal) {
        repo.deleteFav(meal);
    }
}
