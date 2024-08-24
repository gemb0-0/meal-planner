package com.example.mealplannerapplication.model.RemoteDataSource.response;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Regions;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegionResponseApi {
    @SerializedName("meals")
    private List<Regions> meals;

    public List<Regions> getRegions() {return meals;}

}
