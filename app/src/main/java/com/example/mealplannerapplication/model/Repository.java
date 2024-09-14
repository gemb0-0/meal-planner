package com.example.mealplannerapplication.model;


import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealInfo;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.CategoryCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.FirebaseCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.IngredientsCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.AuthCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.MealCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.MealInfoCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.RegionCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.SingleRegionCallBack;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.TodaysPlanCallback;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.RemoteDataSource.MealApi;
import com.example.mealplannerapplication.model.RemoteDataSource.Retrofit;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Repository implements IRepository {

    private static Repository repository;
    private com.example.mealplannerapplication.model.RemoteDataSource.MealApi MealApi;
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    //  private DAO mealDao;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    LocalDS LocalDS;
    RemoteDs RemoteDs;

    private Repository(Context context) {
        MealApi = Retrofit.getClient(BASE_URL).create(MealApi.class);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        RemoteDs = new RemoteDs(this);
        LocalDS = new LocalDS(context, this);
    }

    public static Repository getInstance(Context context) {
        if (repository == null) {
            repository = new Repository(context);
        }
        return repository;
    }

    @Override
    public void fetchMealoftheday(MealCallback callback) {
        RemoteDs.fetchMealOfTheDay(callback);
    }

    @Override
    public void getCategories(CategoryCallback callback) {
        RemoteDs.getCategories(callback);
    }

    @Override
    public void getRegions(RegionCallback RegionCallback) {
        RemoteDs.getRegions(RegionCallback);
    }

    @Override
    public void getIngredients(IngredientsCallback ingredientsCallback) {
        RemoteDs.getIngredients(ingredientsCallback);
    }

    @Override
    public void getMealsByRegion(String regionName, SingleRegionCallBack singleRegionCallBack) {
        RemoteDs.getMealsByRegion(regionName, singleRegionCallBack);
    }

    @Override
    public void getMealsByIngredient(String id, SingleRegionCallBack singleRegionCallBack) {
        RemoteDs.getMealsByIngredient(id, singleRegionCallBack);
    }

    @Override
    public void getMealsByCategory(String id, SingleRegionCallBack singleRegionCallBack) {
        RemoteDs.getMealsByCategory(id, singleRegionCallBack);
    }

    @Override
    public void SearchMealByName(String name, MealInfoCallback callback) {
        RemoteDs.SearchMealByName(name, callback);
    }

    @Override
    public void deleteDB() {
        LocalDS.deleteAll();
    }

    @Override
    public void getAllData(FirebaseCallback callback) {
        LocalDS.getAllData(callback);

    }

    @Override
    public void restoreData(FirebaseCallback callback) {
        RemoteDs.restoreData(callback);
    }

    @Override
    public void getTodayMeals(String chipText, TodaysPlanCallback callback) {
        LocalDS.getTodayMeals(chipText, callback);
    }


    @Override
    public void saveFavToDb(String mealId) {
        LocalDS.saveFavMeal(mealId);
    }

    @Override
    public LiveData<List<MealInfo>> fetchFavourites() {
        return LocalDS.getFavorites();
    }

    @Override
    public void getMealDetailDb(String mealId, MealCallback callback) {
        LocalDS.getMealDetail(mealId, callback);

    }

    @Override
    public void getMealDetail(String mealId, MealCallback callback) {
        RemoteDs.getMeal(mealId, callback);
    }

    @Override
    public void deleteFav(MealInfo meal) {
        LocalDS.deleteFav(meal);
    }

    @Override
    public void saveToPlan(String mealId, String checkedChipDay, String checkedChipMeal) {

        LocalDS.saveMealToPlan(mealId, checkedChipDay, checkedChipMeal);
    }

    @Override
    public void signInWithGoogle(GoogleSignInAccount account, AuthCallback authCallback) {
        RemoteDs.signInWithGoogle(account, authCallback);
    }

    @Override
    public void signup(String name, String email, String password, AuthCallback authCallback) {
        RemoteDs.signup(name, email, password, authCallback);
    }

    @Override
    public void login(String email, String password, AuthCallback authCallback) {
        RemoteDs.login(email, password, authCallback);
    }

    public List<String> getProfileData() {
        return LocalDS.getProfileData();
    }

    @Override
    public void saveUserDataLocal(String name, String email) {
        LocalDS.saveUserInSharedPref(name, email);
    }

    @Override
    public void dataFromFirebase(FirebaseCallback callback, List<Meal> meals) {
        LocalDS.deserializeData(callback, meals);
    }

    @Override
    public void fetchDataFromRemote(String mealId, MealCallback callback) {
        RemoteDs.getMeal(mealId, callback);
    }

    @Override
    public void backupData(List<Meal> data, FirebaseCallback callback) {
        RemoteDs.backupData(data, callback);
    }

    public void deleteMealFromPlan(String idMeal) {
        LocalDS.deleteMealFromPlan(idMeal);
    }
}
