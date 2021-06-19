package com.example.android_2_final_project.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UserViewModel extends ViewModel {

    private final FirebaseAuth mFirebaseAuth;

    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    @Inject
    public UserViewModel(FirebaseAuth firebaseAuth) {
       this.mFirebaseAuth = firebaseAuth;
    }

    public LiveData<FirebaseUser> getUser(){
        return user;
    }

    public void signIn(String email, String password){
        emailPasswordSignIn(email, password);
    }

    private void emailPasswordSignIn(String email, String password){
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    user.setValue(task.getResult().getUser());
                }
                // else..
            }
        });
    }
}
