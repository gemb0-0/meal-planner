package com.example.mealplannerapplication.presenter;

import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.FirebaseCallback;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.view.activity2.Profile.UserProfileInterface;

import java.util.List;

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
           // view.Error();
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
              //  view.Error();
            }
        });
    }

    public void getUserData() {
        List<String> data = repo.getProfileData();
        view.setUserData(data);
    }
}
