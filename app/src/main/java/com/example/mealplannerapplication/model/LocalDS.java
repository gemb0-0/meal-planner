package com.example.mealplannerapplication.model;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mealplannerapplication.model.LocalDataSource.db.DAOs.MealInfoDAO;
import com.example.mealplannerapplication.model.LocalDataSource.db.DAOs.MealIngredientsDAO;
import com.example.mealplannerapplication.model.LocalDataSource.db.MealLocalDataSaurce;
import com.example.mealplannerapplication.model.LocalDataSource.db.DAOs.MealPlanDAO;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealInfo;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealIngredients;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealPlan;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.FirebaseCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.MealCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.TodaysPlanCallback;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LocalDS {
    MealInfoDAO mealInfoDAO;

    MealIngredientsDAO mealIngredientsDAO;
    MealPlanDAO mealPlanDAO;
    SharedPreferences sharedPreferences;
    IRepository repo;

    public LocalDS(Context context, IRepository repo) {
        this.mealInfoDAO = MealLocalDataSaurce.getInstance(context).mealInfoDao();
        this.mealIngredientsDAO = MealLocalDataSaurce.getInstance(context).mealIngredientsDao();
        this.mealPlanDAO = MealLocalDataSaurce.getInstance(context).mealPlanDao();
        this.repo = repo;
        this.sharedPreferences = context.getSharedPreferences("userdata", MODE_PRIVATE);

    }

    public void saveFavMeal(String mealId) {
        repo.fetchDataFromRemote(mealId, new MealCallback() {
            @Override
            public void onSuccess(List<Meal> mealDetail) {
                Meal m = mealDetail.get(0);
                m.setInFav(true);
                new Thread(() -> {
                    MealPlan plan = mealPlanDAO.getMealPlanById(m.getIdMeal());
                    if (plan != null) {
                        m.setDay(plan.getDay());
                        m.setMeal(plan.getMeal());
                        m.setInPlan(true);
                    } else {
                        m.setInPlan(false);
                    }
                    Filter(m);


//                   MealPlan p = mealPlanDAO.getMealPlanById(m.getIdMeal());
//                     if(p != null) {
//                       m.setInPlan(true);
//                     }
//                     else {
//                            m.setInPlan(false);
//                     }
//                    Filter(m);
                }).start();

            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("Error", t.getMessage());
            }
        });


    }

    public void Filter(Meal m) {
        MealInfo info = new MealInfo(m.getIdMeal(), m.getStrMeal(), m.getStrCategory(), m.getStrArea(),
                m.getStrInstructions(), m.getStrMealThumb(), m.getStrYoutube(), m.getInPlan(), m.getInFav());
        String ingredientsJson = new Gson().toJson(m.getAllIngredients());
        String measuresJson = new Gson().toJson(m.getAllMeasures());
        MealIngredients mealIngredients = new MealIngredients(m.getIdMeal(), m.getStrMeal(), m.getStrYoutube(), m.getStrInstructions(), ingredientsJson, measuresJson);
//       try {
//           if (m.getInPlan()) {
//               MealPlan plan = new MealPlan(m.getIdMeal(), m.getDay(), m.getMeal());
//               insertIntoDb(info, mealIngredients, plan, m.getInPlan());
//           }
//
//       }
//         catch (Exception e){
//             insertIntoDb(info, mealIngredients, null, m.getInPlan());
//              Log.e("Error",e.getMessage());
//         }

        try {
            if (m.getInPlan() != null && m.getInPlan()) {
                MealPlan plan = new MealPlan(m.getIdMeal(), m.getDay(), m.getMeal());
                insertIntoDb(info, mealIngredients, plan, true);
            } else {
                insertIntoDb(info, mealIngredients, null, false);
            }
        } catch (Exception e) {
            insertIntoDb(info, mealIngredients, null, false);
            Log.e("Error", e.getMessage());
        }


    }

    public void insertIntoDb(MealInfo info, MealIngredients ingredients, MealPlan mealPlan, boolean inPlan) {
        new Thread(() -> {
            mealInfoDAO.insertMeal(info);
            mealIngredientsDAO.insertIngredient(ingredients);
            if (inPlan) {
                mealPlanDAO.insertMealPlan(mealPlan);
            }
        }).start();
    }

    public LiveData<List<MealInfo>> getFavorites() {

        return mealInfoDAO.getAllFavourites();
    }

    public void deleteFav(MealInfo meal) {
        new Thread(() -> {
            if (meal.getInPlan()) {
                meal.setInFav(false);
                mealInfoDAO.update(meal);
            } else {
                mealInfoDAO.delete(meal);
            }

        }).start();
    }

    public void getMealDetail(String mealId, MealCallback callback) {

        new Thread(() -> {
            MealInfo mealInfo = mealInfoDAO.getMealById(mealId);
            MealIngredients mealIngredients = mealIngredientsDAO.getIngredientsById(mealId);
            Meal meal = new Meal();
            meal.setIdMeal(mealInfo.getIdMeal());
            meal.setStrMeal(mealInfo.getStrMeal());
            meal.setStrCategory(mealInfo.getStrCategory());
            meal.setStrArea(mealInfo.getStrArea());
            meal.setStrInstructions(mealInfo.getStrInstructions());
            meal.setStrMealThumb(mealInfo.getStrMealThumb());
            meal.setStrYoutube(mealInfo.getStrYoutube());
            meal.setAllIngredients(new Gson().fromJson(mealIngredients.getIngredients(), List.class));
            meal.setAllMeasures(new Gson().fromJson(mealIngredients.getStrMeasure(), List.class));
            List<Meal> mealList = new ArrayList<>();
            mealList.add(meal);
            callback.onSuccess(mealList);


        }).start();


    }

    public void getTodayMeals(String chipText, TodaysPlanCallback callback) {

        mealPlanDAO.getMealPlanByDay(chipText).observeForever(mealPlans -> {
            // Log.i("mealsize", String.valueOf(mealPlans.size()));
            List<Meal> meals = new ArrayList<>();
            for (MealPlan plan : mealPlans) {
                getMealDetail(plan.getIdMeal(), new MealCallback() {
                    @Override
                    public void onSuccess(List<Meal> mealDetail) {
                        meals.add(mealDetail.get(0));
                        mealDetail.get(0).setDay(plan.getDay());
                        mealDetail.get(0).setMeal(plan.getMeal());
                        if (meals.size() == mealPlans.size()) {
                            callback.onSuccess(meals);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        callback.onFailure(t);
                    }
                });
            }
        });


    }

    public void saveMealToPlan(String mealId, String checkedChipDay, String checkedChipMeal) {
        repo.fetchDataFromRemote(mealId, new MealCallback() {
            @Override
            public void onSuccess(List<Meal> mealDetail) {
                Meal m = mealDetail.get(0);
                m.setDay(checkedChipDay);
                m.setMeal(checkedChipMeal);
                m.setInPlan(true);
                new Thread(() -> {
                    MealInfo info = mealInfoDAO.getMealById(m.getIdMeal());
                    if (info != null) {
                        m.setInFav(info.getInFav());

                    }

                    Filter(m);
                }).start();


            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("Error", t.getMessage());
            }
        });


    }

    public void deleteAll() {
        new Thread(() -> {
            mealInfoDAO.deleteAll();
        }).start();
    }

    public void saveUserInSharedPref(String name, String email) {
        //SharedPreferences sharedPreferences =   context.getSharedPreferences("userdata", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.commit();

    }

    public List<String> getProfileData() {
        List<String> data = new ArrayList<>();
        data.add(sharedPreferences.getString("name", ""));
        data.add(sharedPreferences.getString("email", ""));
        return data;
    }

    public void getAllData(FirebaseCallback callback) {
        List<Meal> meals = new ArrayList<>();
        new Thread(() -> {
            List<MealInfo> mealInfo = mealInfoDAO.getAllMeals();
            SerializeData(mealInfo, callback);
        }).start();

    }

    private void SerializeData(List<MealInfo> mealInfo, FirebaseCallback callback) {
        List<Meal> meals = new ArrayList<>();
        new Thread(() -> {
            for (MealInfo info : mealInfo) {
                MealIngredients mealIngredients = mealIngredientsDAO.getingbtid(info.getIdMeal());
                MealPlan plan = mealPlanDAO.getMealPlanById(info.getIdMeal());
                Boolean inFav = info.getInFav();

//                if (mealIngredients == null) {
//                    Log.e("NullCheck0", "No meal ingredients found for mealId: " + info.getIdMeal());
//                } else {
//                    Log.e("NullCheck1", "Meal ingredients found for mealId: " + info.getIdMeal());
//                }
                Meal meal = new Meal();
                meal.setIdMeal(info.getIdMeal());
                meal.setStrMeal(info.getStrMeal());
                meal.setStrCategory(info.getStrCategory());
                meal.setStrArea(info.getStrArea());
                meal.setStrInstructions(info.getStrInstructions());
                meal.setStrMealThumb(info.getStrMealThumb());
                meal.setStrYoutube(info.getStrYoutube());
                meal.setAllIngredients(new Gson().fromJson(mealIngredients.getIngredients(), List.class));
                meal.setAllMeasures(new Gson().fromJson(mealIngredients.getStrMeasure(), List.class));
                if (plan != null) {
                    meal.setDay(plan.getDay());
                    meal.setMeal(plan.getMeal());
                    meal.setInPlan(true);
                }

                Log.i("inFav", String.valueOf(inFav));
                if (inFav!=null && inFav) {
                    meal.setInFav(true);
                }
                meals.add(meal);
            }
            Log.i("mealsize", String.valueOf(meals.size()));
            repo.backupData(meals, callback);
        }).start();
    }

    protected void deserializeData(FirebaseCallback callback, List<Meal> meals) {
        Log.i("mealsize in restore", String.valueOf(meals.size()));
        for (Meal meal : meals) {
            Filter(meal);
        }
        callback.onSuccess();
    }

    public void deleteMealFromPlan(String idMeal) {
        new Thread(() -> {
            MealInfo info = mealInfoDAO.getMealById(idMeal);
            info.setInPlan(false);
            mealInfoDAO.insertMeal(info);
            mealPlanDAO.deleteMealPlan(idMeal);
        }).start();
    }
}
