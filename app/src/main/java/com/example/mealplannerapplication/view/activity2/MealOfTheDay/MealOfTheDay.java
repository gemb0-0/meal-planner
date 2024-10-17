package com.example.mealplannerapplication.view.activity2.MealOfTheDay;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.presenter.MealOfTheDayPresenter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MealOfTheDay extends Fragment implements IMealOfTheDay, ViewPlanInterface {

    MealOfTheDayPresenter presenter;
    TextView title, txtBreakfast, txtLunch, txtDinner;
    ImageView mealImage, imgBreakfast, imgLunch, imgDinner;
    FloatingActionButton deleteBreakfast, deleteLunch, deleteDinner;
    ChipGroup chipGroup;
    ConstraintLayout constraintLayout, mealV;
    String mealId;
    RecyclerView countiresRecylerView;
    Chip prev = null;
    boolean guest;

    public MealOfTheDay() {
    }

    public static MealOfTheDay newInstance(String param1, String param2) {
        MealOfTheDay fragment = new MealOfTheDay();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("guest", MODE_PRIVATE);
        guest = sharedPreferences.getBoolean("guest", false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mealoftheday, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new MealOfTheDayPresenter(this, this, Repository.getInstance(getContext()));
        presenter.getMealOfTheDay();
        title = view.findViewById(R.id.txtV2);
        mealImage = view.findViewById(R.id.imgV);
        constraintLayout = view.findViewById(R.id.mealsOftheDay);
        ChipGroup chipGroup = view.findViewById(R.id.daysChips);
        mealV = view.findViewById(R.id.mealV);
        chipGroupListener(chipGroup);

        countiresRecylerView = view.findViewById(R.id.countriesRV);
        if (guest) {
            chipGroup.setVisibility(View.GONE);
            TextView txtV = view.findViewById(R.id.plantxtV);
            txtV.setText(R.string.please_login_to_make_or_view_meal_plans);
        } else {
            imgBreakfast = view.findViewById(R.id.imgBreakfast);
            imgLunch = view.findViewById(R.id.imgLunch);
            imgDinner = view.findViewById(R.id.imgDinner);
            deleteBreakfast = view.findViewById(R.id.deleteBreakfast);
            deleteLunch = view.findViewById(R.id.deleteLunch);
            deleteDinner = view.findViewById(R.id.deleteDinner);
            txtBreakfast = view.findViewById(R.id.txtBreakfast);
            txtLunch = view.findViewById(R.id.txtLunch);
            txtDinner = view.findViewById(R.id.txtDinner);
        }
    }


    private void chipGroupListener(ChipGroup chipGroup) {
        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {

                if (checkedIds.size() > 0) {
                    Chip chip = chipGroup.findViewById(checkedIds.get(0));
                    if (chip != null) {
                        Clrscr();
                        chip.setChipBackgroundColorResource(R.color.primary_varient);

                        if (prev != null && prev != chip) {
                            prev.setChipBackgroundColorResource(R.color.chip_transparent);
                            prev.setChipStrokeWidth(3);
                        }
                        prev = chip;
                        String Day = getResources().getResourceEntryName(checkedIds.get(0));
                        presenter.getSchedule(Day);
                        constraintLayout.setVisibility(View.VISIBLE);

                    }
                } else {
                    if (prev != null) {
                        prev.setChipBackgroundColorResource(R.color.chip_transparent);
                        prev.setChipStrokeWidth(3);
                    }
                    constraintLayout.setVisibility(View.GONE);
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
        deleteBreakfast.setVisibility(View.GONE);
        deleteLunch.setVisibility(View.GONE);
        deleteDinner.setVisibility(View.GONE);
    }

    private void ClrMeal(String meal) {
        switch (meal) {
            case "Breakfast":
                imgBreakfast.setImageResource(0);
                txtBreakfast.setText("");
                deleteBreakfast.setVisibility(View.GONE);
                break;
            case "Lunch":
                imgLunch.setImageResource(0);
                txtLunch.setText("");
                deleteLunch.setVisibility(View.GONE);
                break;
            case "Dinner":
                imgDinner.setImageResource(0);
                txtDinner.setText("");
                deleteDinner.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onSuccess(List<Meal> MealList) {
        mealId = MealList.get(0).getIdMeal();
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
        Log.i(getString(R.string.error), t.getMessage());
    }


    @Override
    public void getMealsForTheDay(List<Meal> todaysMeals) {
        //loop through and set every one

        for (Meal meal : todaysMeals) {
            switch (meal.getMeal()) {
                case "Breakfast":
                    requireActivity().runOnUiThread(() -> {
                        deleteBreakfast.setVisibility(View.VISIBLE);
                        txtBreakfast.setText(R.string.breakfast);
                        Glide.with(requireContext())
                                .load(meal.getStrMealThumb())
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .apply(new RequestOptions())
                                .centerCrop()
                                .placeholder(imgBreakfast.getDrawable())
                                .into(imgBreakfast);

                        imgBreakfast.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MealOfTheDayDirections.ActionMealofthedayToMealDetailView action = MealOfTheDayDirections.actionMealofthedayToMealDetailView(meal.getIdMeal(), true);
                                Navigation.findNavController(v).navigate(action);
                            }
                        });

                        deleteBreakfast.setOnClickListener(v -> {
                            ShowDialog("Breakfast", meal.getIdMeal());
                        });

                    });
                    break;
                case "Lunch":
                    requireActivity().runOnUiThread(() -> {
                        deleteLunch.setVisibility(View.VISIBLE);
                        txtLunch.setText(R.string.lunch);
                        Glide.with(requireContext())
                                .load(meal.getStrMealThumb())
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .apply(new RequestOptions())
                                .centerCrop()
                                .placeholder(imgLunch.getDrawable())
                                .into(imgLunch);

                        imgLunch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MealOfTheDayDirections.ActionMealofthedayToMealDetailView action = MealOfTheDayDirections.actionMealofthedayToMealDetailView(meal.getIdMeal(), true);
                                Navigation.findNavController(v).navigate(action);
                            }
                        });

                        deleteLunch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ShowDialog("Lunch", meal.getIdMeal());
                            }
                        });

                    });
                    break;
                case "Dinner":
                    requireActivity().runOnUiThread(() -> {
                        deleteDinner.setVisibility(View.VISIBLE);
                        txtDinner.setText(R.string.dinner);
                        Glide.with(requireContext())
                                .load(meal.getStrMealThumb())
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .apply(new RequestOptions())
                                .centerCrop()
                                .placeholder(imgDinner.getDrawable())
                                .into(imgDinner);

                        imgDinner.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MealOfTheDayDirections.ActionMealofthedayToMealDetailView action = MealOfTheDayDirections.actionMealofthedayToMealDetailView(meal.getIdMeal(), true);
                                Navigation.findNavController(v).navigate(action);
                            }
                        });

                        deleteDinner.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ShowDialog("Dinner", meal.getIdMeal());
                            }
                        });

                    });
                    break;
            }
        }

    }

    @Override
    public void errorGettingSchedule(Throwable t) {
        getActivity().runOnUiThread(() -> {
            txtLunch.setText(R.string.no_meals_found_err);
        });
        Log.i(String.valueOf(R.string.error), t.getMessage());
    }


    private void ShowDialog(String meal, String id) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alert, null);
        TextView text = alertLayout.findViewById(R.id.alertTitle);
        Button okBtn = alertLayout.findViewById(R.id.okBtn);
        Button cancelBtn = alertLayout.findViewById(R.id.cancelBtn);
        text.setText("Remove from the plan?");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(alertLayout);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        okBtn.setOnClickListener(v -> {
            presenter.deleteMealFromPlan(id);
            ClrMeal(meal);
            dialog.dismiss();
        });

        cancelBtn.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}