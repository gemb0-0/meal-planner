package com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.SingleRegionMeals;

import java.util.List;

public interface SingleRegionCallBack {
    void onSuccess(List<SingleRegionMeals> meals);
    void onFailure(Throwable t);
}
