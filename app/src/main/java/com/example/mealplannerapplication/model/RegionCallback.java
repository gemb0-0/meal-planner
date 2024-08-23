package com.example.mealplannerapplication.model;

import com.example.mealplannerapplication.model.Pojos.Regions;

import java.util.List;

public interface RegionCallback {
    void onSuccess(List<Regions> regions);
    void onFailure(Throwable t);
}
