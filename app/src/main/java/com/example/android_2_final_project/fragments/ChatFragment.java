package com.example.android_2_final_project.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.android_2_final_project.R;
import com.example.android_2_final_project.models.UserModel;
import com.example.android_2_final_project.viewmodels.AuthenticationViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatFragment extends Fragment {

    private ImageButton mSellerProfilePictureImgBtn;
    private ImageButton mSendMessageImgBtn;
    private TextView mSellerProfileNameTV;
    private EditText mMessageContainerET;
    private RecyclerView mMessagesRecyclerView;

    private AuthenticationViewModel viewModel;

    private UserModel mSeller;

   public interface ChatFragmentListener {
        public void OnChatOpen();

        public void OnChatClose();
    }

    private ChatFragmentListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ChatFragmentListener) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException("The called activity is without implementation of the ChatFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listener.OnChatOpen();

        viewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);

        viewModel.getSeller().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel seller) {
                mSeller = seller;
                populateViews();
            }
        });

        String sellerUID = requireArguments().getString(CarDetailsFragment.SELLER_UID_KEY);
        viewModel.getUserByUID(sellerUID);

        intiViews(view);
        setListeners();

    }

    private void populateViews() {

        Glide.with(requireActivity())
                .load(mSeller.getProfileImage())
                .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(50)))
                .into(mSellerProfilePictureImgBtn);

        mSellerProfileNameTV.setText(mSeller.getUsername());

    }

    private void intiViews(View view) {
        mSellerProfilePictureImgBtn = view.findViewById(R.id.profile_img_btn);
        mSellerProfileNameTV = view.findViewById(R.id.profile_username);

        mMessagesRecyclerView = view.findViewById(R.id.chat_recycler_view);

        mMessageContainerET = view.findViewById(R.id.chat_input_text_et);
        mSendMessageImgBtn = view.findViewById(R.id.sent_message_img_btn);
    }

    private void setListeners() {

        mSellerProfilePictureImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mSendMessageImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listener.OnChatClose();
    }
}
