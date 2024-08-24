package com.example.mealplannerapplication.presenter;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.view.activity1.SignUp.signupFragment;
import com.example.mealplannerapplication.view.activity1.SignUp.signupInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signupPresenter {
signupInterface signupview;
private FirebaseAuth mAuth;
private FirebaseFirestore db;
    Map<String, Object> user = new HashMap<>();

    public signupPresenter(signupFragment signupFragment) {
        this.signupview = signupFragment;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void signup(String name, String email, String password, String confirmpassword) {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user.put("name", name);
                    signupview.signupSuccess();
                    String id = mAuth.getCurrentUser().getUid();
                    db.collection("users").document(id).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG,  R.string.error_adding_document + id);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, String.valueOf(R.string.error_adding_document), e);
                        }
                    });
                } else {
                    signupview.signupError();
                }
            });

    }

}
