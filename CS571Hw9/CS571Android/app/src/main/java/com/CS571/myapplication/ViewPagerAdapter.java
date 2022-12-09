package com.CS571.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public List<Fragment> displayPicList;

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public ViewPagerAdapter(@NonNull FragmentManager fm, List<Fragment> displayPicList) {
        super(fm);
        this.displayPicList = displayPicList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return displayPicList.get(position);
    }

    @Override
    public int getCount() {
        return displayPicList.size();
    }
}