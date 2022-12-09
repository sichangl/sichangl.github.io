package com.CS571.myapplication;


import static com.CS571.myapplication.MainActivity.EXTRA_ID;
import static com.CS571.myapplication.MainActivity.EXTRA_LAT;
import static com.CS571.myapplication.MainActivity.EXTRA_LINK;
import static com.CS571.myapplication.MainActivity.EXTRA_LNG;
import static com.CS571.myapplication.MainActivity.EXTRA_NAME;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.CS571.myapplication.databinding.ActivityDetailBinding;
import com.CS571.myapplication.model.detailYelp.detailCategory;
import com.CS571.myapplication.model.detailYelp.detailResponse;
import com.CS571.myapplication.myFragment.DetailFragment;
import com.CS571.myapplication.myFragment.MapFragment;
import com.CS571.myapplication.myFragment.ReviewFragment;
import com.CS571.myapplication.network.detailYelpApi;
import com.CS571.myapplication.network.detailYelpRetrofitClient;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    // tableLayout
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ActivityDetailBinding binding;
    // all the parameters that we need from yelp.
    public String detailId;
    public String detailName;
    public String detailAddress;
    public String detailPrice;
    public String detailPhoneNumber;
    public boolean detailStatus;
    public String detailLink;
    public String detailCategory;
    public double detailLat;
    public double detailLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        detailId = intent.getStringExtra(EXTRA_ID);
        detailName = intent.getStringExtra(EXTRA_NAME);
        detailLat = Double.parseDouble(intent.getStringExtra(EXTRA_LAT));
        detailLng = Double.parseDouble(intent.getStringExtra(EXTRA_LNG));
        detailLink = intent.getStringExtra(EXTRA_LINK);
        Log.d("review", detailId);
        // set up tableLayout
        tabLayout = binding.tableTopBar;
        viewPager = binding.myViewPage;

        tabLayout.setupWithViewPager(viewPager);

        TableAdapter tableAdapter = new TableAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        tableAdapter.addFragment(new DetailFragment(detailId), "BUSINESS DETAILS");
        tableAdapter.addFragment(new MapFragment(detailLat, detailLng), "MAP LOCATION");
        tableAdapter.addFragment(new ReviewFragment(detailId), "REVIEWS");


        // ToolBar Action
        Toolbar toolbar = binding.toolbar;
        ImageView backArrow = binding.backArrowIcon;
        ImageView faceBookIcon = binding.facebookIcon;
        ImageView twitterIcon = binding.twitterIcon;
        toolbar.setTitle(detailName);
        setSupportActionBar(toolbar);

        // backArrow onClick to the previous page
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // click on facebookIcon to send a post
        faceBookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareAppLinkViaFacebook(detailLink);
            }
        });

        // click on twitterIcon to send a post
        twitterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Check out the " + detailName + " On Yelp." + detailLink;
                String tweetUrl = "https://twitter.com/intent/tweet?text=" + message;
                Uri uri = Uri.parse(tweetUrl);
                Intent shareIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(shareIntent);
            }
        });

        // navigationListener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        viewPager.setAdapter(tableAdapter);
    }
    // share link through FaceBook
    private void shareAppLinkViaFacebook(String urlToShare) {
        try {
            Intent intent1 = new Intent();
            intent1.setClassName("com.facebook.katana", "com.facebook.katana.activity.composer.ImplicitShareIntentHandler");
            intent1.setAction("android.intent.action.SEND");
            intent1.setType("text/plain");
            intent1.putExtra("android.intent.extra.TEXT",urlToShare);
            startActivity(intent1);
        } catch (Exception e) {
            // If we failed (not native FB app installed), try share through SEND
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            startActivity(intent);
        }
    }

}