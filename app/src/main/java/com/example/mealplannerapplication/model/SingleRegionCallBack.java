package com.example.mealplannerapplication.model;

import com.example.mealplannerapplication.model.Pojos.SingleRegionMeals;

import java.util.List;

public interface SingleRegionCallBack {
    void onSuccess(List<SingleRegionMeals> meals);
    void onFailure(Throwable t);
}
