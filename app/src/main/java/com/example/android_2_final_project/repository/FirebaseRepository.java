package com.example.android_2_final_project.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.android_2_final_project.Question;
import com.example.android_2_final_project.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

public class FirebaseRepository {

    private final DatabaseReference mDatabase;
    private final FirebaseAuth mFirebaseAuth;

    private FireBaseRepositoryListener listener;

    public interface FireBaseRepositoryListener {

        void OnSignInSuccessful();

        void OnSignUpSuccessful(User user);

        void OnUserExists(boolean isExists);

        void OnQuestionsReceived(List<Question> questions);

        void OnRealtimeUserReceived(User user);

        void OnCredentialsChanged();
    }

    @Inject
    public FirebaseRepository(DatabaseReference db, FirebaseAuth auth) {
        mDatabase = db;
        mFirebaseAuth = auth;
    }

    public void saveUser(User user) {

        if (mFirebaseAuth.getCurrentUser() != null) {

            mDatabase
                    .child("users")
                    .child(mFirebaseAuth.getCurrentUser().getUid()).setValue(user);
        }



//            mFirebaseAuth.getCurrentUser().updateEmail(mFirebaseAuth.getCurrentUser().getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if (task.isSuccessful()) {
//                        if (listener != null) {
//                            listener.OnCredentialsChanged();
//                        }
//
//                    }
//                    else {
//                        Exception e = task.getException();
//                        if (e instanceof FirebaseAuthRecentLoginRequiredException) {
//                            // navigate to login page
//                        }
//                    }
//                }
//            });


    }

    public void emailPasswordSignIn(String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null) {
                        if (listener != null) {
                            listener.OnSignInSuccessful();
                        }
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

    private void signUpWithEmailAndPassword(User user, String password) {
        mFirebaseAuth.createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                if (listener != null) {
                                    listener.OnSignUpSuccessful(user);
                                }
                            }
                        }
                    }
                });
    }

    public void signUp(User user, String password) {
        signUpWithEmailAndPassword(user, password);
    }

//    public void updateUserPicture(picture){
////        FirebaseStorage storage;
////        StorageReference storageReference;
////
////        storage = FirebaseStorage.getInstance();
////        storageReference = storage.getReference();
////
////        StorageReference ref
////                = storageReference
////                .child(
////                        "images/"
////                                + UUID.randomUUID().toString());
////
////        ref.putf
//    }

    public void isUserExists(String email) {
        mFirebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean isExists = !task.getResult().getSignInMethods().isEmpty();

                if (listener != null) {
                    listener.OnUserExists(isExists);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("markomarko", "onFailure: failed  " + e.getMessage());
            }
        });
    }

    public void registerUserInRealtime(User user) {
        mDatabase.child("users").child(mFirebaseAuth.getCurrentUser().getUid()).setValue(user);
    }

    public void getQuestions() {
        mDatabase.child("questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Question> questions = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    questions.add(dataSnapshot.getValue(Question.class));
                }

                Log.d("TAG", "onDataChange: " + questions.toString());

                if (listener != null) {
                    listener.OnQuestionsReceived(questions);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setListener(FireBaseRepositoryListener listener) {
        this.listener = listener;
    }

    public void getRealtimeUser() {
        mDatabase.child("users").child(mFirebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (listener != null) {
                    listener.OnRealtimeUserReceived(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
