package com.example.mealplannerapplication.model.CallbackInterfaces;

import com.example.mealplannerapplication.model.Pojos.SingleRegionMeals;

import java.util.List;

public interface SingleRegionCallBack {
    void onSuccess(List<SingleRegionMeals> meals);
    void onFailure(Throwable t);
}
