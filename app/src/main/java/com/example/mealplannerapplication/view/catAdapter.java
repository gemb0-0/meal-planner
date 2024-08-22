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

import java.util.List;

public class catAdapter extends RecyclerView.Adapter<catAdapter.ViewHolder>{
    List<Category> categories;

    public catAdapter(List<Category> categories) {
        this.categories = categories;
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
        holder.name.setText(categories.get(position).getStrCategory());
        String url = categories.get(position).getStrCategoryThumb();
        Glide.with(holder.itemView.getContext()).load(url)
                .apply(new RequestOptions().override(190,130).centerInside()).placeholder(R.drawable.ic_launcher_foreground).into(holder.image);

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
        return categories.size();
    }

}
