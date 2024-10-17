package com.example.mealplannerapplication.presenter;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.AuthCallback;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.view.activity1.SignUp.SignupFragment;
import com.example.mealplannerapplication.view.activity1.SignUp.SignupInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupPresenter {
    SignupInterface signupview;
    Repository repo;

    public SignupPresenter(SignupFragment signupFragment, Repository repo) {
        this.signupview = signupFragment;
        this.repo = repo;
    }

    public void signup(String name, String email, String password) {
        repo.signup(name, email, password, new AuthCallback() {
            @Override
            public void loginSuccess() {
                signupview.signupSuccess();
            }

            @Override
            public void loginError(String err) {
                signupview.signupError(err);
            }
        });

    }

}
