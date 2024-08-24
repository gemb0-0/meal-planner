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
    IngradientsAdapterCallback callback;
    CategoriesAdapterCallback catCallback;
    public catAdapter(List<Category> categories, List<Ingredients> ingredients, IngradientsAdapterCallback callback, CategoriesAdapterCallback catCallback) {
        this.categories = categories;
       this.ingredients = ingredients;
        this.callback = callback;
        this.catCallback = catCallback;
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
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.name.getText();
                    callback.onIngredientClicked(ingredients.get(position).getStrIngredient());
                }
            });
        }else {
            holder.name.setText(categories.get(position).getStrCategory());
            String url = categories.get(position).getStrCategoryThumb();
            Glide.with(holder.itemView.getContext()).load(url)
                    .apply(new RequestOptions().override(190, 130).centerInside()).placeholder(R.drawable.ic_launcher_foreground).into(holder.image);
      holder.image.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              holder.name.getText();
              catCallback.onCategoryClicked(categories.get(position).getStrCategory());
          }
      });
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
        if (categories != null) {
            return categories.size();
        } else if (ingredients != null) {
            return ingredients.size();
        } else {
            return 0;
        }
    }

}
