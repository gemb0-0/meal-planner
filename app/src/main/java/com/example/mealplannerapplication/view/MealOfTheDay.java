package com.example.mealplannerapplication.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.Meal;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.presenter.mealOfTheDayPresenter;
import com.google.android.material.chip.ChipGroup;
import java.util.List;
import java.util.Map;


public class MealOfTheDay extends Fragment implements MealOfTheDayInterface, viewPlanInterface {

    mealOfTheDayPresenter presenter;
    TextView title, txtBreakfast, txtLunch, txtDinner;
    ImageView mealImage,imgBreakfast,imgLunch,imgDinner;
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

        presenter = new mealOfTheDayPresenter(this,   this,Repository.getInstance(getContext()));
        presenter.getMealOfTheDay();
        title = view.findViewById(R.id.txtV2);
        mealImage = view.findViewById(R.id.imgV);
        constraintLayout = view.findViewById(R.id.mealsOftheDay);
        ChipGroup chipGroup = view.findViewById(R.id.daysChips);
        mealV = view.findViewById(R.id.mealV);
        chipGroupListener(chipGroup);
        imgBreakfast = view.findViewById(R.id.imgBreakfast);
        imgLunch = view.findViewById(R.id.imgLunch);
        imgDinner = view.findViewById(R.id.imgDinner);
        txtBreakfast = view.findViewById(R.id.txtBreakfast);
        txtLunch = view.findViewById(R.id.txtLunch);
        txtDinner = view.findViewById(R.id.txtDinner);

    }

    private void chipGroupListener(ChipGroup chipGroup) {
        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                if(checkedIds.size() > 0){
                    Clrscr();
                    String Day = getResources().getResourceEntryName(checkedIds.get(0));
                    presenter.getMealsForTheDay(Day);
                    constraintLayout.setVisibility(View.VISIBLE);
                    Log.d("SelectedChip", "Selected chip: " + Day);
                } else {
                    constraintLayout.setVisibility(View.GONE);
                    Log.d("SelectedChip", "No chip selected");
                    Clrscr();
                }
            }
        });

    }

    private void Clrscr() {
        imgBreakfast.setImageResource(0);
        imgLunch.setImageResource(0);
        imgDinner.setImageResource(0);
        txtBreakfast.setText("");
        txtLunch.setText("");
        txtDinner.setText("");
    }


    @Override
    public void onSuccess(List<Meal> MealList) {
        mealId =  MealList.get(0).getIdMeal();
        title.setText(MealList.get(0).getStrMeal());
        Glide.with(getContext()).load(MealList.get(0).getStrMealThumb())
                .apply(new RequestOptions().transform(new RoundedCorners(90))).override(380, 440).centerCrop()
                .placeholder(mealImage.getDrawable())
                .into(mealImage);
        mealV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MealOfTheDayDirections.ActionMealofthedayToMealDetailView action = MealOfTheDayDirections.actionMealofthedayToMealDetailView(mealId, false);
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    @Override
    public void onFailure(Throwable t) {
        Log.i("Error", t.getMessage());
    }


    @Override
    public void getMealsForTheDay(Map<String, Meal> mealsMap) {

        requireActivity().runOnUiThread(() -> {
            if (mealsMap.containsKey("BreakFast")) {
                txtBreakfast.setText("Breakfast");
                Glide.with(requireContext())
                        .load(mealsMap.get("BreakFast").getStrMealThumb())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(new RequestOptions())
                        .centerCrop()
                        .placeholder(imgBreakfast.getDrawable())
                        .into(imgBreakfast);
            }


            if (mealsMap.containsKey("Launch")) {
                txtLunch.setText("Lunch");
                Glide.with(requireContext())
                        .load(mealsMap.get("Launch").getStrMealThumb())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(new RequestOptions())
                        .centerCrop()
                        .placeholder(imgLunch.getDrawable())
                        .into(imgLunch);
            }

            // Update image for dinner if it exists
            if (mealsMap.containsKey("Dinner")) {
                txtDinner.setText("Dinner");
                Glide.with(requireContext())
                        .load(mealsMap.get("Dinner").getStrMealThumb())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(new RequestOptions())
                        .centerCrop()
                        .placeholder(imgDinner.getDrawable())
                        .into(imgDinner);
            }
        });
    }
        imgBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MealOfTheDayDirections.ActionMealofthedayToMealDetailView action = MealOfTheDayDirections.actionMealofthedayToMealDetailView( mealsMap.get("BreakFast").getIdMeal(), true);
                Navigation.findNavController(v).navigate(action);
            }
        });

        imgLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MealOfTheDayDirections.ActionMealofthedayToMealDetailView action = MealOfTheDayDirections.actionMealofthedayToMealDetailView( mealsMap.get("Launch").getIdMeal(), true);
                Navigation.findNavController(v).navigate(action);
            }
        });
        imgDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MealOfTheDayDirections.ActionMealofthedayToMealDetailView action = MealOfTheDayDirections.actionMealofthedayToMealDetailView( mealsMap.get("Dinner").getIdMeal(), true);
                Navigation.findNavController(v).navigate(action);
            }
        });


    @Override
    public void errorGettingSchedule(Throwable t) {
       txtLunch.setText("No meals found");

        Log.i("Error", t.getMessage());
    }
}