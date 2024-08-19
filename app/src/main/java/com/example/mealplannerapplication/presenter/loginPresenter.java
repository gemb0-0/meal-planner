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
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                if (account != null) {
//                    // Google Sign-In was successful, now authenticate with Firebase
//                    firebaseAuthWithGoogle(account);
//                }
//            } catch (ApiException e) {
//                // Google Sign-In failed
//                //Toast.makeText(getContext(), "Google Sign-In failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(requireActivity(), task -> {
//                    if (task.isSuccessful()) {
//                        // Sign-in successful
//                      //  Toast.makeText(getContext(), "Sign-In successful", Toast.LENGTH_SHORT).show();
//                        //  startActivity(new Intent(getContext(), HomeActivity.class));
//                        requireActivity().finish(); // Finish the current activity if needed
//                    } else {
//                        // Sign-in failed
//                     //   Toast.makeText(getContext(), "Sign-In failed", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }


    }


