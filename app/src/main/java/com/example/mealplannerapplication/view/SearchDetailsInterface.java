package com.example.mealplannerapplication.view;

import com.example.mealplannerapplication.model.Pojos.SingleRegionMeals;

import java.util.List;

public interface SearchDetailsInterface {
    void showData(List<SingleRegionMeals> meals);
    void onError(String message);
}
