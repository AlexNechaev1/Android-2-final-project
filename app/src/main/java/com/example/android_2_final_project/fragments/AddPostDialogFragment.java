package com.example.android_2_final_project.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.android_2_final_project.R;
import com.example.android_2_final_project.models.CarModel;
import com.example.android_2_final_project.models.PostModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AddPostDialogFragment extends BottomSheetDialogFragment {

    private static final int GALLERY_REQUEST = 1;
    private final static String POST_IMAGES_STORAGE_PATH = "post_images";

    private ImageButton mCarPostIb;
    private EditText mCarModelEt;
    private EditText mManufactureYearEt;
    private EditText mDescriptionEt;
    private Button mPostBtn;

    private File mPhotoFile;
    private Uri mUri;
    private String mUniqueId;

    private ActivityResultLauncher<String> mRequestPermissionLauncher;
    private ActivityResultLauncher<Uri> mTakePictureLauncher;
    private ActivityResultLauncher<String> mChoosePictureLauncher;

    private StorageReference mUserStorageRef;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mPostsDbReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserStorageRef = FirebaseStorage
                .getInstance().getReference()
                .child(POST_IMAGES_STORAGE_PATH);
        mPostsDbReference = FirebaseDatabase.getInstance().getReference().child("posts");

        initLaunchers();
        initViews(view);
        setListeners();
    }

    private void initLaunchers() {
        mRequestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean isGranted) {
                        if (isGranted) {
                            pickImageFromGallery();
                        } else {
                            Toast.makeText(getContext(), "Need permission to add image", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        mTakePictureLauncher =
                registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        Glide.with(requireActivity())
                                .load(mPhotoFile.getAbsoluteFile())
                                .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(50)))
                                .into(mCarPostIb);
                    }
                });

        mChoosePictureLauncher =
                registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        mUri = uri;
                        Glide.with(requireActivity())
                                .load(mUri)
                                .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(50)))
                                .into(mCarPostIb);
                    }
                });
    }

    private void initViews(View view) {
        mCarPostIb = view.findViewById(R.id.car_post_ib);
        mCarModelEt = view.findViewById(R.id.car_model_et);
        mManufactureYearEt = view.findViewById(R.id.car_manufacture_year_et);
        mDescriptionEt = view.findViewById(R.id.car_description_et);
        mPostBtn = view.findViewById(R.id.post_btn);
    }

    private void setListeners() {
        mCarPostIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });

        mPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPost();
            }
        });
    }

    private void uploadPost() {
        getDialog().dismiss();

        mUniqueId = UUID.randomUUID().toString();

        mUserStorageRef.child(mUniqueId).putFile(mUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mUserStorageRef.child(mUniqueId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String carModel = mCarModelEt.getText().toString().trim();
                        int manufactureYear = Integer.parseInt(mManufactureYearEt.getText().toString().trim());
                        String description = mDescriptionEt.getText().toString();

                        String strUri = uri.toString();

                        CarModel car = new CarModel(carModel, description, strUri, manufactureYear);
                        PostModel post = new PostModel(car, mFirebaseUser.getUid(), mUniqueId);

                        mPostsDbReference.child(mUniqueId).setValue(post);
                    }
                });
            }
        });
    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet);

        Button userCameraBtn = bottomSheetDialog.findViewById(R.id.use_camera_btn);
        userCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
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

    private void takePicture() {
        try {
            mPhotoFile = File.createTempFile(
                    "IMG_",
                    ".jpg",
                    requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        mUri = FileProvider.getUriForFile(requireActivity(), "com.example.android_2_final_project.provider", mPhotoFile);

        mTakePictureLauncher.launch(mUri);
    }

    private void pickImageFromGalleryConfirmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasWritePermission = requireActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (hasWritePermission == PackageManager.PERMISSION_GRANTED) {
                pickImageFromGallery();
            } else {
                mRequestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        } else {
            pickImageFromGallery();
        }
    }

    private void pickImageFromGallery() {
        mChoosePictureLauncher.launch("image/*");
    }

//    private void pickImageFromGallery() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        startActivityForResult(galleryIntent, GALLERY_REQUEST);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
//            if (data != null) {
//                imageUri = data.getData();
//                Glide.with(this).load(imageUri).into(mProfileIv);
//                uploadImageToFirebase(imageUri);
//            }
//        }
//    }
}
