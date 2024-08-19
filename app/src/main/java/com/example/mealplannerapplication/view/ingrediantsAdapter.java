package com.example.mealplannerapplication.view;

import static java.security.AccessController.getContext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealplannerapplication.R;

import java.util.List;

public class ingrediantsAdapter extends RecyclerView.Adapter<ingrediantsAdapter.ViewHolder>{

    List<String>  Ingredients;
    List<String>  Measurements;
    String url;
    public ingrediantsAdapter(List<String> allIngredients,List<String> allMeasurements) {
        this.Ingredients = allIngredients;
        this.Measurements = allMeasurements;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.ingredients, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ingrediantsAdapter.ViewHolder holder, int position) {
        holder.title.setText(Ingredients.get(position) + " " + Measurements.get(position));
        //Glide.with(getContext()).load(mealDetail.get(0).getStrMealThumb()).apply(new RequestOptions()).centerCrop().placeholder(mealImage.getDrawable()).into(mealImage);
       url =  "https://www.themealdb.com/images/ingredients/"+Ingredients.get(position)+".png";
        Glide.with(holder.itemView.getContext())
                .load(url)
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
        public TextView title;
        public ViewHolder(View itemView) {
            super(itemView);
        imageView = itemView.findViewById(R.id.ingredientImage);
        title = itemView.findViewById(R.id.ingredientQuantity);
        }
    }
}
