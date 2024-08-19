package com.example.mealplannerapplication.view;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.Meal;
import com.example.mealplannerapplication.presenter.mealDetailPresenter;

import java.util.List;

public class mealDetailView extends Fragment implements mealDetailViewInterface {
mealDetailPresenter presenter= new mealDetailPresenter( this );
ImageView mealImage;
TextView mealName, mealCategory, mealArea, mealInstructions, mealIngredients, mealMeasurements;
RecyclerView mealIngredientsList;
    private ingrediantsAdapter adapter;
    String mealId;
    WebView webView;




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
        mealImage = view.findViewById(R.id.imgV);
        mealName = view.findViewById(R.id.mealName);
       presenter.getMealDetail(mealId);
        mealIngredientsList = view.findViewById(R.id.ingredientList);
        mealInstructions = view.findViewById(R.id.instructions);
        webView = view.findViewById(R.id.webView);
        mealIngredientsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    @Override
    public void onSuccess(List<Meal> mealDetail) {

        mealName.setText(mealDetail.get(0).getStrMeal());
        mealInstructions.setText(mealDetail.get(0).getStrInstructions());
        Glide.with(getContext()).load(mealDetail.get(0).getStrMealThumb()).apply(new RequestOptions()).centerCrop().placeholder(mealImage.getDrawable()).into(mealImage);
        String videoId = mealDetail.get(0).getStrYoutube().split("v=")[1];
        String videoHtml = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" frameborder=\"0\" allowfullscreen></iframe>";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadDataWithBaseURL(null, videoHtml, "text/html", "UTF-8", null);
        List<String> ingredients = mealDetail.get(0).getAllIngredients();
        List<String> measurements = mealDetail.get(0).getAllMeasures();
        adapter = new ingrediantsAdapter(ingredients,measurements);
        mealIngredientsList.setAdapter(adapter);
    }

    @Override
    public void onFailure(Throwable t) {
Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
    }
}