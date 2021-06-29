package com.example.android_2_final_project.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_2_final_project.R;
import com.example.android_2_final_project.adapters.ChatListRecyclerViewAdapter;
import com.example.android_2_final_project.adapters.MessagesRecyclerViewAdapter;
import com.example.android_2_final_project.models.ChatModel;
import com.example.android_2_final_project.models.MessageModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatListFragment extends Fragment {

    private RecyclerView mChatListRecycler;
    private ChatListRecyclerViewAdapter chatListRecyclerViewAdapter;
    private DatabaseReference mRootDbReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_list, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        mRootDbReference = FirebaseDatabase.getInstance().getReference();

        loadChatList();

    }

    private void loadChatList() {
        mChatListRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));

        DatabaseReference contactsReference = mRootDbReference.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("chats");

        FirebaseRecyclerOptions<String> options
                = new FirebaseRecyclerOptions.Builder<String>()
                .setQuery(contactsReference, String.class)
                .build();

        chatListRecyclerViewAdapter = new ChatListRecyclerViewAdapter(options, requireContext(), new ChatListRecyclerViewAdapter.ChatListAdapterListener() {
            @Override
            public void OnCellClicked(String contactID) {
                Bundle bundle = new Bundle();
                bundle.putString(CarDetailsFragment.SELLER_UID_KEY, contactID);

                NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_chatListFragment_to_chatFragment, bundle);
            }
        });

        mChatListRecycler.setAdapter(chatListRecyclerViewAdapter);
        chatListRecyclerViewAdapter.startListening();
    }

    private void initViews(View view) {
        mChatListRecycler = view.findViewById(R.id.chat_list_recycler);
    }

    @Override
    public void onStop() {
        super.onStop();

        chatListRecyclerViewAdapter.stopListening();
    }
}
