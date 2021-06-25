package com.example.android_2_final_project.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class FirebaseModule {

    @Singleton
    @Provides
    public DatabaseReference provideDatabase() {
        return FirebaseDatabase.getInstance().getReference();
    }

    @Singleton
    @Provides
    public FirebaseAuth getFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }
}
