package com.example.mealplannerapplication.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.model.Pojos.Category;
import com.example.mealplannerapplication.presenter.SearchPresenter;

import java.util.List;

public class search extends Fragment implements SearchInterface{

  SearchView searchView;
  RecyclerView CategoryrecyclerView;
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
        if (getArguments() != null) {

        }
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
        CategoryrecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));

        presenter = new SearchPresenter(this, Repository.getInstance(getContext()));
        presenter.getDataForSearch();

    }

    @Override
    public void showData(List<Category> categories) {

        catAdapter adapter = new catAdapter(categories);
        CategoryrecyclerView.setAdapter(adapter);
    }

    @Override
    public void onError(String message) {
        System.out.println("errrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
    }
}