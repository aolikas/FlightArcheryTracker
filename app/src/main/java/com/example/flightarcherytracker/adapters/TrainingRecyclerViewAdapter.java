package com.example.flightarcherytracker.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flightarcherytracker.R;
import com.example.flightarcherytracker.entity.Training;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TrainingRecyclerViewAdapter extends RecyclerView.Adapter<TrainingRecyclerViewAdapter.TrainingViewHolder> {

    private static final String TAG = "TrainingRecyclerAdapter";

    private List<Training> mTrainings = new ArrayList<>();
    private TrainingDetailAdapterListener mListener;

    @NonNull
    @Override
    public TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_training, parent, false);
        return new TrainingViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingViewHolder holder, int position) {
        Training currentTraining = mTrainings.get(position);
        holder.tvId.setText(String.valueOf(currentTraining.getId()));
        //holder.tvPosition.setText(String.valueOf(position + 1));
        holder.tvDate.setText(fromDate(currentTraining.getTrainingDate()));
    }

    @Override
    public int getItemCount() {
        return mTrainings.size();
    }

    public void setTrainings(List<Training> trainings) {
        this.mTrainings = trainings;
        notifyDataSetChanged();
    }

    public Training getTrainingAt(int position) {
        return mTrainings.get(position);
    }


    class TrainingViewHolder extends RecyclerView.ViewHolder {

        //private TextView tvPosition;
        private TextView tvId;
        private TextView tvDate;

        private TrainingViewHolder(@NonNull View itemView, final TrainingDetailAdapterListener listener) {
            super(itemView);

            //tvPosition = itemView.findViewById(R.id.tv_training_position);
            tvDate = itemView.findViewById(R.id.tv_training_date);
            tvId = itemView.findViewById(R.id.tv_training_id);

            TextView showShootRecords = itemView.findViewById(R.id.tv_training_show_shots);
            ImageView showShootMapRecords = itemView.findViewById(R.id.iv_training_show_map_shots);
            ImageView deleteTraining = itemView.findViewById(R.id.iv_training_delete);

            showShootRecords.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onSeeShootsRecords: Clicked!");
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onSeeShootRecordsClick(mTrainings.get(position));
                        }
                    }
                }
            });

            showShootMapRecords.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onShowMapClick: Clicked!");
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onShowMapTrainingClick(mTrainings.get(position));
                        }
                    }
                }
            });

            deleteTraining.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onDeleteTrainingClick: Clicked!");
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteTrainingClick(position);
                        }
                    }
                }
            });
        }
    }

    public interface TrainingDetailAdapterListener {

        void onShowMapTrainingClick(Training training);

        void onDeleteTrainingClick(int position);

        void onSeeShootRecordsClick(Training training);
    }

    public void setOnTrainingClickListener(TrainingDetailAdapterListener listener) {
        mListener = listener;
    }

    private String fromDate(Date timestamp) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd", Locale.GERMAN);
            return df.format(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
