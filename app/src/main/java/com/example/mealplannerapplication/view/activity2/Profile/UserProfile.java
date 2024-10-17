package com.example.mealplannerapplication.view.activity2.Profile;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.presenter.UserProfilePresenter;
import com.example.mealplannerapplication.view.activity1.activity1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;


public class UserProfile extends Fragment implements UserProfileInterface {
    Button SignoutBtn, backupBtn, restoreBtn;
    TextView email, name;
    UserProfilePresenter userProfilePresenter;
    LottieAnimationView lottieAnimationView;
    View profileView;
    public UserProfile() {}

    public static UserProfile newInstance(String param1, String param2) {
        UserProfile fragment = new UserProfile();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("guest", MODE_PRIVATE);
        boolean guest = sharedPreferences.getBoolean("guest", false);
        if(guest){
            Toast.makeText(getContext(), R.string.sign_up_or_sign_in_to_view_your_profile, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), activity1.class);
            startActivity(intent);
            requireActivity().finish();
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SignoutBtn = view.findViewById(R.id.signoutBtn);
        backupBtn = view.findViewById(R.id.backupBtn);
        restoreBtn = view.findViewById(R.id.restoreBtn);
        email = view.findViewById(R.id.profile_mail);
        name = view.findViewById(R.id.nameVV);
        lottieAnimationView = view.findViewById(R.id.animation_view);
        profileView = view.findViewById(R.id.viewpof);
        userProfilePresenter = new UserProfilePresenter(Repository.getInstance(getContext()), this);


//            Toast.makeText(getContext(), R.string.sign_up_or_sign_in_to_view_your_profile, Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getActivity(), activity1.class);
//            startActivity(intent);
        userProfilePresenter.getUserData();
        backupBtn.setOnClickListener(v -> {
            profileView.setVisibility(View.VISIBLE);
            lottieAnimationView.setVisibility(View.VISIBLE);
            lottieAnimationView.playAnimation();
            userProfilePresenter.backup();
        });
        restoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileView.setVisibility(View.VISIBLE);
                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();
                userProfilePresenter.restore();
            }
        });


        SignoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), activity1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                userProfilePresenter.deleteDB();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("guest", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("guest", true);
                editor.commit();
            }
        });

    }
//have to fix the animation and callback later

    @Override
    public void backupSuccess() {
       getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                profileView.setVisibility(View.INVISIBLE);
                lottieAnimationView.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Backup Success", Toast.LENGTH_SHORT).show();
            }
        });
//        profileView.setVisibility(View.INVISIBLE);
//        lottieAnimationView.setVisibility(View.GONE);
//        Toast.makeText(getContext(), "Backup Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Error() {
        profileView.setVisibility(View.INVISIBLE);
        lottieAnimationView.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void restoreSuccess() {
        Toast.makeText(getContext(), "Restore Success", Toast.LENGTH_SHORT).show();
        profileView.setVisibility(View.INVISIBLE);
        lottieAnimationView.setVisibility(View.GONE);

    }

    @Override
    public void setUserData(List<String> data) {
        name.setText(data.get(0));
        email.setText(data.get(1));
    }
}