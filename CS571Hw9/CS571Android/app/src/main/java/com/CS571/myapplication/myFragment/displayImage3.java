package com.CS571.myapplication.myFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.CS571.myapplication.R;
import com.CS571.myapplication.databinding.FragmentDisplayImage1Binding;
import com.CS571.myapplication.databinding.FragmentDisplayImage3Binding;
import com.squareup.picasso.Picasso;

public class displayImage3 extends Fragment {
    String url;
    FragmentDisplayImage3Binding binding;

    public static displayImage1 newInstance(String displayPicUrl3) {
        Bundle bundle = new Bundle();
        bundle.putString("url", displayPicUrl3);
        displayImage1 diplayPic3 = new displayImage1();
        diplayPic3.setArguments(bundle);
        return  diplayPic3;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDisplayImage3Binding.inflate(getLayoutInflater());
        Picasso.get().load(getArguments().getString("url")).into(binding.displayImage3);
        return binding.getRoot();
    }
}