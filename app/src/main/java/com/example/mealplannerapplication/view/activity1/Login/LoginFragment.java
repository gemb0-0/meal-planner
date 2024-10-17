package com.example.mealplannerapplication.view.activity1.Login;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.presenter.LoginPresenter;
import com.example.mealplannerapplication.view.activity2.activity2;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginFragment extends Fragment implements LoginInterface {

    LoginPresenter presenter;
    Button loginButton, signUpButton, googlesigninbtn;
    TextView email, password;
    TextView guest;
    View profileView;
    LottieAnimationView lottieAnimationView;

    private GoogleSignInOptions gso;
    private GoogleSignInClient signInClient;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            Toast.makeText(getContext(), "Welcome Back", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getActivity(), activity2.class);
//            startActivity(intent);
//
//        }
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        signInClient = GoogleSignIn.getClient(getActivity(), gso);
        //will be deleted when i bring back splash screen


        lottieAnimationView = view.findViewById(R.id.animation_view);
        profileView = view.findViewById(R.id.viewpof);
        signUpButton = view.findViewById(R.id.signupBtn);
        loginButton = view.findViewById(R.id.loginBtn);
        googlesigninbtn = view.findViewById(R.id.sign_in_button);
        email = view.findViewById(R.id.mailV);
        password = view.findViewById(R.id.loginpass);
        presenter = new LoginPresenter(LoginFragment.this, Repository.getInstance(getContext()));
        guest = view.findViewById(R.id.guest_tv);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signupFragment2);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = checkValidation();
                if ((isValid)) {
                    presenter.login(email.getText().toString(), password.getText().toString());
                    profileView.setVisibility(View.VISIBLE);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                }

            }
        });

        googlesigninbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = signInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("guest", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("guest", true);
                editor.commit();

                NavController navController = Navigation.findNavController(v);
                if (navController.getCurrentDestination().getId() == R.id.loginFragment) {
                    Intent intent = new Intent(getActivity(), activity2.class);
                    startActivity(intent);
                    requireActivity().finish();
                }
            }
        });

    }

    @Override
    public void loginSuccess() {
        profileView.setVisibility(View.GONE);
        lottieAnimationView.setVisibility(View.GONE);
        Toast.makeText(getContext(), R.string.welcome_back, Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("guest", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("guest", false);
        editor.commit();
        Intent intent = new Intent(getActivity(), activity2.class);
        startActivity(intent);
        requireActivity().finish();

    }

    @Override
    public void loginError(String err) {
        profileView.setVisibility(View.GONE);
        lottieAnimationView.setVisibility(View.GONE);
        Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
    }

    public boolean checkValidation() {
        boolean isValid = true;

        TextInputLayout mail = getView().findViewById(R.id.mailLayout);
        TextInputLayout pass = getView().findViewById(R.id.passLayout);
        mail.setError(null);
        pass.setError(null);
        if (email.getText().toString().isEmpty()) {
            mail.setError(getString(R.string.email_is_required));
            isValid = false;
        }

        if (password.getText().toString().isEmpty()) {
            pass.setError(getString(R.string.password_is_required));
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
                    profileView.setVisibility(View.VISIBLE);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    presenter.signInWithGoogle(account);
                }
            } catch (ApiException e) {
                // Google Sign-In failed
                Toast.makeText(getContext(), getString(R.string.google_sign_in_failed) + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}