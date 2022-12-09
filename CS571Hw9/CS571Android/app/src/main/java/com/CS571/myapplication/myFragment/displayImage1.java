package com.CS571.myapplication.myFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.CS571.myapplication.R;
import com.CS571.myapplication.databinding.FragmentDisplayImage1Binding;
import com.squareup.picasso.Picasso;

public class displayImage1 extends Fragment {
    String url;
    FragmentDisplayImage1Binding binding;

    public static displayImage1 newInstance(String displayPicUrl1) {
        Bundle bundle = new Bundle();
        bundle.putString("url", displayPicUrl1);
        displayImage1 diplayPic1 = new displayImage1();
        diplayPic1.setArguments(bundle);
        return  diplayPic1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDisplayImage1Binding.inflate(getLayoutInflater());
        Picasso.get().load(getArguments().getString("url")).into(binding.displayImage1);
        return binding.getRoot();
    }
}