package com.example.mealplannerapplication.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.Pojos.Regions;

import java.util.List;

public class regionAdapter extends RecyclerView.Adapter<regionAdapter.ViewHolder> {
        List<Regions> regions;
    public regionAdapter( List<Regions> regions) {
        this.regions = regions;
    }

    @NonNull
    @Override
    public regionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_region, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull regionAdapter.ViewHolder holder, int position) {
        holder.regionName.setText(regions.get(position).getStrArea());

    }

    @Override
    public int getItemCount() {
        return regions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView regionName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            regionName = itemView.findViewById(R.id.catTitle);
        }
    }
}
