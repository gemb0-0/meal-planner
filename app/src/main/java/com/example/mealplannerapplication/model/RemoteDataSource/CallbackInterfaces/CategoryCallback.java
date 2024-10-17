package com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Category;

import java.util.List;

public interface CategoryCallback {
    void onSuccess(List<Category> categories);
    void onFailure(Throwable t);
}
