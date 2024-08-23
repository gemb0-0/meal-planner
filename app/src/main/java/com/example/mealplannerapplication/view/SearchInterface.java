package com.example.mealplannerapplication.view;

import com.example.mealplannerapplication.model.Pojos.Category;
import com.example.mealplannerapplication.model.Pojos.Regions;

import java.util.List;

public interface SearchInterface {


    void showData(List<Category> categories);

    void onError(String message);

    void showRegionData(List<Regions> regions);
}
