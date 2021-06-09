package com.example.android_2_final_project.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_2_final_project.R;

public class CarDetailsFragment extends Fragment {

    private Button mFollowCar;
    private Button mSeeSellerProfile;

    private ImageView mCarImage;
    private TextView mMainTitle;
    private TextView mFirstTitle;
    private TextView mSecondTitle;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_details_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        initListeners();
        // populate views with data
    }

    private void initListeners() {

        mFollowCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mSeeSellerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: transition to seller profile
            }
        });

        mCarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: transition to fragment to show full scale car image
            }
        });

    }

    private void initViews(View view) {
        mCarImage = view.findViewById(R.id.car_img);
        mMainTitle = view.findViewById(R.id.main_title_text_view);
        mFirstTitle = view.findViewById(R.id.first_description_text_view);
        mSecondTitle = view.findViewById(R.id.second_description_text_view);

        mFollowCar = view.findViewById(R.id.car_follow_btn);
        mSeeSellerProfile = view.findViewById(R.id.seller_profile_btn);
    }
}
