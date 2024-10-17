package com.example.mealplannerapplication.model.LocalDataSource.db.Pojos;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity (tableName = "meal_info")
public class MealInfo {
    @PrimaryKey
    @NotNull
    private String idMeal;
    private String strMeal;
    private String strCategory;
    private String strArea;
    private String strInstructions;
    private String strMealThumb;
    private String strYoutube;
    private Boolean inPlan= false;
    private Boolean inFav= false;

    public MealInfo(String idMeal, String strMeal, String strCategory, String strArea, String strInstructions, String strMealThumb, String strYoutube, Boolean inPlan, Boolean inFav) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strCategory = strCategory;
        this.strArea = strArea;
        this.strInstructions = strInstructions;
        this.strMealThumb = strMealThumb;
        this.strYoutube = strYoutube;
        this.inPlan = inPlan;
        this.inFav = inFav;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public void setStrYoutube(String strYoutube) {
        this.strYoutube = strYoutube;
    }

    public Boolean getInPlan() {
        return inPlan;
    }

    public void setInPlan(Boolean inPlan) {
        this.inPlan = inPlan;
    }

    public Boolean getInFav() {
        return inFav;
    }

    public void setInFav(Boolean inFav) {
        this.inFav = inFav;
    }
}
