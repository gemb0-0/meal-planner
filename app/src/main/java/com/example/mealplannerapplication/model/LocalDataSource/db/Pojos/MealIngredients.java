package com.example.mealplannerapplication.model.LocalDataSource.db.Pojos;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "meal_ingredients", foreignKeys = @ForeignKey(entity = MealInfo.class, parentColumns = "idMeal", childColumns = "idMeal", onDelete = ForeignKey.CASCADE))
public class MealIngredients {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String idMeal;
    private String strMeal;
    private String strYoutube;
    private String strInstructions;
    private String ingredients;
    private String strMeasure;


    public MealIngredients(String idMeal, String strMeal, String strYoutube, String strInstructions, String ingredients, String strMeasure) {

        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strYoutube = strYoutube;
        this.strInstructions = strInstructions;
        this.ingredients = ingredients;
        this.strMeasure = strMeasure;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStrYoutube() {
        return strYoutube;
    }

    public void setStrYoutube(String strYoutube) {
        this.strYoutube = strYoutube;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getStrMeasure() {
        return strMeasure;
    }

    public void setStrMeasure(String strMeasure) {
        this.strMeasure = strMeasure;
    }
}
