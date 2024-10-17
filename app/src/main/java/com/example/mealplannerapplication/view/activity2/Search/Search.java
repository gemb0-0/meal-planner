package com.example.mealplannerapplication.view.activity2.Search;

import android.graphics.Rect;
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
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Ingredients;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Meal;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealInfo;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Regions;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Category;
import com.example.mealplannerapplication.presenter.SearchPresenter;
import com.example.mealplannerapplication.view.activity2.Adapters.CategoriesAdapterCallback;
import com.example.mealplannerapplication.view.activity2.Adapters.FavouriteAdapter;
import com.example.mealplannerapplication.view.activity2.Adapters.IngradientsAdapterCallback;
import com.example.mealplannerapplication.view.activity2.Adapters.catAdapter;
import com.example.mealplannerapplication.view.activity2.Favourite.FavouriteDetailView;
import com.example.mealplannerapplication.view.activity2.Adapters.RegionAdapterCallback;
import com.example.mealplannerapplication.view.activity2.Adapters.RegionAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Search extends Fragment implements SearchInterface, RegionAdapterCallback, IngradientsAdapterCallback, CategoriesAdapterCallback, FavouriteDetailView {

    SearchView searchView;
    RecyclerView CategoryrecyclerView, RegionrecyclerView, IngredientsrecyclerView, MealrecyclerView;
    SearchPresenter presenter;
    SearchView searchBox;
    BottomNavigationView bottomNavigationView;
    List<List<Object>> motherList;
    LinearLayout mealLinearLayout;

    public Search() {
        // Required empty public constructor
    }

    public static Search newInstance(String param1, String param2) {
        Search fragment = new Search();
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
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);

        searchBox.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                searchBox.getWindowVisibleDisplayFrame(r); // size of the search box area
                int screenHeight = searchBox.getRootView().getHeight();
                int keypadHeight = screenHeight - (r.bottom - r.top);

                if (keypadHeight > 500) { // Detect if the keyboard is visible
                    bottomNavigationView.setVisibility(View.GONE); // Hide navigation bar
                }
            }
        });


    }

    private void SearchBoxClick() {
        // bottomNavigationView.setVisibility(View.GONE);
        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newText.replaceAll("\\s+", ""); //removes white spaces
                if (!newText.isEmpty()) {
                    mealLinearLayout.setVisibility(View.VISIBLE);  //this contain the header meal and see more (hidden for now) in a horizontal linear layout
                    MealrecyclerView.setVisibility(View.VISIBLE); //this contains the recycler view
                    presenter.searchMealName(newText);
                    UpdateSearchResults(newText);
                } else {
                    mealLinearLayout.setVisibility(View.INVISIBLE);
                    MealrecyclerView.setVisibility(View.INVISIBLE);
                }
                return true;
            }


        });

    }


    private void UpdateSearchResults(String newText) {

        if (motherList != null) {
            for (List<Object> list : motherList) {
                FilterSearch(list, newText);
            }
        }
    }

    private void FilterSearch(List list, String newText) {
        List<Category> list1 = new ArrayList<>();
        List<Ingredients> list2 = new ArrayList<>();
        List<Regions> list3 = new ArrayList<>();

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

            if (!list1.isEmpty()) {
                showCategoryData(list1);
            }

            if (!list2.isEmpty()) {
                showIngredientsData(list2);
            }
            if (!list3.isEmpty()) {
                showRegionData(list3);
            }
        }


    }

    @Override
    public void showCategoryData(List<Category> categories) {

        catAdapter adapter = new catAdapter(categories, null, this, this);
        CategoryrecyclerView.setAdapter(adapter);
    }


    @Override
    public void showRegionData(List<Regions> regions) {
        RegionAdapter adapter = new RegionAdapter(regions, this);
        RegionrecyclerView.setAdapter(adapter);
    }

    @Override
    public void showIngredientsData(List<Ingredients> ingredients) {
        catAdapter adapter = new catAdapter(null, ingredients, this, this);
        IngredientsrecyclerView.setAdapter(adapter);
    }

    @Override
    public void onError(String message) {
        Log.i("Error", message);
    }

    @Override
    public void SearchData(List<List<Object>> ListOfAllLists) {
        motherList = ListOfAllLists;

    }

    @Override
    public void showMealData(List<MealInfo> mealDetail) {
       FavouriteAdapter adapter = new FavouriteAdapter(mealDetail, null, this);
        MealrecyclerView.setAdapter(adapter);
    }

    @Override
    public void onRegionClick(String regionName) {

        SearchDirections.ActionSearchToSearchDetailsView action = SearchDirections.actionSearchToSearchDetailsView(regionName, "r");
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void onIngredientClicked(String ingredient) {
        SearchDirections.ActionSearchToSearchDetailsView action = SearchDirections.actionSearchToSearchDetailsView(ingredient, "i");
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void onCategoryClicked(String category) {
        SearchDirections.ActionSearchToSearchDetailsView action = SearchDirections.actionSearchToSearchDetailsView(category, "c");
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void getMealDetail(String mealId) {
        SearchDirections.ActionSearchToMealDetailView action = SearchDirections.actionSearchToMealDetailView(mealId, false);
        Navigation.findNavController(getView()).navigate(action);

    }
}