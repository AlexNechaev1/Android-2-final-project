package com.example.android_2_final_project.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.android_2_final_project.R;
import com.example.android_2_final_project.models.User;
import com.example.android_2_final_project.viewmodels.AuthenticationViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UserProfilePageFragment extends Fragment {

    private FirebaseStorage mStorage;
    private StorageReference mStorageReference;

    private final static String PROFILE_IMAGE_STORAGE_PATH = "profile_image";

    private static final int PICK_IMAGE_REQUEST = 001;
    private static final int RESULT_OK = 010;
    private static final int PICK_PHOTO_FOR_AVATAR = 002;
    private static final int WRITE_PERMISSION_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int GALLERY_REQUEST = 3;
    private Button mEditChangesBtn;
    private ImageButton mProfileUserPictureEditBtn;
    private Button mSwitchToSellerBtn;
    private Button mChangePassword;
    private Button mSignOutBtn;
    private ImageView mProfileIv;

    private EditText mUsernameEt;
    private EditText mEmailEt;
    private EditText mBioEt;

    private User mUser;
    private Uri imageUri;


    Uri uri;
    File photoFile;

    AuthenticationViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mStorage = FirebaseStorage.getInstance();
        mStorageReference = mStorage.getReference();

        LinearLayout mBottomSheet = view.findViewById(R.id.bottom_sheet);
        BottomSheetBehavior mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


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

        LinearLayout bottomSheet = view.findViewById(R.id.bottom_sheet);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        mProfileIv = view.findViewById(R.id.profile_picture_img);

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

                if (Build.VERSION.SDK_INT >= 23) {
                    int hasWritePermission = requireActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_REQUEST);
                    }
                }

//                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                showBottomSheetDialog();


//                Intent intent = new Intent();
//                intent.setAction(android.content.Intent.ACTION_VIEW);
//                intent.setType("image/*");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
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

    private void uploadImageToFirebase(Uri uri){
        StorageReference ref = mStorageReference
                .child(PROFILE_IMAGE_STORAGE_PATH)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                          @Override
                                          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                              mStorageReference
                                                      .child(PROFILE_IMAGE_STORAGE_PATH)
                                                      .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                      .getDownloadUrl()
                                                      .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                          @Override
                                                          public void onSuccess(Uri uri) {

                                                              User user = viewModel.getRealtimeUser().getValue();
                                                              user.setProfileImage(uri.toString());
                                                              // save in real time
                                                              FirebaseDatabase
                                                                      .getInstance()
                                                                      .getReference()
                                                                      .child("users")
                                                                      .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                      .setValue(user);

                                                          }
                                                      });

                                          }
                                      }
                );
    }

    private final ActivityResultLauncher<Uri> takePicture = registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {

            Glide.with(requireActivity()).load(photoFile.getAbsoluteFile()).into(mProfileIv);

            uploadImageToFirebase(Uri.fromFile(photoFile));
        }
    });

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet);
        Button userCameraBtn = bottomSheetDialog.findViewById(R.id.use_camera_btn);
        userCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    photoFile = File.createTempFile(
                            "IMG_",
                            ".jpg",
                            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                uri = FileProvider.getUriForFile(requireActivity(), "com.example.android_2_final_project.provider", photoFile);

                takePicture.launch(uri);
            }
        });
        Button galleryBtn = bottomSheetDialog.findViewById(R.id.choose_from_gallery_btn);
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGalleryConfirmPermission();
            }
        });

        bottomSheetDialog.show();
    }

    private void pickImageFromGalleryConfirmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasWritePermission = requireActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (hasWritePermission == PackageManager.PERMISSION_GRANTED) {
                pickImageFromGallery();
            }
            else {
                startPermissionRequest(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }
        else {
            pickImageFromGallery();
        }
    }

    private void pickImageFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST);
    }

    private void startPermissionRequest(String manifest) {
        ActivityResultLauncher<String> requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean isGranted) {
                        if (isGranted) {
                            pickImageFromGallery();
                        }
                        else {
                            Toast.makeText(getContext(), "Need permission to add image", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        requestPermissionLauncher.launch(manifest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {

            if (data != null) {
                imageUri = data.getData();
                Glide.with(this).load(imageUri).into(mProfileIv);
                uploadImageToFirebase(imageUri);
            }

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

        if (mUser.getProfileImage() != null) {
            Glide.with(this).load(mUser.getProfileImage()).into(mProfileIv);
        }
    }
}
