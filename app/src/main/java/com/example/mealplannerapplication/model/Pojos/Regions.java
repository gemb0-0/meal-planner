package com.example.mealplannerapplication.model.Pojos;

import com.google.gson.annotations.SerializedName;

public class Regions {

    @SerializedName("strArea")
    private String strArea;

    public Regions(String strArea) {
        this.strArea = strArea;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }
}
