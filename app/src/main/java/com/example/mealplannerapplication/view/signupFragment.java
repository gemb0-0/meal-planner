package com.example.mealplannerapplication.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.presenter.signupPresenter;
import com.google.android.material.textfield.TextInputLayout;

public class signupFragment extends Fragment implements signupInterface {

  public static final String EMAIL_VERIFICATION = "^[\\w.-]+@[\\w-]+\\.[a-zA-Z]{2,}$";
    public static final String PASSWORD_VERIFICATION =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

    Button signupButton;
  TextView email, password, confirmPassword,name;
  signupPresenter presenter;
    public static signupFragment newInstance(String param1, String param2) {
        signupFragment fragment = new signupFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.signup_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter=new signupPresenter(signupFragment.this);
        signupButton = view.findViewById(R.id.signupBtn);
        email = view.findViewById(R.id.mailV);
        password = view.findViewById(R.id.passwordsignup);
        confirmPassword = view.findViewById(R.id.confirmpasswordsignup);
        name = view.findViewById(R.id.nameV);


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean isvaild= checkValidation(view);
                if(isvaild){
                      presenter.signup(name.getText().toString(),email.getText().toString(), password.getText().toString(), confirmPassword.getText().toString());
                }
         }
        });

    }

    @Override
    public void signupSuccess() {
        Toast.makeText(getContext(), "User created", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(getView()).navigate(R.id.action_signupFragment_to_loginFragment);

    }

    @Override
    public void signupError() {
        Toast.makeText(getContext(), "Failed to create an account", Toast.LENGTH_SHORT).show();
    }


    private boolean checkValidation(View view) {
        boolean isValid = true;
        String Name = name.getText().toString(), Email = email.getText().toString(), Password = password.getText().toString(), ConfirmPassword = confirmPassword.getText().toString();
        TextInputLayout emailLayout = view.findViewById(R.id.mailLayout);
        TextInputLayout passwordLayout = view.findViewById(R.id.passLayout);
        TextInputLayout confirmpasswordLayout = view.findViewById(R.id.confirmpasswordLayout);
        TextInputLayout nameLayout = view.findViewById(R.id.nameLayout);
        nameLayout.setError(null);
        emailLayout.setError(null);
        passwordLayout.setError(null);
        confirmpasswordLayout.setError(null);
        if (Email.isEmpty() || Name.isEmpty() || Password.isEmpty() || ConfirmPassword.isEmpty()) {
            if (Email.isEmpty()) {
                emailLayout.setError("Email is required");
                isValid = false;
            }
            if (Name.isEmpty()) {
                nameLayout.setError("Name is required");
                isValid = false;
            }
            if (Password.isEmpty()) {
                passwordLayout.setError("Password is required");
                isValid = false;
            }
            if (ConfirmPassword.isEmpty()) {
                confirmpasswordLayout.setError("Confirm Password is required");
                isValid = false;
            }
        } else {
            if (!Password.matches(PASSWORD_VERIFICATION)) {
                passwordLayout.setError("Use at least 8 characters, one uppercase, one lowercase, one number, and one special character");
                isValid = false;
            }

            if (!Email.matches(EMAIL_VERIFICATION)) {
                emailLayout.setError("Invalid Email");
                isValid = false;
            }

            if (!Password.equals(ConfirmPassword)) {
                confirmpasswordLayout.setError("Passwords do not match");
                isValid = false;
            }
        }
        return isValid;
    }

}