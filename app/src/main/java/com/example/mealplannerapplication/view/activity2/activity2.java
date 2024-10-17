package com.example.mealplannerapplication.view.activity2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.view.activity1.activity1;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class activity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);




        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.mealDetailView || destination.getId() == R.id.searchDetailsView) {
                bottomNavigationView.setVisibility(View.GONE);
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);

            }
            if (destination.getId() == R.id.profile && sharedPrefData()) {
                Intent intent = new Intent(activity2.this, activity1.class);
                startActivity(intent);
                finish();

            }
        });


    }

    private Boolean sharedPrefData() {
        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.guest), MODE_PRIVATE);
        return sharedPreferences.getBoolean(getString(R.string.guest), false);
    }
}