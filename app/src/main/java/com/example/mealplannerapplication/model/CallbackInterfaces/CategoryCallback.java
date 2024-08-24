package com.example.mealplannerapplication.model.CallbackInterfaces;

import com.example.mealplannerapplication.model.Pojos.Category;

import java.util.List;

public interface CategoryCallback {
    void onSuccess(List<Category> categories);
    void onFailure(Throwable t);
}
