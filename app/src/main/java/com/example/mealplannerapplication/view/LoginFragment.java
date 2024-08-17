package com.example.mealplannerapplication.view;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Activity;
import android.content.Intent;
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
import com.example.mealplannerapplication.presenter.loginPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginFragment extends Fragment implements loginInterface {

loginPresenter presenter;
Button loginButton, signUpButton, googlesigninbtn;
TextView email, password;
    private    GoogleSignInOptions   gso;
    private GoogleSignInClient signInClient;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        signInClient = GoogleSignIn.getClient( getActivity(), gso);

        super.onViewCreated(view, savedInstanceState);
        signUpButton = view.findViewById(R.id.signupBtn);
        loginButton = view.findViewById(R.id.loginBtn);
        googlesigninbtn = view.findViewById(R.id.sign_in_button);
        email = view.findViewById(R.id.mailV);
        password = view.findViewById(R.id.loginpass);
        presenter = new loginPresenter(LoginFragment.this);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signupFragment2);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isVaild = checkValidation();
                if((isVaild)){
                   presenter.login(email.getText().toString(), password.getText().toString());
                }

            }
        });

        googlesigninbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = signInClient.getSignInIntent();
             startActivityForResult(signInIntent, RC_SIGN_IN);
                //presenter.signInWithGoogle( );

            }
        });
    }

    @Override
    public void loginSuccess() {
Toast.makeText(getContext(), "Welcome Back", Toast.LENGTH_SHORT).show();
Intent intent = new Intent(getActivity(), activity2.class);
startActivity(intent);
    }

    @Override
    public void loginError() {
        Toast.makeText(getContext(), "Login Failed, wrong email or password", Toast.LENGTH_SHORT).show();
    }

    public boolean checkValidation() {
        boolean isValid = true;

        TextInputLayout mail = getView().findViewById(R.id.mailLayout);
        TextInputLayout pass = getView().findViewById(R.id.passLayout);
        mail.setError(null);
        pass.setError(null);
        if (email.getText().toString().isEmpty()) {
            mail.setError("Email is required");
            isValid = false;
        }

        if (password.getText().toString().isEmpty()) {
            pass.setError("Password is required");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                }
            } catch (ApiException e) {
                // Google Sign-In failed
                Toast.makeText(getContext(), "Google Sign-In failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        loginSuccess();
                        requireActivity().finish(); // Finish the current activity if needed
                    } else {
                        loginError();
                    }
                });
    }
}