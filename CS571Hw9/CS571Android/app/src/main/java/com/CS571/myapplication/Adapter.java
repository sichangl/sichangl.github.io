package com.CS571.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CS571.myapplication.model.yelpApi.Business;
import com.CS571.myapplication.model.yelpApi.yelpResponse;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflater;
    List<Business> businessesList;
    private static OnItemClickListener mListener;
    // OnItemClickListener
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    // constructor
    public Adapter(Context ctx, List<Business> businessesList) {
        this.inflater = LayoutInflater.from(ctx);
        this.businessesList = businessesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.yelp_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // bind the data
        DecimalFormat df = new DecimalFormat("###.##");
        holder.displayIndex.setText(String.valueOf(position + 1));
        holder.displayName.setText(String.valueOf(businessesList.get(position).getName()));
        holder.displayRating.setText(String.valueOf(new String(String.valueOf(businessesList.get(position).getRating()))));
        holder.displayDistance.setText(String.valueOf(new String(df.format(businessesList.get(position).getDistance() / 1609.34))));
        Picasso.get().load(businessesList.get(position).getImage_url()).into(holder.displayUrl);
    }

    @Override
    public int getItemCount() {
        return businessesList.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        TextView displayName, displayRating, displayIndex, displayDistance;
        ImageView displayUrl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            displayName = itemView.findViewById(R.id.displayName);
            displayIndex = itemView.findViewById(R.id.displayIndex);
            displayRating = itemView.findViewById(R.id.displayRating);
            displayDistance = itemView.findViewById(R.id.displayDistance);
            displayUrl = itemView.findViewById(R.id.displayUrl);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
