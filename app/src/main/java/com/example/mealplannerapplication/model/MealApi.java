package com.example.mealplannerapplication.model;

import com.example.mealplannerapplication.model.response.IngredientsResponseApi;
import com.example.mealplannerapplication.model.response.RegionResponseApi;
import com.example.mealplannerapplication.model.response.SingleRegionResponseApi;
import com.example.mealplannerapplication.model.response.mealResponseApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MealApi {
    @GET("random.php")
    Call <mealResponseApi> getMealoftheday();

    @GET("lookup.php")
    Call<mealResponseApi> getDetail(@Query("i") String mealId);

    @GET("filter.php")
    Call<SingleRegionResponseApi> getCountryMeals(@Query("a") String country);

    @GET("categories.php")
    Call<mealResponseApi> getCategories();

    @POST("list.php?i=list")
    Call<IngredientsResponseApi>getIngredients();

    @GET("list.php?a=list")
    Call<RegionResponseApi> getRegions();


}
