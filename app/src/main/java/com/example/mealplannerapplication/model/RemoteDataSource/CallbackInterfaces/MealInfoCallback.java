package com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealInfo;

import java.util.List;

public interface MealInfoCallback {
   // void onSuccess(List<MealInfo> mealInfo);
    void onSuccess(List<MealInfo> mealInfo);
    void onFailure(Throwable t);
}
