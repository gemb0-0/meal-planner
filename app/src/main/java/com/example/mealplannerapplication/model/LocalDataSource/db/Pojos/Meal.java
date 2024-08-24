package com.example.mealplannerapplication.model.LocalDataSource.db.Pojos;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
        import java.util.List;
        @Entity(tableName = "meal_table")
        public class Meal {
            @PrimaryKey
            @NotNull
            private String idMeal;

            public Meal() {
            }

            private String strMeal;
            private String strDrinkAlternate;
            private String strCategory;
            private String strArea;
            private String strInstructions;
            private String strMealThumb;
            private String strTags;
            private String strYoutube;
            private String strIngredient1;
            private String strIngredient2;
            private String strIngredient3;
            private String strIngredient4;
            private String strIngredient5;
            private String strIngredient6;
            private String strIngredient7;
            private String strIngredient8;
            private String strIngredient9;
            private String strIngredient10;
            private String strIngredient11;
            private String strIngredient12;
            private String strIngredient13;
            private String strIngredient14;
            private String strIngredient15;
            private String strIngredient16;
            private String strIngredient17;
            private String strIngredient18;
            private String strIngredient19;
            private String strIngredient20;
            private String strMeasure1;
            private String strMeasure2;
            private String strMeasure3;
            private String strMeasure4;
            private String strMeasure5;
            private String strMeasure6;
            private String strMeasure7;
            private String strMeasure8;
            private String strMeasure9;
            private String strMeasure10;
            private String strMeasure11;
            private String strMeasure12;
            private String strMeasure13;
            private String strMeasure14;
            private String strMeasure15;
            private String strMeasure16;
            private String strMeasure17;
            private String strMeasure18;
            private String strMeasure19;
            private String strMeasure20;
            private String strSource;
            private String strImageSource;
            private String strCreativeCommonsConfirmed;
            private String dateModified;
            private  Boolean inPlan;
            private String meal=null;
            private String weekDay=null;

            public String getMeal() {
                return meal;
            }

            public void setMeal(String meal) {
                this.meal = meal;
            }

            public Meal(@NotNull String idMeal, String strMeal, String strDrinkAlternate, String strCategory, String strArea, String strInstructions, String strMealThumb, String strTags, String strYoutube, String strIngredient1, String strIngredient2, String strIngredient3, String strIngredient4, String strIngredient5, String strIngredient6, String strIngredient7, String strIngredient8, String strIngredient9, String strIngredient10, String strIngredient11, String strIngredient12, String strIngredient13, String strIngredient14, String strIngredient15, String strIngredient16, String strIngredient17, String strIngredient18, String strIngredient19, String strIngredient20, String strMeasure1, String strMeasure2, String strMeasure3, String strMeasure4, String strMeasure5, String strMeasure6, String strMeasure7, String strMeasure8, String strMeasure9, String strMeasure10, String strMeasure11, String strMeasure12, String strMeasure13, String strMeasure14, String strMeasure15, String strMeasure16, String strMeasure17, String strMeasure18, String strMeasure19, String strMeasure20, String strSource, String strImageSource, String strCreativeCommonsConfirmed, String dateModified, Boolean inPlan, String meal, String weekDay) {
                this.idMeal = idMeal;
                this.strMeal = strMeal;
                this.strDrinkAlternate = strDrinkAlternate;
                this.strCategory = strCategory;
                this.strArea = strArea;
                this.strInstructions = strInstructions;
                this.strMealThumb = strMealThumb;
                this.strTags = strTags;
                this.strYoutube = strYoutube;
                this.strIngredient1 = strIngredient1;
                this.strIngredient2 = strIngredient2;
                this.strIngredient3 = strIngredient3;
                this.strIngredient4 = strIngredient4;
                this.strIngredient5 = strIngredient5;
                this.strIngredient6 = strIngredient6;
                this.strIngredient7 = strIngredient7;
                this.strIngredient8 = strIngredient8;
                this.strIngredient9 = strIngredient9;
                this.strIngredient10 = strIngredient10;
                this.strIngredient11 = strIngredient11;
                this.strIngredient12 = strIngredient12;
                this.strIngredient13 = strIngredient13;
                this.strIngredient14 = strIngredient14;
                this.strIngredient15 = strIngredient15;
                this.strIngredient16 = strIngredient16;
                this.strIngredient17 = strIngredient17;
                this.strIngredient18 = strIngredient18;
                this.strIngredient19 = strIngredient19;
                this.strIngredient20 = strIngredient20;
                this.strMeasure1 = strMeasure1;
                this.strMeasure2 = strMeasure2;
                this.strMeasure3 = strMeasure3;
                this.strMeasure4 = strMeasure4;
                this.strMeasure5 = strMeasure5;
                this.strMeasure6 = strMeasure6;
                this.strMeasure7 = strMeasure7;
                this.strMeasure8 = strMeasure8;
                this.strMeasure9 = strMeasure9;
                this.strMeasure10 = strMeasure10;
                this.strMeasure11 = strMeasure11;
                this.strMeasure12 = strMeasure12;
                this.strMeasure13 = strMeasure13;
                this.strMeasure14 = strMeasure14;
                this.strMeasure15 = strMeasure15;
                this.strMeasure16 = strMeasure16;
                this.strMeasure17 = strMeasure17;
                this.strMeasure18 = strMeasure18;
                this.strMeasure19 = strMeasure19;
                this.strMeasure20 = strMeasure20;
                this.strSource = strSource;
                this.strImageSource = strImageSource;
                this.strCreativeCommonsConfirmed = strCreativeCommonsConfirmed;
                this.dateModified = dateModified;
                this.inPlan = inPlan;
                this.meal = meal;
                this.weekDay = weekDay;
            }


            public String getWeekDay() {
                return weekDay;
            }

            public void setWeekDay(String weekDay) {
                this.weekDay = weekDay;
            }

            public List<String> getAllIngredients() {
                List<String> ingredients = new ArrayList<>();
                if (strIngredient1 != null && !strIngredient1.isEmpty()) ingredients.add(strIngredient1);
                if (strIngredient2 != null && !strIngredient2.isEmpty()) ingredients.add(strIngredient2);
                if (strIngredient3 != null && !strIngredient3.isEmpty()) ingredients.add(strIngredient3);
                if (strIngredient4 != null && !strIngredient4.isEmpty()) ingredients.add(strIngredient4);
                if (strIngredient5 != null && !strIngredient5.isEmpty()) ingredients.add(strIngredient5);
                if (strIngredient6 != null && !strIngredient6.isEmpty()) ingredients.add(strIngredient6);
                if (strIngredient7 != null && !strIngredient7.isEmpty()) ingredients.add(strIngredient7);
                if (strIngredient8 != null && !strIngredient8.isEmpty()) ingredients.add(strIngredient8);
                if (strIngredient9 != null && !strIngredient9.isEmpty()) ingredients.add(strIngredient9);
                if (strIngredient10 != null && !strIngredient10.isEmpty()) ingredients.add(strIngredient10);
                if (strIngredient11 != null && !strIngredient11.isEmpty()) ingredients.add(strIngredient11);
                if (strIngredient12 != null && !strIngredient12.isEmpty()) ingredients.add(strIngredient12);
                if (strIngredient13 != null && !strIngredient13.isEmpty()) ingredients.add(strIngredient13);
                if (strIngredient14 != null && !strIngredient14.isEmpty()) ingredients.add(strIngredient14);
                if (strIngredient15 != null && !strIngredient15.isEmpty()) ingredients.add(strIngredient15);
                if (strIngredient16 != null && !strIngredient16.isEmpty()) ingredients.add(strIngredient16);
                if (strIngredient17 != null && !strIngredient17.isEmpty()) ingredients.add(strIngredient17);
                if (strIngredient18 != null && !strIngredient18.isEmpty()) ingredients.add(strIngredient18);
                if (strIngredient19 != null && !strIngredient19.isEmpty()) ingredients.add(strIngredient19);
                if (strIngredient20 != null && !strIngredient20.isEmpty()) ingredients.add(strIngredient20);
                return ingredients;
            }

            public List<String> getAllMeasures() {
                List<String> measures = new ArrayList<>();
                if (strMeasure1 != null && !strMeasure1.isEmpty()) measures.add(strMeasure1);
                if (strMeasure2 != null && !strMeasure2.isEmpty()) measures.add(strMeasure2);
                if (strMeasure3 != null && !strMeasure3.isEmpty()) measures.add(strMeasure3);
                if (strMeasure4 != null && !strMeasure4.isEmpty()) measures.add(strMeasure4);
                if (strMeasure5 != null && !strMeasure5.isEmpty()) measures.add(strMeasure5);
                if (strMeasure6 != null && !strMeasure6.isEmpty()) measures.add(strMeasure6);
                if (strMeasure7 != null && !strMeasure7.isEmpty()) measures.add(strMeasure7);
                if (strMeasure8 != null && !strMeasure8.isEmpty()) measures.add(strMeasure8);
                if (strMeasure9 != null && !strMeasure9.isEmpty()) measures.add(strMeasure9);
                if (strMeasure10 != null && !strMeasure10.isEmpty()) measures.add(strMeasure10);
                if (strMeasure11 != null && !strMeasure11.isEmpty()) measures.add(strMeasure11);
                if (strMeasure12 != null && !strMeasure12.isEmpty()) measures.add(strMeasure12);
                if (strMeasure13 != null && !strMeasure13.isEmpty()) measures.add(strMeasure13);
                if (strMeasure14 != null && !strMeasure14.isEmpty()) measures.add(strMeasure14);
                if (strMeasure15 != null && !strMeasure15.isEmpty()) measures.add(strMeasure15);
                if (strMeasure16 != null && !strMeasure16.isEmpty()) measures.add(strMeasure16);
                if (strMeasure17 != null && !strMeasure17.isEmpty()) measures.add(strMeasure17);
                if (strMeasure18 != null && !strMeasure18.isEmpty()) measures.add(strMeasure18);
                if (strMeasure19 != null && !strMeasure19.isEmpty()) measures.add(strMeasure19);
                if (strMeasure20 != null && !strMeasure20.isEmpty()) measures.add(strMeasure20);
                return measures;
            }


            public Boolean getInPlan() {
                return inPlan;
            }

            public void setInPlan(Boolean fav) {
                inPlan = fav;
            }


            public String getStrIngredient1() {
                return strIngredient1;
            }

            public void setStrIngredient1(String strIngredient1) {
                this.strIngredient1 = strIngredient1;
            }

            public String getStrIngredient2() {
                return strIngredient2;
            }

            public void setStrIngredient2(String strIngredient2) {
                this.strIngredient2 = strIngredient2;
            }

            public String getStrIngredient3() {
                return strIngredient3;
            }

            public void setStrIngredient3(String strIngredient3) {
                this.strIngredient3 = strIngredient3;
            }

            public String getStrIngredient4() {
                return strIngredient4;
            }

            public void setStrIngredient4(String strIngredient4) {
                this.strIngredient4 = strIngredient4;
            }

            public String getStrIngredient5() {
                return strIngredient5;
            }

            public void setStrIngredient5(String strIngredient5) {
                this.strIngredient5 = strIngredient5;
            }

            public String getStrIngredient6() {
                return strIngredient6;
            }

            public void setStrIngredient6(String strIngredient6) {
                this.strIngredient6 = strIngredient6;
            }

            public String getStrIngredient7() {
                return strIngredient7;
            }

            public void setStrIngredient7(String strIngredient7) {
                this.strIngredient7 = strIngredient7;
            }

            public String getStrIngredient8() {
                return strIngredient8;
            }

            public void setStrIngredient8(String strIngredient8) {
                this.strIngredient8 = strIngredient8;
            }

            public String getStrIngredient9() {
                return strIngredient9;
            }

            public void setStrIngredient9(String strIngredient9) {
                this.strIngredient9 = strIngredient9;
            }

            public String getStrIngredient10() {
                return strIngredient10;
            }

            public void setStrIngredient10(String strIngredient10) {
                this.strIngredient10 = strIngredient10;
            }

            public String getStrIngredient11() {
                return strIngredient11;
            }

            public void setStrIngredient11(String strIngredient11) {
                this.strIngredient11 = strIngredient11;
            }

            public String getStrIngredient12() {
                return strIngredient12;
            }

            public void setStrIngredient12(String strIngredient12) {
                this.strIngredient12 = strIngredient12;
            }

            public String getStrIngredient13() {
                return strIngredient13;
            }

            public void setStrIngredient13(String strIngredient13) {
                this.strIngredient13 = strIngredient13;
            }

            public String getStrIngredient14() {
                return strIngredient14;
            }

            public void setStrIngredient14(String strIngredient14) {
                this.strIngredient14 = strIngredient14;
            }

            public String getStrIngredient15() {
                return strIngredient15;
            }

            public void setStrIngredient15(String strIngredient15) {
                this.strIngredient15 = strIngredient15;
            }

            public String getStrIngredient16() {
                return strIngredient16;
            }

            public void setStrIngredient16(String strIngredient16) {
                this.strIngredient16 = strIngredient16;
            }

            public String getStrIngredient17() {
                return strIngredient17;
            }

            public void setStrIngredient17(String strIngredient17) {
                this.strIngredient17 = strIngredient17;
            }

            public String getStrIngredient18() {
                return strIngredient18;
            }

            public void setStrIngredient18(String strIngredient18) {
                this.strIngredient18 = strIngredient18;
            }

            public String getStrIngredient19() {
                return strIngredient19;
            }

            public void setStrIngredient19(String strIngredient19) {
                this.strIngredient19 = strIngredient19;
            }

            public String getStrIngredient20() {
                return strIngredient20;
            }

            public void setStrIngredient20(String strIngredient20) {
                this.strIngredient20 = strIngredient20;
            }

            public String getStrMeasure1() {
                return strMeasure1;
            }

            public void setStrMeasure1(String strMeasure1) {
                this.strMeasure1 = strMeasure1;
            }

            public String getStrMeasure2() {
                return strMeasure2;
            }

            public void setStrMeasure2(String strMeasure2) {
                this.strMeasure2 = strMeasure2;
            }

            public String getStrMeasure3() {
                return strMeasure3;
            }

            public void setStrMeasure3(String strMeasure3) {
                this.strMeasure3 = strMeasure3;
            }

            public String getStrMeasure4() {
                return strMeasure4;
            }

            public void setStrMeasure4(String strMeasure4) {
                this.strMeasure4 = strMeasure4;
            }

            public String getStrMeasure5() {
                return strMeasure5;
            }

            public void setStrMeasure5(String strMeasure5) {
                this.strMeasure5 = strMeasure5;
            }

            public String getStrMeasure6() {
                return strMeasure6;
            }

            public void setStrMeasure6(String strMeasure6) {
                this.strMeasure6 = strMeasure6;
            }

            public String getStrMeasure7() {
                return strMeasure7;
            }

            public void setStrMeasure7(String strMeasure7) {
                this.strMeasure7 = strMeasure7;
            }

            public String getStrMeasure8() {
                return strMeasure8;
            }

            public void setStrMeasure8(String strMeasure8) {
                this.strMeasure8 = strMeasure8;
            }

            public String getStrMeasure9() {
                return strMeasure9;
            }

            public void setStrMeasure9(String strMeasure9) {
                this.strMeasure9 = strMeasure9;
            }

            public String getStrMeasure10() {
                return strMeasure10;
            }

            public void setStrMeasure10(String strMeasure10) {
                this.strMeasure10 = strMeasure10;
            }

            public String getStrMeasure11() {
                return strMeasure11;
            }

            public void setStrMeasure11(String strMeasure11) {
                this.strMeasure11 = strMeasure11;
            }

            public String getStrMeasure12() {
                return strMeasure12;
            }

            public void setStrMeasure12(String strMeasure12) {
                this.strMeasure12 = strMeasure12;
            }

            public String getStrMeasure13() {
                return strMeasure13;
            }

            public void setStrMeasure13(String strMeasure13) {
                this.strMeasure13 = strMeasure13;
            }

            public String getStrMeasure14() {
                return strMeasure14;
            }

            public void setStrMeasure14(String strMeasure14) {
                this.strMeasure14 = strMeasure14;
            }

            public String getStrMeasure15() {
                return strMeasure15;
            }

            public void setStrMeasure15(String strMeasure15) {
                this.strMeasure15 = strMeasure15;
            }

            public String getStrMeasure16() {
                return strMeasure16;
            }

            public void setStrMeasure16(String strMeasure16) {
                this.strMeasure16 = strMeasure16;
            }

            public String getStrMeasure17() {
                return strMeasure17;
            }

            public void setStrMeasure17(String strMeasure17) {
                this.strMeasure17 = strMeasure17;
            }

            public String getStrMeasure18() {
                return strMeasure18;
            }

            public void setStrMeasure18(String strMeasure18) {
                this.strMeasure18 = strMeasure18;
            }

            public String getStrMeasure19() {
                return strMeasure19;
            }

            public void setStrMeasure19(String strMeasure19) {
                this.strMeasure19 = strMeasure19;
            }

            public String getStrMeasure20() {
                return strMeasure20;
            }

            public void setStrMeasure20(String strMeasure20) {
                this.strMeasure20 = strMeasure20;
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

            public String getStrDrinkAlternate() {
                return strDrinkAlternate;
            }

            public void setStrDrinkAlternate(String strDrinkAlternate) {
                this.strDrinkAlternate = strDrinkAlternate;
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

            public String getStrTags() {
                return strTags;
            }

            public void setStrTags(String strTags) {
                this.strTags = strTags;
            }

            public String getStrYoutube() {
                return strYoutube;
            }

            public void setStrYoutube(String strYoutube) {
                this.strYoutube = strYoutube;
            }
            
            public String getStrSource() {
                return strSource;
            }

            public void setStrSource(String strSource) {
                this.strSource = strSource;
            }

            public String getStrImageSource() {
                return strImageSource;
            }

            public void setStrImageSource(String strImageSource) {
                this.strImageSource = strImageSource;
            }

            public String getStrCreativeCommonsConfirmed() {
                return strCreativeCommonsConfirmed;
            }

            public void setStrCreativeCommonsConfirmed(String strCreativeCommonsConfirmed) {
                this.strCreativeCommonsConfirmed = strCreativeCommonsConfirmed;
            }

            public String getDateModified() {
                return dateModified;
            }

            public void setDateModified(String dateModified) {
                this.dateModified = dateModified;
            }

        }
