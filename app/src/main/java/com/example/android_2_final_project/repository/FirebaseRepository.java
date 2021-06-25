package com.example.android_2_final_project.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

public class FirebaseRepository {

    private final DatabaseReference mDatabase;
    private final FirebaseAuth mFirebaseAuth;

    private FireBaseRepositoryListener listener;

    public interface FireBaseRepositoryListener {
        void OnSignInSuccessful(FirebaseUser user);

        void OnSignUpSuccessful(FirebaseUser user);

        void OnUserExists(boolean isExists);
    }

    @Inject
    public FirebaseRepository(DatabaseReference db, FirebaseAuth auth) {
        mDatabase = db;
        mFirebaseAuth = auth;
    }

    public void emailPasswordSignIn(String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null) {
                        listener.OnSignInSuccessful(task.getResult().getUser());
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "onFailure: " + e.getMessage());
            }
        });
    }

    private void signUpWithEmailAndPassword(String email, String password) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null) {
                        listener.OnSignUpSuccessful(task.getResult().getUser());
                    }
                }
            }
        });
    }

    public void signUp(String email, String password) {
        signUpWithEmailAndPassword(email, password);
    }

    public void isUserExists(String email) {
        mFirebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean isExists = !task.getResult().getSignInMethods().isEmpty();

                listener.OnUserExists(isExists);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("markomarko", "onFailure: failed  " + e.getMessage());
            }
        });
    }

    public void registerUserInRealtime(FirebaseUser user) {
//        mDatabase
    }

    public void setListener(FireBaseRepositoryListener listener) {
        this.listener = listener;
    }
}
