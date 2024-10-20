package com.example.mealplannerapplication.model.RemoteDataSource;

import com.example.mealplannerapplication.model.RemoteDataSource.response.IngredientsResponseApi;
import com.example.mealplannerapplication.model.RemoteDataSource.response.RegionResponseApi;
import com.example.mealplannerapplication.model.RemoteDataSource.response.SingleRegionResponseApi;
import com.example.mealplannerapplication.model.RemoteDataSource.response.mealResponseApi;

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

    @GET("filter.php")
    Call<SingleRegionResponseApi> getIngrediantMeal(@Query("c") String country);

    @GET("filter.php")
    Call<SingleRegionResponseApi> getCategoryMeal(@Query("i") String country);

     @GET("search.php")
    Call<mealResponseApi> getMealByName(@Query("s") String name);

    @GET("categories.php")
    Call<mealResponseApi> getCategories();

    @POST("list.php?i=list")
    Call<IngredientsResponseApi>getIngredients();

    @GET("list.php?a=list")
    Call<RegionResponseApi> getRegions();


}
