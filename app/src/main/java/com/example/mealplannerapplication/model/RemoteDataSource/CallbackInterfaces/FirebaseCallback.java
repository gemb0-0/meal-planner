package com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces;

public interface FirebaseCallback {
    void onSuccess();
    void onFailure(Throwable t);
}
