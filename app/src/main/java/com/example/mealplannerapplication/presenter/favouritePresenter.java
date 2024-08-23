package com.example.mealplannerapplication.presenter;

import androidx.lifecycle.Observer;

import com.example.mealplannerapplication.model.Pojos.Meal;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.view.FavouriteInterface;
import com.example.mealplannerapplication.view.favourite;

import java.util.List;

public class favouritePresenter {
    Repository repo;
    FavouriteInterface view;
    public favouritePresenter(Repository repo, FavouriteInterface view) {
        this.repo = repo;
        this.view = view;
    }

    public void retriveFavourite() {
         repo.fetchFavourite().observe((favourite)view, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                if (!meals.isEmpty() &&meals!=null) {
                    view.showFavProducts(meals);
                } else {
                    view.showErrMsg();
                }
            }
    });
    }

    public void deleteFav(Meal meal) {
        repo.deleteFav(meal);
    }
}
