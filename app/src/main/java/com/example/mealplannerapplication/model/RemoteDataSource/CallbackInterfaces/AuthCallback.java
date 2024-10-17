package com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces;

public interface AuthCallback {
    void loginSuccess();
    void loginError(String err);
}
