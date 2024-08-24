package com.example.mealplannerapplication.view.activity2.Search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Ingredients;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Regions;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Category;
import com.example.mealplannerapplication.presenter.SearchPresenter;
import com.example.mealplannerapplication.view.activity2.adapters.CategoriesAdapterCallback;
import com.example.mealplannerapplication.view.activity2.adapters.FavouriteAdapter;
import com.example.mealplannerapplication.view.activity2.adapters.IngradientsAdapterCallback;
import com.example.mealplannerapplication.view.activity2.adapters.catAdapter;
import com.example.mealplannerapplication.view.activity2.Favourite.favDetailView;
import com.example.mealplannerapplication.view.activity2.adapters.RegionAdapterCallback;
import com.example.mealplannerapplication.view.activity2.adapters.regionAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class search extends Fragment implements SearchInterface, RegionAdapterCallback, IngradientsAdapterCallback, CategoriesAdapterCallback, favDetailView {

  SearchView searchView;
  RecyclerView CategoryrecyclerView, RegionrecyclerView, IngredientsrecyclerView, MealrecyclerView;
  SearchPresenter presenter;
    SearchView searchBox;
    BottomNavigationView bottomNavigationView;
    List<List<Object>> motherList;
    LinearLayout mealLinearLayout;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.searchBox);
        MealrecyclerView = view.findViewById(R.id.MealrecyclerView);
        CategoryrecyclerView = view.findViewById(R.id.recyclerView1);
        RegionrecyclerView = view.findViewById(R.id.recyclerView3);
        IngredientsrecyclerView = view.findViewById(R.id.recyclerView2);
        MealrecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
        CategoryrecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
        RegionrecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
        IngredientsrecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
        searchBox = view.findViewById(R.id.searchBox);
        mealLinearLayout = view.findViewById(R.id.mealLinearLayout);
        presenter = new SearchPresenter(this, Repository.getInstance(getContext()));

        presenter.getDataForSearch();
        SearchBoxClick();
//        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
//
//        ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Rect r = new Rect();
//                bottomNavigationView.getWindowVisibleDisplayFrame(r); // size of the screen
//                int screenHeight = bottomNavigationView.getRootView().getHeight();
//                int keypadHeight = screenHeight - (r.bottom - r.top);
//
//                if (keypadHeight > 500) { // Detect if the keyboard is visible
//                    bottomNavigationView.setVisibility(View.GONE);
//                } else {
//                    bottomNavigationView.setVisibility(View.VISIBLE);
//                }
//            }
//        };
//
//        bottomNavigationView.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);



    }

    private void SearchBoxClick() {

        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText.replaceAll("\\s+", "");
                if(newText.length()>0) {
                    mealLinearLayout.setVisibility(View.VISIBLE);
                    MealrecyclerView.setVisibility(View.VISIBLE);
                    presenter.searchMealName(newText);
                    UpdateSearchResults(newText);
                }
                else {
                    mealLinearLayout.setVisibility(View.INVISIBLE);
                    MealrecyclerView.setVisibility(View.INVISIBLE);
                }
                return true;
            }


        });

    }



    private void UpdateSearchResults(String newText) {

       if(motherList!=null) {
           for (List<Object> list : motherList) {
               FilterSearch( list ,newText);
           }
       }
    }

    private void FilterSearch(List list, String newText) {
            List<Category>list1=new ArrayList<>();
            List<Ingredients>list2=new ArrayList<>();
            List<Regions>list3=new ArrayList<>();

            for (Object object : list) {
                if (object instanceof Category) {
                    if (((Category) object).getStrCategory().toLowerCase().contains(newText.toLowerCase())) {
                        list1.add((Category) object);
                    }
                } else if (object instanceof Ingredients) {
                    if (((Ingredients) object).getStrIngredient().toLowerCase().contains(newText.toLowerCase())) {
                        list2.add((Ingredients) object);
                    }
                } else if (object instanceof Regions) {
                    if (((Regions) object).getStrArea().toLowerCase().contains(newText.toLowerCase())) {
                        list3.add((Regions) object);
                    }
                }

                if(!list1.isEmpty()){
                    showCategoryData(list1);
                }

                if(!list2.isEmpty()){
                    showIngredientsData(list2);
                }
                if(!list3.isEmpty()){
                showRegionData(list3);
                                   }
            }


    }

    @Override
    public void showCategoryData(List<Category> categories) {

        catAdapter adapter = new catAdapter(categories,null,this,this);
        CategoryrecyclerView.setAdapter(adapter);
    }


    @Override
    public void showRegionData(List<Regions> regions) {
        regionAdapter adapter = new regionAdapter(regions,this);
        RegionrecyclerView.setAdapter(adapter);
    }

    @Override
    public void showIngredientsData(List<Ingredients> ingredients) {
        catAdapter adapter = new catAdapter(null,ingredients,this,this);
        IngredientsrecyclerView.setAdapter(adapter);
    }

    @Override
    public void onError(String message) {
        Log.i("Error", message);
    }

    @Override
    public void SearchData(List<List<Object>> ListOfAllLists) {
        motherList=ListOfAllLists;

    }

    @Override
    public void showMealData(List<Meal> mealDetail) {
        FavouriteAdapter adapter = new FavouriteAdapter(mealDetail, null, this);
        MealrecyclerView.setAdapter(adapter);
    }

    @Override
    public void onRegionClick(String regionName) {

        searchDirections.ActionSearchToSearchDetailsView action = searchDirections.actionSearchToSearchDetailsView(regionName,"r");
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void onIngredientClicked(String ingredient) {
        searchDirections.ActionSearchToSearchDetailsView action = searchDirections.actionSearchToSearchDetailsView(ingredient,"i");
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void onCategoryClicked(String category) {
        searchDirections.ActionSearchToSearchDetailsView action = searchDirections.actionSearchToSearchDetailsView(category,"c");
        Navigation.findNavController(getView()).navigate(action);
    }


    @Override
    public void getMealDetail(String mealId) {
        searchDirections.ActionSearchToMealDetailView action = searchDirections.actionSearchToMealDetailView(mealId,false);
        Navigation.findNavController(getView()).navigate(action);


    }
}