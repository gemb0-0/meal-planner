package com.example.mealplannerapplication.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.navigation.NavController;
import com.example.mealplannerapplication.view.MealOfTheDayDirections;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.Meal;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.presenter.mealOfTheDayPresenter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.List;


public class MealOfTheDay extends Fragment implements MealOfTheDayInterface {
    Repository repo;
    mealOfTheDayPresenter presenter;
    TextView title,id;
    ImageView mealImage;
    ChipGroup chipGroup;
    ConstraintLayout constraintLayout, mealV;
    String mealId;
    public MealOfTheDay() {}

    public static MealOfTheDay newInstance(String param1, String param2) {
        MealOfTheDay fragment = new MealOfTheDay();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mealoftheday, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repo = Repository.getInstance();
        presenter = new mealOfTheDayPresenter(this, repo);
        presenter.getMealOfTheDay();
        title = view.findViewById(R.id.txtV2);
        mealImage = view.findViewById(R.id.imgV);
        constraintLayout = view.findViewById(R.id.mealsOftheDay);
        ChipGroup chipGroup = view.findViewById(R.id.chipgroup);
        mealV = view.findViewById(R.id.mealV);
        chipGroupListener(chipGroup);

    }

    private void chipGroupListener(ChipGroup chipGroup) {
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId != View.NO_ID) {
                    Chip selectedChip = chipGroup.findViewById(checkedId);
                    String chipText = selectedChip.getText().toString();
                    constraintLayout.setVisibility(View.VISIBLE);
                    Log.d("SelectedChip", "Selected chip: " + chipText);
                } else {

                    constraintLayout.setVisibility(View.GONE);
                    Log.d("SelectedChip", "No chip selected");
                }
            }
        });
    }

    @Override
    public void onSuccess(List<Meal> MealList) {
        Log.i("Meal of the dayyyyyyyyyy", MealList.toString());
        // title.setText(mealoftheday);
        mealId =  MealList.get(0).getIdMeal();
        title.setText(MealList.get(0).getStrMeal());
        Glide.with(getContext()).load(MealList.get(0).getStrMealThumb())
                .apply(new RequestOptions().transform(new RoundedCorners(90))).override(380, 440).centerCrop()
                .placeholder(mealImage.getDrawable())
                .into(mealImage);

        mealV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MealOfTheDayDirections.ActionMealofthedayToMealDetailView action = MealOfTheDayDirections.actionMealofthedayToMealDetailView(mealId);
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    @Override
    public void onFailure(Throwable t) {
        Log.i("Error", t.getMessage());
    }


}