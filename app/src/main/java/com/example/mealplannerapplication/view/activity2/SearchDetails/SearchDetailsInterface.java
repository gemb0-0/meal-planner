package com.example.mealplannerapplication.view.activity2.SearchDetails;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.SingleRegionMeals;

import java.util.List;

public interface SearchDetailsInterface {
    void showData(List<SingleRegionMeals> meals);
    void onError(String message);
}
