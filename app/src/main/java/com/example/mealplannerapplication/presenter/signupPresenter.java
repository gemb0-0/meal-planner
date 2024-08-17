package com.example.mealplannerapplication.presenter;

import com.example.mealplannerapplication.view.signupFragment;
import com.example.mealplannerapplication.view.signupInterface;
import com.google.firebase.auth.FirebaseAuth;

public class signupPresenter {
signupInterface signupview;
private FirebaseAuth mAuth;

    public signupPresenter(signupFragment signupFragment) {
        this.signupview = signupFragment;

    }

    public void signup(String name, String email, String password, String confirmpassword) {
        mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    signupview.signupSuccess();
                } else {
                    signupview.signupError();
                }
            });

    }

//           signupButton.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    Toast.makeText(getContext(), "User created", Toast.LENGTH_SHORT).show();
//                    System.out.println("User created");
//                } else {
//                    Toast.makeText(getContext(), "User not created", Toast.LENGTH_SHORT).show();
//                    System.out.println("User not created");
//                }
//            });
//        }
//    });
}
