package com.example.mealplannerapplication.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApi {
    @GET("random.php")
    Call <mealofthedayResponse> getMealoftheday();

    @GET("lookup.php")
    Call<mealofthedayResponse> getDetail(@Query("i") String mealId);

}
