package com.example.mealplannerapplication.view.activity2.MealDetailView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.MODE_PRIVATE;

import static java.lang.String.valueOf;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.presenter.MealDetailPresenter;
import com.example.mealplannerapplication.view.activity2.Adapters.IngredientsAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MealDetailView extends Fragment implements MealDetailViewInterface {

    MealDetailPresenter presenter = new MealDetailPresenter(this, Repository.getInstance(getContext()));
    ImageView mealImage;
    TextView mealName, mealArea, mealInstructions, mealCategory;
    RecyclerView mealIngredientsList;
    IngredientsAdapter adapter;
    String mealId;
    WebView webView;
    ScrollView scrollView;
    FloatingActionButton btn1, btn2, btn3;
    Boolean DB = false;
    Boolean guest;

    public MealDetailView() {
    }

    public static MealDetailView newInstance(String param1, String param2) {
        MealDetailView fragment = new MealDetailView();
        Bundle args = new Bundle();
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

        mealId = MealDetailViewArgs.fromBundle(getArguments()).getMealId();
        DB = MealDetailViewArgs.fromBundle(getArguments()).getDB();

//         ConnectivityManager connectivityManager = getSystemService(getContext() ,ConnectivityManager.class);
//           Network currentNetwork = connectivityManager.getActiveNetwork();
//           if(currentNetwork!=null)
//             isOnline = false;
        return inflater.inflate(R.layout.fragment_meal_detail_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!DB)
            presenter.getMealDetail(mealId);//online
        else
            presenter.getFromDb(mealId);//offline

        mealImage = view.findViewById(R.id.imgV);
        mealName = view.findViewById(R.id.mealName);
        mealArea = view.findViewById(R.id.CountrytxtV);
        mealCategory = view.findViewById(R.id.categorytxtV);
        mealIngredientsList = view.findViewById(R.id.ingredientList);
        mealIngredientsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mealInstructions = view.findViewById(R.id.instructions);
        webView = view.findViewById(R.id.webView);
        btn1 = view.findViewById(R.id.floatingActionButton);
        btn2 = view.findViewById(R.id.floatingActionButton2);
        btn3 = view.findViewById(R.id.floatingActionButton3);
        scrollView = view.findViewById(R.id.mealDetailscrollV);
        View fadedScr = view.findViewById(R.id.view);

        ScrollControls();
        Btn1Controls(fadedScr);

    }


    private void Btn1Controls(View fadedScr) {
        if (DB || guest)
            btn1.setVisibility(View.GONE);
        else {
            btn1.setOnClickListener(view1 -> {
                btn1.animate().rotationBy(180).setDuration(200);
                //    ObjectAnimator.ofFloat(scrollView, "translationY", 0).setDuration(200).start();
                if (btn2.getVisibility() == View.VISIBLE) {
                    hideBtns(fadedScr);

                } else {
                    showBtns(fadedScr);
                }
            });
        }
    }

    private void ScrollControls() {
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (scrollView.getScrollY() == 0 && !DB && !guest) {
                    btn1.setVisibility(View.VISIBLE);
                    btn1.animate().setDuration(250).translationY(0);
                    //  ObjectAnimator.ofFloat(scrollView, "translationY", 0).setDuration(200).start();
                } else
                    btn1.setVisibility(View.GONE);
            }
        });
    }


    private void showBtns(View fadedScr) {
        btn2.setVisibility(View.VISIBLE);
        btn3.setVisibility(View.VISIBLE);
        btn2.setClickable(true);
        btn3.setClickable(true);
        fadedScr.setVisibility(View.VISIBLE);
        scrollView.setOnTouchListener((v, event) -> true);
        scrollView.setFocusable(false);
        scrollView.setFocusableInTouchMode(false);


        btn2.setOnClickListener(view -> {
            presenter.saveToFavourites(mealId);
            Toast.makeText(getContext(), R.string.mealsaved, Toast.LENGTH_SHORT).show();
            hideBtns(fadedScr);
        });

        btn3.setOnClickListener(view -> {
            showPop_up(view);
            hideBtns(fadedScr);
        });

        fadedScr.setOnClickListener(view -> hideBtns(fadedScr));
    }

    private void hideBtns(View fadedScr) {
        btn2.setVisibility(View.GONE);
        btn3.setVisibility(View.GONE);
        btn2.setClickable(false);
        btn3.setClickable(false);
        fadedScr.setVisibility(View.GONE);
        scrollView.setOnTouchListener(null);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
    }


    @Override
    public void onSuccess(List<Meal> mealDetail) {
        Meal detail = mealDetail.get(0);
        requireActivity().runOnUiThread(() -> {
            mealName.setText(detail.getStrMeal());
            mealInstructions.setText(detail.getStrInstructions());
            List<String> ingredients = detail.getAllIngredients();
            List<String> measurements = detail.getAllMeasures();
            //glide dosne't save the img
            requireActivity().runOnUiThread(() -> {
                Glide.with(requireContext())
                        .load(detail.getStrMealThumb())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(new RequestOptions())
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(mealImage);
            });
            if (DB == false) {
                fromNetwork(detail);
            }
            adapter = new IngredientsAdapter(ingredients, measurements);
            mealIngredientsList.setAdapter(adapter);

            //add icons later
            if (detail.getStrArea() != null && detail.getStrCategory() != null) {
                mealArea.setText(getString(R.string.nationality) + detail.getStrArea());
                mealCategory.setText(getString(R.string.category) + detail.getStrCategory());
            } else if (detail.getStrArea() != null) {
                mealArea.setText(R.string.nationality);
                mealArea.append(detail.getStrArea());
            } else if (detail.getStrCategory() != null) {
                mealCategory.setText(R.string.category + detail.getStrCategory());
            }
        });
    }

    @Override
    public void onFailure(Throwable t) {
        //  Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
    }

    private void fromNetwork(Meal detail) {
        if (detail.getStrYoutube() == null || !detail.getStrYoutube().contains("v="))
            return;
        String videoId = detail.getStrYoutube().split("v=")[1];
        String videoHtml = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" frameborder=\"0\" allowfullscreen></iframe>";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadDataWithBaseURL(null, videoHtml, "text/html", "UTF-8", null);
    }


    public void showPop_up(View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup, null);

        int width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setAnimationStyle(R.style.popupMenuAni);

        Button b = popupView.findViewById(R.id.saveBtn);
        ChipGroup dayChipGroup = popupView.findViewById(R.id.daysChips);
        ChipGroup MealChipGroup = popupView.findViewById(R.id.mealChips);
        ControlSaveBtnPopup(b, dayChipGroup, MealChipGroup, popupView, popupWindow);
        ChipColorControl(dayChipGroup);
        ChipColorControl(MealChipGroup);


    }

    private static void ChipColorControl(ChipGroup dayChipGroup) {
        dayChipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            Chip prev = null;

            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                if (checkedIds.size() > 0) {
                    Chip chip = dayChipGroup.findViewById(checkedIds.get(0));
                    chip.setChipBackgroundColorResource(R.color.primary_varient);

                    if (prev != null && prev != chip) {
                        prev.setChipBackgroundColorResource(R.color.chip_transparent);
                        prev.setChipStrokeWidth(3);
                    }
                    prev = chip;
                } else if (prev != null) {
                    prev.setChipBackgroundColorResource(R.color.chip_transparent);
                    prev.setChipStrokeWidth(3);

                }
            }

        });
    }

    private void ControlSaveBtnPopup(Button b, ChipGroup dayChipGroup, ChipGroup MealChipGroup, View popupView, PopupWindow popupWindow) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayChipGroup.getCheckedChipId() == -1 || MealChipGroup.getCheckedChipId() == -1) {
                    Toast.makeText(getContext(), R.string.please_select_a_day_and_a_meal_type, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.mealsaved, Toast.LENGTH_SHORT).show();
                    String checkedChipDay = ((Chip) popupView.findViewById(dayChipGroup.getCheckedChipId())).getText().toString();
                    String checkedChipMeal = ((Chip) popupView.findViewById(MealChipGroup.getCheckedChipId())).getText().toString();
                    presenter.saveMealToPlan(mealId, checkedChipDay, checkedChipMeal);
                    popupWindow.dismiss();
                }
            }

        });
    }

}