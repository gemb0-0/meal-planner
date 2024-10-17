package com.example.mealplannerapplication.view.activity2.Favourite;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealInfo;
import com.example.mealplannerapplication.model.Repository;
import com.example.mealplannerapplication.presenter.FavouritePresenter;
import com.example.mealplannerapplication.view.activity1.activity1;
import com.example.mealplannerapplication.view.activity2.Adapters.FavouriteAdapter;

import java.util.List;


public class Favourite extends Fragment implements FavouriteInterface, DeleteFavCallback, FavouriteDetailView {

    RecyclerView recyclerView;
    FavouritePresenter presenter;
    FavouriteAdapter adapter;
    boolean guest;
    public Favourite() {}

    public static Favourite newInstance(String param1, String param2) {
        Favourite fragment = new Favourite();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("guest", MODE_PRIVATE);
         guest = sharedPreferences.getBoolean("guest", false);
        if(guest){
            Toast.makeText(getContext(),"Login to view Favourites", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), activity1.class);
            startActivity(intent);
            requireActivity().finish();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //  BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        // bottomNavigationView.setVisibility(View.GONE);
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.favouriteRecyler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        presenter = new FavouritePresenter(Repository.getInstance(getContext()), this);
        presenter.retrieveFavourites();
    }


    @Override
    public void showErrMsg() {
        if (!guest)
            Toast.makeText(getContext(), R.string.no_fav, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext(), R.string.no_fav_guest, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showFavProducts(List<MealInfo> meals) {
        adapter = new FavouriteAdapter(meals, this, this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void deleteFav(MealInfo mealInfo) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alert, null);

        TextView text = alertLayout.findViewById(R.id.alertTitle);
        Button okBtn = alertLayout.findViewById(R.id.okBtn);
        Button cancelBtn = alertLayout.findViewById(R.id.cancelBtn);
        text.setText("Remove favourite?");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(alertLayout);
        builder.setCancelable(true);

        AlertDialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        okBtn.setOnClickListener(v -> {
            presenter.deleteFav(mealInfo);
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        cancelBtn.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    public void getMealDetail(String mealId) {
        FavouriteDirections.ActionFavouriteToMealDetailView action = FavouriteDirections.actionFavouriteToMealDetailView(mealId, true);
        Navigation.findNavController(getView()).navigate(action);

    }
}