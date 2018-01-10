package com.example.root.mpolispager.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.root.mpolispager.R;

/**
 * Created by root on 6.1.18.
 */

public class FragmentDev extends Fragment {

    View v;
    ImageView img;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_dev, container, false);

        return v;
    }

    @Override
    public void onResume() {
        initV();
        super.onResume();
    }

    private void initV(){

        img = (ImageView) v.findViewById(R.id.dev_img);
        Glide.with(img.getContext()).load(R.drawable.info_1).into(img);

    }
}
