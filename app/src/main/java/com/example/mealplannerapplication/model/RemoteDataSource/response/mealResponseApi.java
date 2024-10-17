package com.example.mealplannerapplication.model.RemoteDataSource.response;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Category;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;

import java.util.List;

import retrofit2.http.Query;

public class mealResponseApi {

    List<Meal> meals;

   List<Category> categories;

    public List<Meal> getMealDetail() {return meals;}
    public List<Meal> getDetail(@Query("i") String mealId) {return meals;}
    public List<Category> getCategories() {return categories;}
    public List<Meal> getMealByName(@Query("s") String name) {return meals;}





}
