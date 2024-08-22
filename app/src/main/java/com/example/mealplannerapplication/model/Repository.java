package com.example.mealplannerapplication.model;


import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mealplannerapplication.model.db.DAO;
import com.example.mealplannerapplication.model.db.MealLocalDataSaurce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static Repository repository;
    private MealApi MealApi;
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private DAO mealDao;

    private Repository(Context context) {

        MealApi = retrofit.getClient(BASE_URL).create(MealApi.class);
        mealDao = MealLocalDataSaurce.getInstance(context).mealDao();
    }

    public static Repository getInstance(Context context) {
        if (repository == null) {
            repository = new Repository(context);
        }
        return repository;
    }


    public void fetchMealoftheday(mealofthedayCallback callback) {
        Call<mealofthedayResponse> call = MealApi.getMealoftheday();
        call.enqueue(new Callback<mealofthedayResponse>() {
            @Override
            public void onResponse(Call<mealofthedayResponse> call, Response<mealofthedayResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMealDetail());

                }
            }

            @Override
            public void onFailure(Call<mealofthedayResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }


    public void fetchMealDetail(String mealId, MealDetailCallback callback) {
        Call<mealofthedayResponse> call = MealApi.getDetail(mealId);
        call.enqueue(new Callback<mealofthedayResponse>() {
            @Override
            public void onResponse(Call<mealofthedayResponse> call, Response<mealofthedayResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    callback.onSuccess(response.body().getDetail(mealId));

                }
            }

            @Override
            public void onFailure(Call<mealofthedayResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void saveToFav(String mealId) {
        fetchMealDetail(mealId, new MealDetailCallback() {
            @Override
            public void onSuccess(List<Meal> mealDetail) {
                Meal m = mealDetail.get(0);
                new Thread(() -> mealDao.insert(m)).start();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<List<Meal>> fetchFavourite() {
        return mealDao.getAllFavouriteMeals();
    }

    public void deleteFav(Meal meal) {
        new Thread(() -> mealDao.delete(meal)).start();
    }

    public void getFromDb(String mealId, MealDetailCallback mealDetailCallback) {
        new Thread(() -> {
            List<Meal> mealDetail = mealDao.getMealDetail(mealId);
            if (mealDetail.size() > 0) {
                mealDetailCallback.onSuccess(mealDetail);
                //System.out.println("fffffffffffffffff"+mealDetail.get(0).getIdMeal());
                // Toast.makeText((Context) mealDetailCallback, "Meal already in Favourites"+mealDetail.size(), Toast.LENGTH_SHORT).show();
            } else {
                mealDetailCallback.onFailure(new Throwable("No data found"));
            }
        }).start();
    }

    public void saveToPlan(String mealId, String checkedChipDay, String checkedChipMeal) {
        fetchMealDetail(mealId, new MealDetailCallback() {
            @Override
            public void onSuccess(List<Meal> mealDetail) {
                Meal m = mealDetail.get(0);
                m.setInPlan(true);
                m.setWeekDay(checkedChipDay);
                m.setMeal(checkedChipMeal);
                new Thread(() -> mealDao.insert(m)).start();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public void getTodayMeals(String chipText, TodaysPlanCallback callback) {


        new Thread(() -> {
            List<Meal> meals = mealDao.getTodayMeals(chipText);
            if (meals != null && !meals.isEmpty()) {
                 callback.onSuccess(filterMeals(meals));
                //  callback.onSuccess(filterMeals(meals));

            } else {
                callback.onFailure(new Throwable("No meals found for today"));
            }
        }).start();


    }

    private Map<String, Meal> filterMeals(List<Meal> meals) {
        Map<String, Meal> mealMap = new HashMap<>();
        Set<String> mealTypes = Set.of("BreakFast", "Launch", "Dinner");

        for (Meal meal : meals) {
            String mealType = meal.getMeal();
            if (mealTypes.contains(mealType)) {
                mealMap.put(mealType, meal);
            }
        }
        return mealMap;
    }
}
