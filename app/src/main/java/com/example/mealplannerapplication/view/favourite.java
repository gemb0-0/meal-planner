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
import android.widget.Toast;

import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.Pojos.Meal;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.presenter.favouritePresenter;

import java.util.List;


public class favourite extends Fragment implements FavouriteInterface, deleteFavCallback,favDetailView{

  RecyclerView recyclerView;
    favouritePresenter presenter;
    FavouriteAdapter adapter;


    public favourite() {
        // Required empty public constructor
    }

    public static favourite newInstance(String param1, String param2) {
        favourite fragment = new favourite();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.favouriteRecyler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        presenter= new favouritePresenter(Repository.getInstance(getContext()),this);
       presenter.retriveFavourite();

    }

    @Override
    public void showFavProducts(List<Meal> mealDetail) {
         adapter = new FavouriteAdapter(mealDetail,this,this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showErrMsg() {
        Toast.makeText(getContext(), "No Favourites Found", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteFav(Meal meal) {
        presenter.deleteFav(meal);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getMealDetail(String mealId) {
       favouriteDirections.ActionFavouriteToMealDetailView action = favouriteDirections.actionFavouriteToMealDetailView(mealId,true );
        Navigation.findNavController(getView()).navigate(action);

    }
}