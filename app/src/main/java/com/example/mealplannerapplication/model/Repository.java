package com.example.mealplannerapplication.model;

import retrofit2.Call;
import retrofit2.Callback;

public class Repository {

    private static Repository repository;
    private MealApi MealApi;
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private Repository() {
        MealApi = retrofit.getClient(BASE_URL).create(MealApi.class);
    }

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
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
}
