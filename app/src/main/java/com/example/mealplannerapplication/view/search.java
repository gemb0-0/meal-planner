package com.example.mealplannerapplication.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.Pojos.Ingredients;
import com.example.mealplannerapplication.model.Pojos.Regions;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.model.Pojos.Category;
import com.example.mealplannerapplication.presenter.SearchPresenter;

import java.util.List;

public class search extends Fragment implements SearchInterface, RegionAdapterCallback {

  SearchView searchView;
  RecyclerView CategoryrecyclerView, RegionrecyclerView, IngredientsrecyclerView;
  SearchPresenter presenter;

    public search() {
        // Required empty public constructor
    }

    public static search newInstance(String param1, String param2) {
        search fragment = new search();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.searchBox);
        CategoryrecyclerView = view.findViewById(R.id.recyclerView1);
        RegionrecyclerView = view.findViewById(R.id.recyclerView3);
        IngredientsrecyclerView = view.findViewById(R.id.recyclerView2);
        CategoryrecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
        RegionrecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
        IngredientsrecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));


        presenter = new SearchPresenter(this, Repository.getInstance(getContext()));

        presenter.getDataForSearch();

    }

    @Override
    public void showData(List<Category> categories) {

        catAdapter adapter = new catAdapter(categories,null);
        CategoryrecyclerView.setAdapter(adapter);
    }

    @Override
    public void onError(String message) {
        System.out.println("errrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
    }

    @Override
    public void showRegionData(List<Regions> regions) {
        regionAdapter adapter = new regionAdapter(regions,this);
        RegionrecyclerView.setAdapter(adapter);
    }

    @Override
    public void showIngredients(List<Ingredients> ingredients) {
        catAdapter adapter = new catAdapter(null,ingredients);
        IngredientsrecyclerView.setAdapter(adapter);
    }

    @Override
    public void onRegionClick(String regionName) {

        searchDirections.ActionSearchToSearchDetailsView action = searchDirections.actionSearchToSearchDetailsView(regionName);
        Navigation.findNavController(getView()).navigate(action);

       // presenter.getMealsByRegion(regionName);
    }
}