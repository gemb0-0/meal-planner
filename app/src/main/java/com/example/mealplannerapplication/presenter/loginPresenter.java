package com.example.mealplannerapplication.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.mealplannerapplication.view.activity1.loginInterface;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginPresenter {
    private FirebaseAuth mAuth;
    loginInterface loginview;
   public loginPresenter(loginInterface loginFragment ) {
        this.loginview = loginFragment;
        mAuth = FirebaseAuth.getInstance();


    }

    public void login(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                          loginview.loginSuccess();
                        } else {
                            loginview.loginError();
                        }
                    }
                });
    }

        public void signInWithGoogle(   ) {





    }



    }


