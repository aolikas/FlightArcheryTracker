package com.example.flightarcherytracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flightarcherytracker.R;
import com.example.flightarcherytracker.entity.Shoot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShootRecyclerViewAdapter extends RecyclerView.Adapter<ShootRecyclerViewAdapter.ShootViewHolder> {

    private List<Shoot> mShoots = new ArrayList<>();

    @NonNull
    @Override
    public ShootViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shot, parent, false);
        return new ShootViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShootViewHolder holder, int position) {

        Shoot currentShoot = mShoots.get(position);
        holder.tvPosition.setText(String.valueOf(position + 1));

        DecimalFormat df = new DecimalFormat("#.##");
        holder.tvDistance.setText(df.format(currentShoot.getShootDistance()));
        holder.tvDescription.setText(currentShoot.getShootDescription());
    }

    @Override
    public int getItemCount() {
        return mShoots.size();
    }

    public void setShoots(List<Shoot> shoots) {
        this.mShoots = shoots;
        notifyDataSetChanged();
    }

    static class ShootViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPosition;
        private TextView tvDistance;
        private TextView tvDescription;

        private ShootViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPosition = itemView.findViewById(R.id.tv_shot_id);
            tvDistance = itemView.findViewById(R.id.tv_shot_distance);
            tvDescription = itemView.findViewById(R.id.tv_shot_description);
        }
    }
}
