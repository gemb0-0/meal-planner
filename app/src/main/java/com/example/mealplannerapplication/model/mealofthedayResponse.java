package com.example.mealplannerapplication.model;

import java.util.List;

import retrofit2.http.Query;

public class mealofthedayResponse {
 List<Meal> meals;
    Meal meal;
    public List<Meal> getMealDetail() {return meals;}
    public List<Meal> getDetail(@Query("i") String mealId) {return meals;}





}
