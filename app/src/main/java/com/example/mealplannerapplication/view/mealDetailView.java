package com.example.mealplannerapplication.view;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import static java.lang.String.valueOf;

import android.animation.ObjectAnimator;
import android.net.ConnectivityManager;
import android.net.Network;
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
import com.bumptech.glide.request.RequestOptions;
import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.Meal;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.presenter.mealDetailPresenter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class mealDetailView extends Fragment implements mealDetailViewInterface {

mealDetailPresenter presenter= new mealDetailPresenter( this, Repository.getInstance(getContext()));
ImageView mealImage;
TextView mealName, mealArea, mealInstructions;
RecyclerView mealIngredientsList;
    private ingrediantsAdapter adapter;
    String mealId;
    WebView webView;
ScrollView scrollView;
FloatingActionButton btn1,btn2,btn3;
Boolean isOnline;
    public mealDetailView() {}

    public static mealDetailView newInstance(String param1, String param2) {
        mealDetailView fragment = new mealDetailView();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         mealId = mealDetailViewArgs.fromBundle(getArguments()).getMealId();
        ConnectivityManager connectivityManager = getSystemService(getContext() ,ConnectivityManager.class);
        Network currentNetwork = connectivityManager.getActiveNetwork();
        if(currentNetwork!=null)
            isOnline = false;

        return inflater.inflate(R.layout.fragment_meal_detail_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        if(isOnline ==false)
            presenter.getMealDetail(mealId);
        else
        {
            presenter.getFromDb(mealId);
        }
        mealImage = view.findViewById(R.id.imgV);
        mealName = view.findViewById(R.id.mealName);
        mealArea = view.findViewById(R.id.originCountrytxtV);
        mealIngredientsList = view.findViewById(R.id.ingredientList);
        mealInstructions = view.findViewById(R.id.instructions);
        webView = view.findViewById(R.id.webView);
        btn1 = view.findViewById(R.id.floatingActionButton);
        btn2 = view.findViewById(R.id.floatingActionButton2);
        btn3 = view.findViewById(R.id.floatingActionButton3);
        mealIngredientsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        scrollView = view.findViewById(R.id.mealDetailscrollV);
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                onScroll();
            }
        });

        btn1.setOnClickListener(view1 -> {
            btn1.animate().rotationBy(180).setDuration(200);
            ObjectAnimator.ofFloat(scrollView, "translationY", 0).setDuration(200).start();

            View fadedScr = view.findViewById(R.id.view);
            if(btn2.getVisibility() == View.VISIBLE|| isOnline ==true){
                hideBtns(fadedScr);

            }
            else {
                showBtns(fadedScr);
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


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.saveMeal(mealId);
                Toast.makeText(getContext(), "Meal Saved", Toast.LENGTH_SHORT).show();
                hideBtns(fadedScr);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop_up(view);
            }
        });

        fadedScr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideBtns(fadedScr);
            }
        });
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


    private void onScroll() {
        if (scrollView.getScrollY() == 0) {
            btn1.animate().setDuration(250).translationY(0).alpha(1f).start();
        } else if (isOnline) {
            btn1.animate().setDuration(250).alpha(0f).withEndAction(() -> btn1.setVisibility(View.GONE)).start();
        } else {
            btn1.animate().setDuration(200).translationY(500).alpha(0f).withEndAction(() -> btn1.setVisibility(View.GONE)).start();
        }
    }

    @Override
    public void onSuccess(List<Meal> mealDetail) {
        Meal detail = mealDetail.get(0);

            mealName.setText(detail.getStrMeal());
        mealInstructions.setText(detail.getStrInstructions());
        List<String> ingredients = detail.getAllIngredients();
        List<String> measurements = detail.getAllMeasures();
        if(isOnline ==false)
            OnlineStuff(detail, ingredients, measurements);
        else {
            adapter = new ingrediantsAdapter(ingredients, measurements, isOnline);
            mealIngredientsList.setAdapter(adapter);
        }

        //add icons later
        if(detail.getStrArea()!=null && detail.getStrCategory()!=null){
          //  mealArea.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_location_on_24,0,0,0);
            mealArea.setText("Nationality : "+ detail.getStrArea()+" \n");
          //  mealArea.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_category_24,0,0,0);
            mealArea.append("Category : "+ detail.getStrCategory());
        }

        else if (detail.getStrArea()!=null){
           // mealArea.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_location_on_24,0,0,0);
            mealArea.setText("Nationality : "+ detail.getStrArea());
        }

        else if (detail.getStrCategory()!=null) {
           // mealArea.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_category_24, 0, 0, 0);
            mealArea.setText("Category : " + detail.getStrCategory());
        }

    }

    private void OnlineStuff(Meal detail, List<String> ingredients, List<String> measurements) {
        Glide.with(getContext()).load(detail.getStrMealThumb()).apply(new RequestOptions()).centerCrop().placeholder(mealImage.getDrawable()).into(mealImage);
        String videoId = detail.getStrYoutube().split("v=")[1];
        String videoHtml = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" frameborder=\"0\" allowfullscreen></iframe>";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadDataWithBaseURL(null, videoHtml, "text/html", "UTF-8", null);
        adapter = new ingrediantsAdapter(ingredients, measurements, true);
        mealIngredientsList.setAdapter(adapter);
        adapter = new ingrediantsAdapter(ingredients, measurements, isOnline);
        mealIngredientsList.setAdapter(adapter);
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
    }




    public void showPop_up (View view){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup, null);
        int width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setAnimationStyle(R.style.popupMenuAni);
        Button b= popupView.findViewById(R.id.saveBtn);
        ChipGroup dayChipGroup = popupView.findViewById(R.id.daysChips);
        ChipGroup MealChipGroup = popupView.findViewById(R.id.mealChips);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dayChipGroup.getCheckedChipId()==-1 || MealChipGroup.getCheckedChipId()==-1)
                    Toast.makeText(getContext(), "Please select a day and a meal type", Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(getContext(), "Meal Saved", Toast.LENGTH_SHORT).show();
                     String checkedChipDay = ((Chip) popupView.findViewById(dayChipGroup.getCheckedChipId())).getText().toString();
                     String checkedChipMeal = ((Chip) popupView.findViewById(MealChipGroup.getCheckedChipId())).getText().toString();
                       presenter.saveMealToPlan(mealId, checkedChipDay,checkedChipMeal);

                    popupWindow.dismiss();
                }
            }
        });
        hideBtns(view);
    }

}