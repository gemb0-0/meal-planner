package com.example.mealplannerapplication.presenter;

import static androidx.core.app.PendingIntentCompat.getActivity;


import androidx.annotation.NonNull;

import com.example.mealplannerapplication.model.RemoteDataSource.CallbackInterfaces.AuthCallback;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.view.activity1.Login.LoginInterface;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter {
    private FirebaseAuth mAuth;
    LoginInterface loginview;
    private GoogleSignInOptions gso;
    private GoogleSignInClient signInClient;
    private static final int RC_SIGN_IN = 9001;
    Repository repo;


    public LoginPresenter(LoginInterface loginFragment, Repository repo) {
        this.loginview = loginFragment;
        mAuth = FirebaseAuth.getInstance();
        this.repo = repo;

    }

    public void login(String email, String password) {

        repo.login(email, password, new AuthCallback() {
            @Override
            public void loginSuccess() {
                loginview.loginSuccess();
            }

            @Override
            public void loginError(String err) {
                loginview.loginError(err);
            }

        });
    }


    public void signInWithGoogle(GoogleSignInAccount account) {

        repo.signInWithGoogle(account, new AuthCallback() {
            @Override
            public void loginSuccess() {
                loginview.loginSuccess();
            }

            @Override
            public void loginError(String err) {
                loginview.loginError(err);
            }

        });

    }

}


