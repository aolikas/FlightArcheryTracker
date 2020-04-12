package com.example.flightarcherytracker.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flightarcherytracker.R;
import com.example.flightarcherytracker.entity.Shoot;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;



public class ShootMapRecyclerViewAdapter extends RecyclerView.Adapter<ShootMapRecyclerViewAdapter.ShootMapViewHolder> {

    private static final String TAG = "ADAPTER";

    private List<Shoot> mShoots = new ArrayList<>();
    private GoogleMap mMap;


    @NonNull
    @Override
    public ShootMapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shoot_map, parent, false);
        return new ShootMapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShootMapViewHolder holder, int position) {

        final Shoot currentShoot = mShoots.get(position);

        holder.tvPosition.setText(String.valueOf(position + 1));

        DecimalFormat df = new DecimalFormat("#.##");
        holder.tvLat.setText(df.format(currentShoot.getShootLat()));
        holder.tvLng.setText(df.format(currentShoot.getShootLng()));


        // mList.add(selectedLocation);

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d(TAG, "onClick: ");

                final double lat = currentShoot.getShootLat();
                final double lng = currentShoot.getShootLng();
                final LatLng selectedLocation = new LatLng(lat,lng);

                CameraPosition newCameraPosition = new CameraPosition.Builder()
                       .target(selectedLocation)
                       .zoom(20f)
                      .build();
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(newCameraPosition));


            }
        });

    }

    @Override
    public int getItemCount() {
        return mShoots.size();
    }

    public void setShoots( List<Shoot> shoots, GoogleMap map) {

        this.mShoots = shoots;
        this.mMap = map;
        notifyDataSetChanged();
    }


    class ShootMapViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvPosition;
        private TextView tvLat;
        private TextView tvLng;
        private CardView singleCard;
        ItemClickListener clickListener;


        public ShootMapViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPosition = itemView.findViewById(R.id.tv_shoot_map_id);
            tvLat = itemView.findViewById(R.id.tv_shoot_map_lat);
            tvLng = itemView.findViewById(R.id.tv_shoot_map_lng);
            singleCard = itemView.findViewById(R.id.shoot_map_card_view);
            singleCard.setOnClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(itemView, getLayoutPosition());

        }
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }
}
