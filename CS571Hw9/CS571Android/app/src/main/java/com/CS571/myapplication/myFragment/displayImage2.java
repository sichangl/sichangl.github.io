package com.CS571.myapplication.myFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.CS571.myapplication.R;
import com.CS571.myapplication.databinding.FragmentDisplayImage1Binding;
import com.CS571.myapplication.databinding.FragmentDisplayImage2Binding;
import com.squareup.picasso.Picasso;

public class displayImage2 extends Fragment {
    String url;
    FragmentDisplayImage2Binding binding;

    public static displayImage1 newInstance(String displayPicUrl2) {
        Bundle bundle = new Bundle();
        bundle.putString("url", displayPicUrl2);
        displayImage1 diplayPic2 = new displayImage1();
        diplayPic2.setArguments(bundle);
        return  diplayPic2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDisplayImage2Binding.inflate(getLayoutInflater());
        Picasso.get().load(getArguments().getString("url")).into(binding.displayImage2);
        return binding.getRoot();
    }
}