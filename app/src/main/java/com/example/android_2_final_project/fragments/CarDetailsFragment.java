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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.android_2_final_project.R;
import com.example.android_2_final_project.models.PostModel;
import com.example.android_2_final_project.viewmodels.AuthenticationViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dagger.hilt.android.AndroidEntryPoint;

import static com.example.android_2_final_project.fragments.ExploreFragment.POSTS_KEY;

@AndroidEntryPoint
public class CarDetailsFragment extends Fragment {

    private Button mMessageToSellerBtn;
    private Button mSeeSellerProfileBtn;

    private ImageView mCarImage;
    private TextView mCarModelTv;
    private TextView mManufactureYearTv;
    private TextView mDescriptionTv;
    private Toolbar toolbar;

    private AppBarLayout appBarLayout;

    private DatabaseReference mDatabase;

    private AuthenticationViewModel viewModel;

    private PostModel post;

    public static final String SELLER_UID_KEY = "SELLER_UID_KEY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_details_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        viewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);

        setHasOptionsMenu(true);
        initViews(view);
        initListeners();
        populateViews();
    }

    private void populateViews() {
        post = (PostModel) requireArguments().getSerializable(POSTS_KEY);

        Glide.with(this).load(post.getCar().getImagePath()).into(mCarImage);
        mCarModelTv.setText(post.getCar().getCarModel());
        mManufactureYearTv.setText(getString(R.string.empty_string, post.getCar().getManufactureYear()));
        mDescriptionTv.setText(post.getCar().getDescription());
    }

    private void initListeners() {
        mMessageToSellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(SELLER_UID_KEY, post.getSellerId());
                Navigation.findNavController(v).navigate(R.id.action_carDetailsFragment_to_chatFragment, bundle);
            }
        });

        mSeeSellerProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(SELLER_UID_KEY, post.getSellerId());
                Navigation.findNavController(v).navigate(R.id.action_carDetailsFragment_to_profilePageFragment, bundle);
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
        mCarImage = view.findViewById(R.id.profile_picture_img);
        mCarModelTv = view.findViewById(R.id.car_model_tv);
        mManufactureYearTv = view.findViewById(R.id.manufacture_year_text_view);
        mDescriptionTv = view.findViewById(R.id.description_text_view);

        mMessageToSellerBtn = view.findViewById(R.id.car_send_message);
        mSeeSellerProfileBtn = view.findViewById(R.id.seller_profile_btn);

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(null);

        appBarLayout = view.findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    Log.d("markomarko", "onOffsetChanged: full expanded");
                } else {
                    Log.d("markomarko", "onOffsetChanged: Not fully expanded or collapsed");
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_car_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_go_to_seller:
                NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_carDetailsFragment_to_profilePageFragment);
                break;
            case R.id.action_follow:
                // add current post to user's saved posts.
                addFollowerToUser();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addFollowerToUser() {
        Log.d("TAG", "addFollowerToUser: ");
    }
}
