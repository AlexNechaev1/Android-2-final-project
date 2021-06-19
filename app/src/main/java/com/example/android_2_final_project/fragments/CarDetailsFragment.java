package com.example.android_2_final_project.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.android_2_final_project.R;
import com.example.android_2_final_project.models.Car;
import com.google.android.material.appbar.AppBarLayout;


public class CarDetailsFragment extends Fragment {

    private Button mFollowCar;
    private Button mSeeSellerProfile;

    private ImageView mCarImage;
    private TextView mCarModelTv;
    private TextView mManufactureYearTv;
    private TextView mDescriptionTv;
    private Toolbar toolbar;

    private AppBarLayout appBarLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_details_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
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

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(null);

        appBarLayout = view.findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0)
                {
                    Log.d("markomarko", "onOffsetChanged: full expanded");
                }
                else
                {
                    Log.d("markomarko", "onOffsetChanged: Not fully expanded or collapsed");
                }
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_car_detail,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_go_to_seller:
                // navigate to seller profile
                break;
            case R.id.action_follow:
                // add current post to user's saved posts.
                break;
        }

//        Log.d("markomarko", "onOptionsItemSelected: " + title);

        return super.onOptionsItemSelected(item);
    }



}
