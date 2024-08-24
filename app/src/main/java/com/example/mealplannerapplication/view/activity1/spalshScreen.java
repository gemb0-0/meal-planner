package com.example.mealplannerapplication.view.activity1;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.view.activity2.activity2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class spalshScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
       new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                Intent i = new Intent(spalshScreen.this, activity1.class);
                startActivity(i);
                finish();
              }
        }, 3000);

//        FirebaseAuth  mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//           Toast.makeText(this, R.string.welcome_back, Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, activity2.class);
//
//            startActivity(intent);
//        }



    }



}