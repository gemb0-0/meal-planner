package com.example.mealplannerapplication.view.activity2.adapters;

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
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.SingleRegionMeals;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class searchDetailsAdapter extends RecyclerView.Adapter<searchDetailsAdapter.ViewHolder> {
List<SingleRegionMeals> regionMeal;
    SearchDetailAdapterCallback callback;
    public searchDetailsAdapter(SearchDetailAdapterCallback callback,  List <SingleRegionMeals> mealDetail) {
        this.regionMeal = mealDetail;
        this.callback = callback;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_favourites, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    holder.deleteBtn.setVisibility(View.GONE);
    holder.title.setText(regionMeal.get(position).getStrMeal());
        Glide.with(holder.itemView.getContext()).load(regionMeal.get(position).getStrMealThumb())
                .apply(new RequestOptions()).centerCrop().placeholder(holder.mealImage.getDrawable()).into(holder.mealImage);
            holder.mealImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onMealClicked(regionMeal.get(position).getIdMeal());
                }
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
        if(regionMeal==null)
            return 0;
        return regionMeal.size();
    }



}
