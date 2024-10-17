package com.example.mealplannerapplication.view.activity2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealplannerapplication.R;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder>{

    List<String>  Ingredients;
    List<String>  Measurements;
    String url;
    public IngredientsAdapter(List<String> allIngredients, List<String> allMeasurements) {
        this.Ingredients = allIngredients;
        this.Measurements = allMeasurements;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_ingredients, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.ViewHolder holder, int position) {
        holder.ingredients.setText(Ingredients.get(position) + " " + Measurements.get(position));

          url = "https://www.themealdb.com/images/ingredients/" + Ingredients.get(position) + ".png";
          Glide.with(holder.itemView.getContext())
                  .load(url).diskCacheStrategy(DiskCacheStrategy.ALL)
                  .apply(new RequestOptions().override(200, 200))
                  .placeholder(R.drawable.ic_launcher_background)
                  .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
      return Ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView ingredients;
        public ViewHolder(View itemView) {
            super(itemView);
        imageView = itemView.findViewById(R.id.ingredientImage);
        ingredients = itemView.findViewById(R.id.ingredientQuantity);
        }
    }
}
