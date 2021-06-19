package com.example.android_2_final_project;

import com.google.firebase.auth.FirebaseAuth;

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
    public FirebaseAuth getFirebaseAuth(){

        return FirebaseAuth.getInstance();
    }
}
