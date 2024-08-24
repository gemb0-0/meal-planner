package com.example.mealplannerapplication.view;

import android.content.Intent;
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

import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.presenter.UserProfilePresenter;
import com.example.mealplannerapplication.view.activity1.activity1Scr;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class UserProfile extends Fragment implements UserProfileInterface {
    Button SignoutBtn,backupBtn,restoreBtn;
    TextView email,name;
    UserProfilePresenter userProfilePresenter;
    public UserProfile() {
        // Required empty public constructor
    }


    public static UserProfile newInstance(String param1, String param2) {
        UserProfile fragment = new UserProfile();
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
        userProfilePresenter = new UserProfilePresenter(Repository.getInstance(getContext()),this);
        //implemneting signout in MVP
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String id = auth.getCurrentUser().getUid();
        DocumentReference docRef = db.collection("users").document(id);
        docRef.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    name.setText(value.getString("name"));
                    email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                }else
                    System.out.println("errorrrrrrrrrrrrrrrrrrrrrrrr");

            }
        });

      backupBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              userProfilePresenter.backup();
          }
        });
      restoreBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              userProfilePresenter.restore();
          }
        });



        SignoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), activity1Scr.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                userProfilePresenter.deleteDB();
            }
        });


    }


    @Override
    public void backupSuccess() {
     //   Toast.makeText(getContext(), "Backup Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void backupError() {
    //Toast.makeText(getContext(), "Backup Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void restoreSuccess() {
//Toast.makeText(getContext(), "Restore Success", Toast.LENGTH_SHORT).show();
    }
}