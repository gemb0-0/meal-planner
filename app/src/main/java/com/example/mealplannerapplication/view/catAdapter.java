package com.example.mealplannerapplication.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.Pojos.Category;
import com.example.mealplannerapplication.model.Pojos.Ingredients;

import java.util.List;

public class catAdapter extends RecyclerView.Adapter<catAdapter.ViewHolder>{
    List<Category> categories;
    List<Ingredients> ingredients;
    public catAdapter(List<Category> categories, List<Ingredients> ingredients) {
        this.categories = categories;
       this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public catAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
         View listItem = layoutInflater.inflate(R.layout.row_category, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull catAdapter.ViewHolder holder, int position) {
        if(ingredients != null) {
            holder.name.setText(ingredients.get(position).getStrIngredient());


          String  url = "https://www.themealdb.com/images/ingredients/" + ingredients.get(position).getStrIngredient() + ".png";
            Glide.with(holder.itemView.getContext()).load(url)
                    .apply(new RequestOptions().override(190,130).centerInside()).placeholder(R.drawable.ic_launcher_foreground).into(holder.image);

        }else {
            holder.name.setText(categories.get(position).getStrCategory());
            String url = categories.get(position).getStrCategoryThumb();
            Glide.with(holder.itemView.getContext()).load(url)
                    .apply(new RequestOptions().override(190, 130).centerInside()).placeholder(R.drawable.ic_launcher_foreground).into(holder.image);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.catImg);
            name = itemView.findViewById(R.id.catTitle);
        }
    }

    @Override
    public int getItemCount() {
        if(categories == null)
            return ingredients.size();
        return categories.size();
    }

}
