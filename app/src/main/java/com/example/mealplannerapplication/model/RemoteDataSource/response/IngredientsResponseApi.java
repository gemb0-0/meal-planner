package com.example.mealplannerapplication.model.RemoteDataSource.response;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Ingredients;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientsResponseApi {
    @SerializedName("meals")
    List<Ingredients> ingredients;
    public List<Ingredients> getIngredients() {return ingredients;}

}
