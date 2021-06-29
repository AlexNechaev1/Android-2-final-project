package com.example.android_2_final_project.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.android_2_final_project.R;
import com.example.android_2_final_project.adapters.MessagesRecyclerViewAdapter;
import com.example.android_2_final_project.models.MessageModel;
import com.example.android_2_final_project.models.UserModel;
import com.example.android_2_final_project.viewmodels.AuthenticationViewModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatFragment extends Fragment {

    private ImageButton mSellerProfilePictureImgBtn;
    private ImageButton mSendMessageImgBtn;
    private TextView mSellerProfileNameTV;
    private EditText mMessageContainerET;
    private RecyclerView mMessagesRecyclerView;
    private MessagesRecyclerViewAdapter mMessagesAdapter;

    private AuthenticationViewModel viewModel;

    private UserModel mSeller;
    private String mSellerUID;
    private String mChatId;

    private DatabaseReference mRootDbReference;
    private DatabaseReference mUserChatsReference;

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

        mSellerUID = requireArguments().getString(CarDetailsFragment.SELLER_UID_KEY);

        mRootDbReference = FirebaseDatabase.getInstance().getReference();
        mUserChatsReference = mRootDbReference.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("chats");

        mUserChatsReference.child(mSellerUID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    mChatId = task.getResult().getValue(String.class);

                    if (mChatId == null) {
                        createChat();
                    } else {
                        loadMessages();
                    }
                } else {
                    Log.d("TAG", "onComplete: failed " + task.getException().toString());
                }
            }
        });

        listener.OnChatOpen();

        viewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        viewModel.getSeller().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel seller) {
                mSeller = seller;
                populateViews();
            }
        });

        viewModel.getUserByUID(mSellerUID);

        intiViews(view);
        setListeners();
    }

    private void loadMessages() {
        mMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        DatabaseReference messagesReference = mRootDbReference.child("chats").child(mChatId);

        FirebaseRecyclerOptions<MessageModel> options
                = new FirebaseRecyclerOptions.Builder<MessageModel>()
                .setQuery(messagesReference, MessageModel.class)
                .build();

        mMessagesAdapter = new MessagesRecyclerViewAdapter(options);
        mMessagesRecyclerView.setAdapter(mMessagesAdapter);
        mMessagesAdapter.startListening();

    }

    private void createChat() {
        mChatId = mUserChatsReference.child(mSellerUID).push().getKey();

        mRootDbReference.child("users").child(mSellerUID).child("chats")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(mChatId);

        mUserChatsReference.child(mSellerUID).setValue(mChatId).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    loadMessages();
                }
            }
        });
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
                sendMessage();
            }
        });

    }

    private void sendMessage() {
        String key = mRootDbReference.child("chats").child(mChatId).push().getKey();

        MessageModel message = new MessageModel(
                mMessageContainerET.getText().toString(),
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );

        mMessageContainerET.setText("");

        mRootDbReference.child("chats").child(mChatId).child(key).setValue(message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listener.OnChatClose();
    }

    @Override
    public void onStop() {
        super.onStop();

        mMessagesAdapter.stopListening();
    }
}
