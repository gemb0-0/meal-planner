package com.example.mealplannerapplication.model;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import com.example.mealplannerapplication.model.db.DAO;
import com.example.mealplannerapplication.model.db.MealLocalDataSaurce;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Repository {

    private static Repository repository;
    private MealApi MealApi;
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private DAO mealDao;
    private Repository(Context context) {

        MealApi = retrofit.getClient(BASE_URL).create(MealApi.class);
        mealDao =  MealLocalDataSaurce.getInstance(context).mealDao();
    }

    public static Repository getInstance(Context context) {
        if (repository == null) {
            repository = new Repository(context);
        }
        return repository;
    }


    public void fetchMealoftheday(mealofthedayCallback callback) {
        Call <mealofthedayResponse> call = MealApi.getMealoftheday();
        call.enqueue(new Callback<mealofthedayResponse>() {
            @Override
            public void onResponse(Call<mealofthedayResponse> call, retrofit2.Response<mealofthedayResponse> response) {
                if (response.isSuccessful()&& response.body() != null) {
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
        Call <mealofthedayResponse> call = MealApi.getDetail(mealId);
        call.enqueue(new Callback<mealofthedayResponse>() {
            @Override
            public void onResponse(Call<mealofthedayResponse> call, retrofit2.Response<mealofthedayResponse> response) {
                if (response.isSuccessful()&& response.body() != null) {

                    callback.onSuccess(response.body().getDetail(mealId));

                }
            }

            @Override
            public void onFailure(Call<mealofthedayResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void saveMeal(String mealId) {
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
      return mealDao.getAllMeals();
    }

    public void deleteFav(Meal meal) {
        new Thread(() -> mealDao.delete(meal)).start();
    }
}
