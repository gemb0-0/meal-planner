package com.example.mealplannerapplication.view.activity2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplannerapplication.R;
import com.example.mealplannerapplication.model.LocalDataSource.db.Pojos.Regions;

import java.util.List;

public class RegionAdapter extends RecyclerView.Adapter<RegionAdapter.ViewHolder> {
        List<Regions> regions;
    RegionAdapterCallback regionCallback;
    public RegionAdapter(List<Regions> regions, RegionAdapterCallback regionCallback) {
        this.regions = regions;
        this.regionCallback = regionCallback;
    }

    @NonNull
    @Override
    public RegionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_region, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RegionAdapter.ViewHolder holder, int position) {
        holder.regionName.setText(regions.get(position).getStrArea());
        holder.regionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.regionName.getText();
                regionCallback.onRegionClick(holder.regionName.getText().toString());

            }     });
    }

    @Override
    public int getItemCount() {
        return regions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView regionName;
       ImageView regionImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            regionName = itemView.findViewById(R.id.regionName);
            regionImage = itemView.findViewById(R.id.regionImg);
        }
    }
}
