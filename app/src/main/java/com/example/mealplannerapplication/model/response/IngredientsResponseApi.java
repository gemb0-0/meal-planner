package com.example.mealplannerapplication.model.response;

import com.example.mealplannerapplication.model.Pojos.Ingredients;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientsResponseApi {
    @SerializedName("meals")
    List<Ingredients> ingredients;
    public List<Ingredients> getIngredients() {return ingredients;}

}
