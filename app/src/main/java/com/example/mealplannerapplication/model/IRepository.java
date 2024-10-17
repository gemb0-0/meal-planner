package com.example.mealplannerapplication.model;

import androidx.lifecycle.LiveData;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealInfo;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.AuthCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.CategoryCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.FirebaseCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.IngredientsCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.MealCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.MealInfoCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.RegionCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.SingleRegionCallBack;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.TodaysPlanCallback;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

public interface IRepository {
    void fetchMealoftheday(MealCallback callback);

    void getCategories(CategoryCallback callback);

    void getRegions(RegionCallback RegionCallback);

    void getIngredients(IngredientsCallback ingredientsCallback);

    void getMealsByRegion(String regionName, SingleRegionCallBack singleRegionCallBack);

    void getMealsByIngredient(String id, SingleRegionCallBack singleRegionCallBack);

    void getMealsByCategory(String id, SingleRegionCallBack singleRegionCallBack);

    void SearchMealByName(String name, MealInfoCallback callback);

    void deleteDB();

    void getAllData(FirebaseCallback callback);

    void restoreData(FirebaseCallback callback);

    void getTodayMeals(String chipText, TodaysPlanCallback callback);

    void saveFavToDb(String mealId);

    LiveData<List<MealInfo>> fetchFavourites();

    void getMealDetailDb(String mealId, MealCallback callback);

    void getMealDetail(String mealId, MealCallback callback);

    void deleteFav(MealInfo meal);

    void saveToPlan(String mealId, String checkedChipDay, String checkedChipMeal);

    void signInWithGoogle(GoogleSignInAccount account, AuthCallback authCallback);

    void signup(String name, String email, String password, AuthCallback authCallback);

    void login(String email, String password, AuthCallback authCallback);

    void saveUserDataLocal(String name, String email);

    void dataFromFirebase(FirebaseCallback callback, List<Meal> meals);

    void fetchDataFromRemote(String mealId, MealCallback callback);

    void backupData(List<Meal> data, FirebaseCallback callback);
}
