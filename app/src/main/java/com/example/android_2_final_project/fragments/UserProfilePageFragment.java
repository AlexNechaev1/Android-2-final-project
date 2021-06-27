package com.example.android_2_final_project.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.android_2_final_project.R;
import com.example.android_2_final_project.models.User;
import com.example.android_2_final_project.viewmodels.AuthenticationViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserProfilePageFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 001;
    private static final int RESULT_OK = 010;
    private static final int PICK_PHOTO_FOR_AVATAR = 002;
    Button mEditChangesBtn;
    ImageButton mProfileUserPictureEditBtn;
    Button mSwitchToSellerBtn;
    Button mChangePassword;
    Button mSignOutBtn;

    EditText mUsernameEt;
    EditText mEmailEt;
    EditText mBioEt;

    User mUser;

    private String mFilePath;


    AuthenticationViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setListeners(view);

        viewModel = new ViewModelProvider(requireActivity()).get(AuthenticationViewModel.class);

//        viewModel.getRealtimeUser().observe(getViewLifecycleOwner(), new Observer<User>() {
//            @Override
//            public void onChanged(User user) {
//                mUser = user;
//                populateView(user);
//            }
//        });


        mUser = viewModel.getRealtimeUser().getValue();
        populateView();
    }

    private void initViews(View view) {

        mEditChangesBtn = view.findViewById(R.id.save_btn);
        mProfileUserPictureEditBtn = view.findViewById(R.id.profile_picture_edit_btn);
        mSwitchToSellerBtn = view.findViewById(R.id.switch_to_seller_btn);
        mChangePassword = view.findViewById(R.id.change_password_btn);
        mSignOutBtn = view.findViewById(R.id.sign_out_btn);

        mUsernameEt = view.findViewById(R.id.profile_name_edit_text);
        mEmailEt = view.findViewById(R.id.email_edit_text);
        mBioEt = view.findViewById(R.id.bio_edit_text);
    }

    private void setListeners(View view) {
        mEditChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEditEnabled = mEditChangesBtn.getText().toString().equals(getResources().getString(R.string.edit)); //edit == true / save = false
                changeEditState(isEditEnabled);
                if (!isEditEnabled) {
                    saveUser();
                }
            }
        });
        mProfileUserPictureEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        mSwitchToSellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigate to marketing page - upgrade to seller profile
            }
        });
        mChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigate to change password page
            }
        });
        mSignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(v).popBackStack();
            }
        });

    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = requireActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }

        }else {

        }
    }


    private void changeEditState(boolean isEditEnabled) {
        if (isEditEnabled) {
            mUsernameEt.setEnabled(true);
            mEmailEt.setEnabled(true);
            mBioEt.setEnabled(true);
            mProfileUserPictureEditBtn.setVisibility(View.VISIBLE);
            mEditChangesBtn.setText(getResources().getString(R.string.save));
        }
        else {
            //TODO collect the data from all field and override the existing on in the database
            mUsernameEt.setEnabled(false);
            mEmailEt.setEnabled(false);
            mBioEt.setEnabled(false);
            mProfileUserPictureEditBtn.setVisibility(View.GONE);
            mEditChangesBtn.setText(getResources().getString(R.string.edit));
        }
    }

    private void saveUser() {
        String username = mUsernameEt.getText().toString().trim();
        String email = mEmailEt.getText().toString().trim();
        String bio = mBioEt.getText().toString().trim();

        mUser.setUsername(username);
        mUser.setEmail(email);
        mUser.setBio(bio);

        viewModel.saveUser(mUser);
    }


    private void populateView() {
        //TODO add setImg
        mUsernameEt.setText(mUser.getUsername());
        mEmailEt.setText(mUser.getEmail());
        mBioEt.setText(mUser.getBio());
    }
}
