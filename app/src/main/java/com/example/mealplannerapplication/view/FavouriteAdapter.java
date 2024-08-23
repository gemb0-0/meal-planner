package com.example.mealplannerapplication.view;

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
import com.example.mealplannerapplication.model.Pojos.Meal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder>{
deleteFav deletedMeal;
favDetailView favDetailView;
List<Meal> mealDetail;
    public FavouriteAdapter(List<Meal> mealDetail, deleteFav deletedMeal, favDetailView favDetailView) {
        this.mealDetail = mealDetail;
        this.deletedMeal = deletedMeal;
        this.favDetailView = favDetailView;
    }

    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_favourites, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, int position) {
            String name =   mealDetail.get(position).getStrMeal();

            holder.title.setText(name);
            Glide.with(holder.itemView.getContext()).load(mealDetail.get(position).getStrMealThumb()).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(new RequestOptions()).centerCrop().placeholder(holder.mealImage.getDrawable()).into(holder.mealImage);

            holder.deleteBtn.setOnClickListener(v ->
                    deletedMeal.deleteFav(mealDetail.get(position))

            );
            holder.mealImage.setOnClickListener(v -> {
                String mealId = mealDetail.get(position).getIdMeal();
                favDetailView.getMealDetail(mealId);
            });


    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView mealImage;
        FloatingActionButton deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.catTitle);
            mealImage = itemView.findViewById(R.id.catImg);
            deleteBtn= itemView.findViewById(R.id.deleteBtn);
        }
    }
    @Override
    public int getItemCount() {
        return mealDetail.size();
    }


}
