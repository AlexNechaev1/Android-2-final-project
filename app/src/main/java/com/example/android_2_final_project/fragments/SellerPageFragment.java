package com.example.android_2_final_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.android_2_final_project.R;
import com.example.android_2_final_project.models.UserModel;
import com.example.android_2_final_project.viewmodels.AuthenticationViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SellerPageFragment extends Fragment {

    private ImageView sellerImg;
    private TextView sellerName;
    private TextView sellerBio;

    private AuthenticationViewModel viewModel;

    private UserModel mSeller;
    private String mSellerUID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSellerUID = requireArguments().getString(CarDetailsFragment.SELLER_UID_KEY);

        viewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        viewModel.getSeller().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel seller) {
                mSeller = seller;
                populateViews();
            }
        });

        viewModel.getUserByUID(mSellerUID);

        initViews(view);

    }

    private void initViews(View view) {
        sellerImg = view.findViewById(R.id.profile_picture_img);
        sellerName = view.findViewById(R.id.profile_name_text_view);
        sellerBio = view.findViewById(R.id.profile_bio_text_view);
    }

    private void populateViews() {

        sellerName.setText(mSeller.getUsername());
        sellerBio.setText(mSeller.getBio());

        Glide.with(requireActivity())
                .load(mSeller.getProfileImage())
                .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(50)))
                .into(sellerImg);


    }
}
