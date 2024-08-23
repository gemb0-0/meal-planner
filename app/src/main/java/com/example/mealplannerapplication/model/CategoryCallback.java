package com.example.mealplannerapplication.model;

import com.example.mealplannerapplication.model.Pojos.Category;

import java.util.List;

public interface CategoryCallback {
    void onSuccess(List<Category> categories);
    void onFailure(Throwable t);
}
