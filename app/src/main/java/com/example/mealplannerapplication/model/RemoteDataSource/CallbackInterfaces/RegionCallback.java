package com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Regions;

import java.util.List;

public interface RegionCallback {
    void onSuccess(List<Regions> regions);
    void onFailure(Throwable t);
}
