package com.example.mealplannerapplication.view;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.view.activity1.activity1Scr;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class profile extends Fragment {
    Button signoutBtb;
    TextView email,name;

    public profile() {
        // Required empty public constructor
    }


    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
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
        signoutBtb = view.findViewById(R.id.signoutBtn);
        email = view.findViewById(R.id.profile_mail);
        name = view.findViewById(R.id.nameVV);

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
                    System.out.println(value.getString("name"));
                }else
                    System.out.println("errorrrrrrrrrrrrrrrrrrrrrrrr");

            }
        });




       String mail= FirebaseAuth.getInstance().getCurrentUser().getEmail();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
        } else {
            // No user is signed in
        }

       email.setText(mail);
        signoutBtb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), activity1Scr.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
    }
}