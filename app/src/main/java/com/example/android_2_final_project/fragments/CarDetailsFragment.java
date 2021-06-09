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

import com.bumptech.glide.Glide;
import com.example.android_2_final_project.R;
import com.example.android_2_final_project.models.Car;

public class CarDetailsFragment extends Fragment {

    private Button mFollowCar;
    private Button mSeeSellerProfile;

    private ImageView mCarImage;
    private TextView mCarModelTv;
    private TextView mManufactureYearTv;
    private TextView mDescriptionTv;


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
        populateViews();
    }

    private void populateViews() {
        Car car = (Car) requireArguments().getSerializable("car");

        Glide.with(this).load(car.getImagePath()).into(mCarImage);
        mCarModelTv.setText(car.getCarModel());
        mManufactureYearTv.setText(getString(R.string.empty_string, car.getManufactureYear()));
        mDescriptionTv.setText(car.getDescription());
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
        mCarModelTv = view.findViewById(R.id.car_model_tv);
        mManufactureYearTv = view.findViewById(R.id.manufacture_year_text_view);
        mDescriptionTv = view.findViewById(R.id.description_text_view);

        mFollowCar = view.findViewById(R.id.car_follow_btn);
        mSeeSellerProfile = view.findViewById(R.id.seller_profile_btn);
    }
}
