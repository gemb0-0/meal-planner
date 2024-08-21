package com.example.mealplannerapplication.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.Meal;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.presenter.mealDetailPresenter;
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

    public mealDetailView() {
        // Required empty public constructor
    }

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
         mealId = mealDetailViewArgs.fromBundle(getArguments()).getName();


        return inflater.inflate(R.layout.fragment_meal_detail_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        presenter.getMealDetail(mealId);
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
            if(btn2.getVisibility() == View.VISIBLE){
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
            btn1.animate().setDuration(250).translationY(0);
            btn1.setVisibility(View.VISIBLE);
        } else {
           btn1.animate().setDuration(200).translationY(500);
            btn1.setVisibility(View.GONE);

        }
    }

    @Override
    public void onSuccess(List<Meal> mealDetail) {
        Meal detail = mealDetail.get(0);
        mealName.setText(detail.getStrMeal());
        mealInstructions.setText(detail.getStrInstructions());
        Glide.with(getContext()).load(detail.getStrMealThumb()).apply(new RequestOptions()).centerCrop().placeholder(mealImage.getDrawable()).into(mealImage);
        String videoId = detail.getStrYoutube().split("v=")[1];
        String videoHtml = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" frameborder=\"0\" allowfullscreen></iframe>";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadDataWithBaseURL(null, videoHtml, "text/html", "UTF-8", null);
        List<String> ingredients = detail.getAllIngredients();
        List<String> measurements = detail.getAllMeasures();
        adapter = new ingrediantsAdapter(ingredients,measurements);
        mealIngredientsList.setAdapter(adapter);

        if(detail.getStrArea()!=null && detail.getStrCategory()!=null)
            mealArea.setText("Nationality : "+ detail.getStrArea()+" \nCategory : "+ detail.getStrCategory());
        else if (detail.getStrArea()!=null)
            mealArea.setText("Nationality : "+ detail.getStrArea());
        else if (detail.getStrCategory()!=null)
            mealArea.setText("Category : "+ detail.getStrCategory());
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(getContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show();
    }



}