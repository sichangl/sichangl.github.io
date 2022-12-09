package com.CS571.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.CS571.myapplication.databinding.ActivityStorageBinding;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StorageActivity extends AppCompatActivity {
    ActivityStorageBinding binding;
    SharedPreferences preferences;
    RecyclerView storageRecyclerView;
    List<storageInfo> storageInfo;
    storageAdapter storageAdapter;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStorageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        constraintLayout = findViewById(R.id.myStorage);
//      set up back arrow :
        ImageView backArrow = binding.back;
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        storageInfo = new ArrayList<storageInfo>();
        preferences = getSharedPreferences("cs571", 0);
        Map<String, ?> storedInfo = preferences.getAll();
        Log.d("storeInfo", String.valueOf(storedInfo.entrySet()));
        int curIndex = 1;

        for (Map.Entry<String, ?> entry : storedInfo.entrySet()) {
            Log.d("storeInfo", entry.getValue().toString());
            String[] reservationDetail = entry.getValue().toString().split(";");
            storageInfo cur = new storageInfo();
            cur.setIndex(curIndex++);
            cur.setReserveName(reservationDetail[0]);
            cur.setReserveDate(reservationDetail[1]);
            cur.setReserveTime(reservationDetail[2]);
            cur.setReserveEmail(reservationDetail[3]);
            cur.setReserveId(reservationDetail[4]);
            storageInfo.add(cur);
            Log.d("storeInfo", reservationDetail[0]);
            Log.d("storeInfo", reservationDetail[1]);
            Log.d("storeInfo", reservationDetail[2]);
            Log.d("storeInfo", reservationDetail[3]);
            Log.d("storeInfo", reservationDetail[4]);
        }
        if(storageInfo.size() == 0) {
            findViewById(R.id.noBookingsFound).setVisibility(View.VISIBLE);
        }
        Log.d("storeInfo", String.valueOf(storageInfo.size()));
        storageRecyclerView = findViewById(R.id.storageRecyclerView);
        storageRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        storageAdapter = new storageAdapter(getApplicationContext(), storageInfo);
        storageRecyclerView.setAdapter(storageAdapter);
        enableSwipeToDeleteAndUndo();
    }
    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();
                storageInfo clickedItem = storageInfo.get(position);
                storageInfo.remove(clickedItem);
                Log.d("aaa", String.valueOf(storageInfo.size()));
                String removedKey = clickedItem.getReserveId();
                preferences = getSharedPreferences( "cs571", 0);
                SharedPreferences.Editor editor = preferences.edit();
                storageRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                storageAdapter = new storageAdapter(getApplicationContext(), storageInfo);
                storageRecyclerView.setAdapter(storageAdapter);
                if(storageInfo.size() == 0) {
                    findViewById(R.id.noBookingsFound).setVisibility(View.VISIBLE);
                }
                editor.remove(removedKey).apply();
                // create a SnackBar :
                Snackbar snackbar = Snackbar.make(constraintLayout,
                        "Removing Existing Reservation", Snackbar.LENGTH_SHORT);
                View custom = getLayoutInflater().inflate(R.layout.snack_custom,null);
                snackbar.getView().setBackgroundColor(Color.BLACK);
                Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
                snackbarLayout.setPadding(0,0,0,0);
                snackbar.show();
                }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(storageRecyclerView);
    }
}