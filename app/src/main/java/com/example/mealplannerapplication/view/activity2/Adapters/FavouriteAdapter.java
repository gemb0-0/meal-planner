package com.example.mealplannerapplication.view.activity2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.MealInfo;
import com.example.mealplannerapplication.view.activity2.Favourite.DeleteFavCallback;
import com.example.mealplannerapplication.view.activity2.Favourite.FavouriteDetailView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    DeleteFavCallback deletedMeal;
    com.example.mealplannerapplication.view.activity2.Favourite.FavouriteDetailView FavouriteDetailView;
    List<MealInfo> mealDetail;

    public FavouriteAdapter(List<MealInfo> mealDetail, DeleteFavCallback deletedMeal, FavouriteDetailView FavouriteDetailView) {
        this.mealDetail = mealDetail;
        this.deletedMeal = deletedMeal;
        this.FavouriteDetailView = FavouriteDetailView;
    }

    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_favourites, parent, false);
        FavouriteAdapter.ViewHolder viewHolder = new FavouriteAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, int position) {
        String name = mealDetail.get(position).getStrMeal();

        holder.title.setText(name);
        Glide.with(holder.itemView.getContext()).load(mealDetail.get(position).getStrMealThumb()).diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(new RequestOptions()).centerCrop().placeholder(holder.mealImage.getDrawable()).into(holder.mealImage);

        holder.mealImage.setOnClickListener(v -> {
            String mealId = mealDetail.get(position).getIdMeal();
            //Log.i("MealId", mealId);
            FavouriteDetailView.getMealDetail(mealId);
        });
        if (deletedMeal != null) {
            holder.deleteBtn.setOnClickListener(v ->

                   deletedMeal.deleteFav(mealDetail.get(position))
            );
        } else {
            holder.deleteBtn.setVisibility(View.GONE);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView mealImage;
        FloatingActionButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.catTitle);
            mealImage = itemView.findViewById(R.id.catImg);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }

    @Override
    public int getItemCount() {
        if (mealDetail == null)
            return 0;
        return mealDetail.size();
    }
}
