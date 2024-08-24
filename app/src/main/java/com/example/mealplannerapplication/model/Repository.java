package com.example.mealplannerapplication.model;


import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.CategoryCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.FirebaseCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.IngredientsCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.MealCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.RegionCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.SingleRegionCallBack;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.TodaysPlanCallback;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.RemoteDataSource.MealApi;
import com.example.mealplannerapplication.model.RemoteDataSource.retrofit;
import com.example.mealplannerapplication.model.LocalDataSource.db.DAO;
import com.example.mealplannerapplication.model.LocalDataSource.db.MealLocalDataSaurce;
import com.example.mealplannerapplication.model.RemoteDataSource.response.IngredientsResponseApi;
import com.example.mealplannerapplication.model.RemoteDataSource.response.RegionResponseApi;
import com.example.mealplannerapplication.model.RemoteDataSource.response.SingleRegionResponseApi;
import com.example.mealplannerapplication.model.RemoteDataSource.response.mealResponseApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;

public class Repository {

    private static Repository repository;
    private com.example.mealplannerapplication.model.RemoteDataSource.MealApi MealApi;
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private DAO mealDao;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private Repository(Context context) {

        MealApi = retrofit.getClient(BASE_URL).create(MealApi.class);
        mealDao = MealLocalDataSaurce.getInstance(context).mealDao();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }

    public static Repository getInstance(Context context) {
        if (repository == null) {
            repository = new Repository(context);
        }
        return repository;
    }


    public void fetchMealoftheday(MealCallback callback) {
        Call<mealResponseApi> call = MealApi.getMealoftheday();
        call.enqueue(new Callback<mealResponseApi>() {
            @Override
            public void onResponse(Call<mealResponseApi> call, retrofit2.Response<mealResponseApi> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMealDetail());

                }
            }

            @Override
            public void onFailure(Call<mealResponseApi> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }


    public void fetchMealDetail(String mealId, MealCallback callback) {
        Call<mealResponseApi> call = MealApi.getDetail(mealId);
        call.enqueue(new Callback<mealResponseApi>() {
            @Override
            public void onResponse(Call<mealResponseApi> call, retrofit2.Response<mealResponseApi> response) {
                if (response.isSuccessful() && response.body() != null) {
                   // System.out.println("fffffffffffffffff "+response.body().getDetail(mealId).size());
                    callback.onSuccess(response.body().getDetail(mealId));

                }
            }

            @Override
            public void onFailure(Call<mealResponseApi> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void saveToFav(String mealId) {
        fetchMealDetail(mealId, new MealCallback() {
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

    public void getFromDb(String mealId, MealCallback mealCallback) {
        new Thread(() -> {
            List<Meal> mealDetail = mealDao.getMealDetail(mealId);
            if (mealDetail.size() > 0) {
                mealCallback.onSuccess(mealDetail);
                //System.out.println("fffffffffffffffff"+mealDetail.get(0).getIdMeal());
                // Toast.makeText((Context) mealDetailCallback, "Meal already in Favourites"+mealDetail.size(), Toast.LENGTH_SHORT).show();
            } else {
                mealCallback.onFailure(new Throwable("No data found"));
            }
        }).start();
    }

    public void saveToPlan(String mealId, String checkedChipDay, String checkedChipMeal) {
        fetchMealDetail(mealId, new MealCallback() {
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

    public void getCategories(CategoryCallback callback) {
        Call<mealResponseApi> call = MealApi.getCategories();
        call.enqueue(new Callback<mealResponseApi>() {
            @Override
            public void onResponse(Call<mealResponseApi> call, retrofit2.Response<mealResponseApi> response) {
                if (response.isSuccessful() && response.body() != null) {
                  callback.onSuccess(response.body().getCategories());

                }
            }

            @Override
            public void onFailure(Call<mealResponseApi> call, Throwable t) {

                callback.onFailure(t);

            }
        });
    }

    public void getRegions(RegionCallback RegionCallback) {
        Call<RegionResponseApi> call = MealApi.getRegions();
        call.enqueue(new Callback<RegionResponseApi>() {
            @Override
            public void onResponse(Call<RegionResponseApi> call, retrofit2.Response<RegionResponseApi> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RegionCallback.onSuccess(
                            response.body().getRegions()
                    );
                }
            }

            @Override
            public void onFailure(Call<RegionResponseApi> call, Throwable t) {
                RegionCallback.onFailure(t);
            }
        });
    }

    public void getIngredients(IngredientsCallback ingredientsCallback) {
        Call<IngredientsResponseApi> call = MealApi.getIngredients();
        call.enqueue(new Callback<IngredientsResponseApi>() {
            @Override
            public void onResponse(Call<IngredientsResponseApi> call, retrofit2.Response<IngredientsResponseApi> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ingredientsCallback.onSuccess(response.body().getIngredients());
                }
            }

            @Override
            public void onFailure(Call<IngredientsResponseApi> call, Throwable t) {
                ingredientsCallback.onFailure(t);
            }
        });
    }

    public void getMealsByRegion(String regionName, SingleRegionCallBack singleRegionCallBack) {
        Call<SingleRegionResponseApi> call = MealApi.getCountryMeals(regionName);
        call.enqueue(new Callback<SingleRegionResponseApi>() {
            @Override
            public void onResponse(Call<SingleRegionResponseApi> call, retrofit2.Response<SingleRegionResponseApi> response) {
                if (response.isSuccessful() && response.body() != null) {
                    singleRegionCallBack.onSuccess(response.body().getCountryMeals(regionName));
                }
            }

            @Override
            public void onFailure(Call<SingleRegionResponseApi> call, Throwable t) {
                singleRegionCallBack.onFailure(t);
            }
        });
    }

    public void getMealsByIngredient(String id, SingleRegionCallBack singleRegionCallBack) {
        Call<SingleRegionResponseApi> call = MealApi.getIngrediantMeal(id);
        call.enqueue(new Callback<SingleRegionResponseApi>() {
            @Override
            public void onResponse(Call<SingleRegionResponseApi> call, retrofit2.Response<SingleRegionResponseApi> response) {
                if (response.isSuccessful() && response.body() != null) {
                    singleRegionCallBack.onSuccess(response.body().getCountryMeals(id));
                }
            }

            @Override
            public void onFailure(Call<SingleRegionResponseApi> call, Throwable t) {
                singleRegionCallBack.onFailure(t);
            }
        });
    }

    public void getMealsByCategory(String id, SingleRegionCallBack singleRegionCallBack) {
        Call<SingleRegionResponseApi> call = MealApi.getCategoryMeal(id);
        call.enqueue(new Callback<SingleRegionResponseApi>() {
            @Override
            public void onResponse(Call<SingleRegionResponseApi> call, retrofit2.Response<SingleRegionResponseApi> response) {
                if (response.isSuccessful() && response.body() != null) {
                    singleRegionCallBack.onSuccess(response.body().getCountryMeals(id));
                }
            }

            @Override
            public void onFailure(Call<SingleRegionResponseApi> call, Throwable t) {
                singleRegionCallBack.onFailure(t);
            }
        });
    }

    public void SearchMealByName(String name, MealCallback mealCallback) {
        Call<mealResponseApi> call = MealApi.getMealByName(name);
        call.enqueue(new Callback<mealResponseApi>() {
            @Override
            public void onResponse(Call<mealResponseApi> call, retrofit2.Response<mealResponseApi> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Meal> meals = response.body().getMealDetail();
                    if(meals!=null) {
                        Collections.sort(meals, new Comparator<Meal>() {
                            @Override
                            public int compare(Meal meal1, Meal meal2) {
                                int index1 = meal1.getStrMeal().indexOf(name);
                                int index2 = meal2.getStrMeal().indexOf(name);

                                return Integer.compare(index1, index2);
                            }
                        });
                    }
                    mealCallback.onSuccess(meals);
                }
            }

            @Override
            public void onFailure(Call<mealResponseApi> call, Throwable t) {
                System.out.println("fffffffffffffffffg "+t.getMessage());
                mealCallback.onFailure(t);
            }
        });
    }

    public void deleteDB() {
        new Thread(() -> mealDao.deleteAll()).start();
    }

    public  void getAllData(FirebaseCallback callback) {
        LiveData<List<Meal>>m = mealDao.getAllData();
        uploadToFirebase(m, callback);

    }

    private void uploadToFirebase(LiveData<List<Meal>> m, FirebaseCallback callback) {
        if(m !=null) {
            m.observeForever(meals -> {
                for (Meal meal : meals) {
                    db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("Meals").document(meal.getIdMeal()).set(meal).addOnSuccessListener(aVoid -> {
                        callback.onSuccess();
                    }).addOnFailureListener(e -> {

                    });
                }
            });
        }
    }

    public void restoreData(FirebaseCallback callback) {

        db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("Meals").get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Meal> meals = queryDocumentSnapshots.toObjects(Meal.class);
            for (Meal meal : meals) {
                new Thread(() -> mealDao.insert(meal)).start();
            }
            callback.onSuccess();
        });


    }
}
