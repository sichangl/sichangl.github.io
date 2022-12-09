package com.CS571.myapplication.myFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.CS571.myapplication.R;
import com.CS571.myapplication.databinding.FragmentReviewBinding;
import com.CS571.myapplication.model.detailYelp.detailResponse;
import com.CS571.myapplication.model.reviewYelp.Review;
import com.CS571.myapplication.model.reviewYelp.reviewResponse;
import com.CS571.myapplication.network.detailYelpApi;
import com.CS571.myapplication.network.detailYelpRetrofitClient;
import com.CS571.myapplication.network.reviewYelpApi;
import com.CS571.myapplication.network.reviewYelpRetrofitClient;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewFragment extends Fragment {
    private String businessId;
    private ArrayList<String> reviews;
    FragmentReviewBinding binding;

    public ReviewFragment(String businessId) {
        this.businessId = businessId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReviewBinding.inflate(inflater, container, false);
        Log.d("review", businessId);
        businessReviewRequest(businessId);
        return binding.getRoot();
    }

    private void businessReviewRequest(String businessId) {
        reviewYelpApi api = reviewYelpRetrofitClient.newInstance().create(reviewYelpApi.class);
        api.sendReviewYelpRequest(businessId).enqueue(new Callback<reviewResponse>() {
            @Override
            public void onResponse(Call<reviewResponse> call, Response<reviewResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("review", "get detail response");
                    ArrayList<Review> detail = response.body().reviews;
                    int numberOfReview = detail.size();
                    if (numberOfReview >= 1) {
                        createFirstLine(detail.get(0));
                    }
                    if (numberOfReview >= 2) {
                        createSecondLine(detail.get(1));
                    }
                    if (numberOfReview >= 3) {
                        createThirdLine(detail.get(2));
                    }
                } else {
                    Log.d("review", "get error");
                }
            }
            @Override
            public void onFailure(Call<reviewResponse> call, Throwable t) {
                Log.d("review", t.toString());
            }
        });
    }

    public void createFirstLine(Review review) {
        Log.d("review", review.user.name);
        TextView textViewUser1 = binding.user1;
        textViewUser1.setText(review.user.name);
        Log.d("review", String.valueOf(review.rating));
        TextView textViewRating1 = binding.rating1;
        textViewRating1.setText("Rating:" + String.valueOf(review.rating) +"/5");
        Log.d("review", review.text);
        TextView textViewComment1 = binding.comments1;
        textViewComment1.setText(review.text);
        Log.d("review", review.time_created);
        TextView textViewDate1 = binding.time1;
        textViewDate1.setText(review.time_created);
    }

    public void createSecondLine(Review review) {
        Log.d("review", review.user.name);
        TextView textViewUser2 = binding.user2;
        textViewUser2.setText(review.user.name);
        Log.d("review", String.valueOf(review.rating));
        TextView textViewRating2 = binding.rating2;
        textViewRating2.setText("Rating:" + String.valueOf(review.rating) +"/5");
        Log.d("review", review.text);
        TextView textViewComment2 = binding.comments2;
        textViewComment2.setText(review.text);
        Log.d("review", review.time_created);
        TextView textViewDate2 = binding.time2;
        textViewDate2.setText(review.time_created);
    }

    public void createThirdLine(Review review) {
        Log.d("review", review.user.name);
        TextView textViewUser3 = binding.user3;
        textViewUser3.setText(review.user.name);
        Log.d("review", String.valueOf(review.rating));
        TextView textViewRating3 = binding.rating3;
        textViewRating3.setText("Rating:" + String.valueOf(review.rating) +"/5");
        Log.d("review", review.text);
        TextView textViewComment3 = binding.comments3;
        textViewComment3.setText(review.text);
        Log.d("review", review.time_created);
        TextView textViewDate3 = binding.time3;
        textViewDate3.setText(review.time_created);
    }
}