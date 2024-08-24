package com.example.mealplannerapplication.presenter;

import com.example.mealplannerapplication.model.CallbackInterfaces.FirebaseCallback;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.view.UserProfileInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserProfilePresenter {
    Repository repo;
    UserProfileInterface view;

    public UserProfilePresenter(Repository repository, UserProfileInterface view) {
        this.repo = repository;
        this.view = view;

    }


    public void deleteDB() {
        repo.deleteDB();

    }

    public void backup() {
        repo.getAllData(new FirebaseCallback() {
            @Override
            public void onSuccess() {
                view.backupSuccess();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    public void restore() {
        repo.restoreData(new FirebaseCallback() {
            @Override
            public void onSuccess() {
                view.restoreSuccess();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
