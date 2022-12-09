package com.CS571.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class storageAdapter extends RecyclerView.Adapter<storageAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<storageInfo> storageList;

    public storageAdapter(Context ctx, List<storageInfo> storageList) {
        this.inflater = LayoutInflater.from(ctx);
        this.storageList = storageList;
    }

    @NonNull
    @Override
    public storageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.storedinfo, parent, false);
        return new storageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull storageAdapter.ViewHolder holder, int position) {
        holder.index.setText(String.valueOf(position + 1));
        holder.name.setText(String.valueOf(storageList.get(position).getReserveName()));
        holder.date.setText(String.valueOf(storageList.get(position).getReserveDate()));
        holder.time.setText(String.valueOf(storageList.get(position).getReserveTime()));
        holder.email.setText(String.valueOf(storageList.get(position).getReserveEmail()));
    }

    @Override
    public int getItemCount() {
        return storageList.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        TextView index, name, date, time, email;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            index = itemView.findViewById(R.id.index);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            email = itemView.findViewById(R.id.email);
        }
    }
}
