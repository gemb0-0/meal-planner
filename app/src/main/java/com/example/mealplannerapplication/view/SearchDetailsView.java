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
import com.example.mealplannerapplication.model.Pojos.SingleRegionMeals;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.presenter.searchDetailPresenter;

import java.util.List;


public class SearchDetailsView extends Fragment implements SearchDetailsInterface , SearchDetailAdapterCallback {
    RecyclerView recyclerView;
    String id,type;
    searchDetailPresenter presenter;
    public SearchDetailsView() {
    }

    public static SearchDetailsView newInstance(String param1, String param2) {
        SearchDetailsView fragment = new SearchDetailsView();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        id =getArguments().getString("id");
        type = getArguments().getString("type");
        //if there's a problem with the above use this one
        // id = SearchDetailViewArgs.fromBundle(getArguments()).getId();

        // System.out.println(+ "idddddddddddddd");
        return inflater.inflate(R.layout.fragment_search_details_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.searchDetailRecylerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

        presenter = new searchDetailPresenter(Repository.getInstance(getContext()),this);

       if(type.equals("r"))
        presenter.getMealsByRegion(id);
       else if (type.equals("i"))
           presenter.getMealsByIngredient(id);
       else if (type.equals("c"))
           presenter.getMealsByCategory(id);

    }

    @Override
    public void showData(List<SingleRegionMeals> meals) {
        searchDetailsAdapter adapter = new searchDetailsAdapter(this,meals);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMealClicked(String mealId) {
        SearchDetailsViewDirections.ActionSearchDetailsViewToMealDetailView action = SearchDetailsViewDirections.actionSearchDetailsViewToMealDetailView(mealId,false);
        Navigation.findNavController(getView()).navigate(action);




    }
}