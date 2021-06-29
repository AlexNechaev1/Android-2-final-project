package com.example.android_2_final_project.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android_2_final_project.models.PostModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExploreRepository {

    private final DatabaseReference mDatabase;
    private final FirebaseAuth mAuth;

    private ExploreRepositoryListener mListener;

    public interface ExploreRepositoryListener {
        void OnPostAdded(PostModel post);
    }

    @Inject
    public ExploreRepository(DatabaseReference db, FirebaseAuth auth) {
        mDatabase = db;
        mAuth = auth;
    }

    public void getRealtimePosts() {
        mDatabase.child("posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                PostModel post = snapshot.getValue(PostModel.class);

                if (mListener != null) {
                    mListener.OnPostAdded(post);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setListener(ExploreRepositoryListener listener) {
        mListener = listener;
    }
}
