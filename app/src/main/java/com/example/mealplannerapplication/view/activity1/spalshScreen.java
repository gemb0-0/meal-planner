package com.example.mealplannerapplication.view.activity1;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mealplannerapplication.R;

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
//       new Handler().postDelayed(new Runnable() {
//              @Override
//              public void run() {
//                Intent i = new Intent(spalshScreen.this, mainMenu.class);
//                startActivity(i);
//                finish();
//              }
//        }, 3000);

                        Intent i = new Intent(spalshScreen.this, activity1Scr.class);
                startActivity(i);

    }



}