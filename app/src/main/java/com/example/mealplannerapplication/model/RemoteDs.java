package com.example.mealplannerapplication.model;

import static android.content.ContentValues.TAG;

import static java.lang.String.valueOf;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealInfo;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.CategoryCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.FirebaseCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.IngredientsCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.AuthCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.MealCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.MealInfoCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.NameCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.RegionCallback;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.SingleRegionCallBack;
import com.example.mealplannerapplication.model.RemoteDataSource.MealApi;
import com.example.mealplannerapplication.model.RemoteDataSource.Retrofit;
import com.example.mealplannerapplication.model.RemoteDataSource.response.IngredientsResponseApi;
import com.example.mealplannerapplication.model.RemoteDataSource.response.RegionResponseApi;
import com.example.mealplannerapplication.model.RemoteDataSource.response.SingleRegionResponseApi;
import com.example.mealplannerapplication.model.RemoteDataSource.response.mealResponseApi;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class RemoteDs {

    private MealApi MealApi;
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private IRepository repo;

    public RemoteDs(IRepository repo) {

        MealApi = Retrofit.getClient(BASE_URL).create(MealApi.class);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        this.repo = repo;
    }

    public void signInWithGoogle(GoogleSignInAccount acct, AuthCallback authCallback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String name = mAuth.getCurrentUser().getDisplayName();
                saveUserDataFirebase(name);
                repo.saveUserDataLocal(name, acct.getEmail());
                restoreData(new FirebaseCallback() {
                    @Override
                    public void onSuccess() {
                        Log.i("Success", "Data restored");
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.i("Error", t.getMessage());
                    }
                });
                authCallback.loginSuccess();
            } else {
                authCallback.loginError("error signing in");

            }
        });
    }

    private void saveUserDataFirebase(String name) {

        Map<String, Object> user = new HashMap<>();
        user.put(valueOf(R.string.name), name);
        String id = mAuth.getCurrentUser().getUid();

        db.collection("users").document(id).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Log.d(TAG, aVoid.toString());
                Log.i("Success", "User data saved");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, e.getMessage());
            }
        });
    }

    public void signup(String name, String email, String password, AuthCallback authCallback) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                saveUserDataFirebase(name);
                authCallback.loginSuccess();
            } else {
                authCallback.loginError("error signing up");
            }
        });
    }

    public void login(String email, String password, AuthCallback authCallback) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    getNameFromFirebase(new NameCallback() {
                        @Override
                        public void nameCallback(String name) {
                            repo.saveUserDataLocal(email, password);

                        }
                    });
                    restoreData(new FirebaseCallback() {
                        @Override
                        public void onSuccess() {
                            // authCallback.loginSuccess();
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                    authCallback.loginSuccess();
                } else {
                    authCallback.loginError("error signing in");
                }
            }
        });
    }

    private void getNameFromFirebase(NameCallback nameCallback) {
        String id = mAuth.getCurrentUser().getUid();
        DocumentReference docRef = db.collection("users").document(id);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    String name = value.getString(valueOf(R.string.name));
                    nameCallback.nameCallback(name);
                }
            }
        });

    }

    public void getMeal(String mealId, MealCallback callback) {
        Call<mealResponseApi> call = MealApi.getDetail(mealId);
        call.enqueue(new retrofit2.Callback<mealResponseApi>() {
            @Override
            public void onResponse(Call<mealResponseApi> call, retrofit2.Response<mealResponseApi> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getDetail(mealId));

                }
            }

            @Override
            public void onFailure(Call<mealResponseApi> call, Throwable t) {
                callback.onFailure(t);
            }
        });


    }

    public void getCategories(CategoryCallback callback) {
        Call<mealResponseApi> call = MealApi.getCategories();
        call.enqueue(new retrofit2.Callback<mealResponseApi>() {
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

    public void fetchMealOfTheDay(MealCallback callback) {
        Call<mealResponseApi> call = MealApi.getMealoftheday();
        call.enqueue(new retrofit2.Callback<mealResponseApi>() {
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

    public void getRegions(RegionCallback RegionCallback) {

        Call<RegionResponseApi> call = MealApi.getRegions();
        call.enqueue(new retrofit2.Callback<RegionResponseApi>() {
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
        call.enqueue(new retrofit2.Callback<IngredientsResponseApi>() {
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
        call.enqueue(new retrofit2.Callback<SingleRegionResponseApi>() {
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
        call.enqueue(new retrofit2.Callback<SingleRegionResponseApi>() {
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
        call.enqueue(new retrofit2.Callback<SingleRegionResponseApi>() {
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

    public void SearchMealByName(String name, MealInfoCallback callback) {
        Call<mealResponseApi> call = MealApi.getMealByName(name);
        call.enqueue(new retrofit2.Callback<mealResponseApi>() {
            @Override
            public void onResponse(Call<mealResponseApi> call, retrofit2.Response<mealResponseApi> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Meal> meals = response.body().getMealDetail();
                    if (meals != null) {
                        Collections.sort(meals, new Comparator<Meal>() {
                            @Override
                            public int compare(Meal meal1, Meal meal2) {
                                int index1 = meal1.getStrMeal().indexOf(name);
                                int index2 = meal2.getStrMeal().indexOf(name);

                                return Integer.compare(index1, index2);
                            }
                        });

                    }
                    FilterMeals(meals, callback);

                }
            }

            @Override
            public void onFailure(Call<mealResponseApi> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    protected void FilterMeals(List<Meal> meals, MealInfoCallback callback) {
        List<MealInfo> mealInfos = new ArrayList<>();
        for (Meal m : meals) {
            MealInfo info = new MealInfo(m.getIdMeal(), m.getStrMeal(), m.getStrCategory(), m.getStrArea(), m.getStrInstructions(), m.getStrMealThumb(), m.getStrYoutube(), m.getInPlan(),m.getInFav());
            mealInfos.add(info);
        }
        callback.onSuccess(mealInfos);
    }

    public void backupData(List<Meal> meals, FirebaseCallback callback) {
        //may use final boolean to check for errors
        final boolean[] x = new boolean[1];
        Log.i("Backup", valueOf(meals.size()));
        for (Meal meal : meals) {
            Log.i("backing up", meal.getStrMeal());
            db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("Meals")
                    .document(meal.getIdMeal()).set(meal).addOnSuccessListener(aVoid -> {

            }).addOnFailureListener(e -> {
                x[0] =false;
                Log.i("Error", e.getMessage());
            });
        }
        callback.onSuccess();
//        if(x[0]) {
//            callback.onSuccess();
//        }
//        else
//            callback.onFailure(new Throwable("Error"));
    }

    public void restoreData(FirebaseCallback callback) {
        db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("Meals").get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Meal> meals = queryDocumentSnapshots.toObjects(Meal.class);
            repo.dataFromFirebase(callback, meals);
            //callback.onSuccess();
        });
    }
}
