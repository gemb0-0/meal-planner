package com.example.mealplannerapplication.model.LocalDataSource.db.Pojos;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "meal_plan",foreignKeys = @ForeignKey(entity = MealInfo.class, parentColumns = "idMeal", childColumns = "idMeal",onDelete = ForeignKey.CASCADE))
public class MealPlan {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String idMeal;
    private String day;
    private String meal;

    public MealPlan( String idMeal, String day, String meal) {

        this.idMeal = idMeal;
        this.day = day;
        this.meal = meal;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }
}
