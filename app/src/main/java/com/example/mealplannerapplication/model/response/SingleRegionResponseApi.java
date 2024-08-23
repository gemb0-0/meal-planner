package com.example.mealplannerapplication.model.response;

import com.example.mealplannerapplication.model.Pojos.Meal;
import com.example.mealplannerapplication.model.Pojos.Regions;
import com.google.gson.annotations.SerializedName;
import com.example.mealplannerapplication.model.Pojos.SingleRegionMeals;

import java.util.List;

import retrofit2.http.Query;

public class SingleRegionResponseApi {
    @SerializedName("meals")
    private List<SingleRegionMeals> meals;

    public List<SingleRegionMeals> getCountryMeals(@Query("i") String country) {return meals;}

}

