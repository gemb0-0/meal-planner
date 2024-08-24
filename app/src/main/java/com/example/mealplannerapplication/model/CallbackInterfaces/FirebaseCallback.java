package com.example.mealplannerapplication.model.CallbackInterfaces;

public interface FirebaseCallback {
    void onSuccess();
    void onFailure(Throwable t);
}
