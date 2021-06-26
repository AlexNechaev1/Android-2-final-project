package com.example.android_2_final_project.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.android_2_final_project.Question;
import com.example.android_2_final_project.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FirebaseRepository {

    private final DatabaseReference mDatabase;
    private final FirebaseAuth mFirebaseAuth;

    private FireBaseRepositoryListener listener;

    public interface FireBaseRepositoryListener {

        void OnSignInSuccessful(FirebaseUser user);

        void OnSignUpSuccessful(FirebaseUser firebaseUser, User user);

        void OnUserExists(boolean isExists);

        void OnQuestionsReceived(List<Question> questions);

        void OnRealtimeUserReceived(User user);
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
                        if (listener != null) {
                            listener.OnSignInSuccessful(task.getResult().getUser());
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
                                    listener.OnSignUpSuccessful(task.getResult().getUser(), user);
                                }
                            }
                        }
                    }
                });
    }

    public void signUp(User user, String password) {
        signUpWithEmailAndPassword(user, password);
    }

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
                if(listener != null){
                    listener.OnRealtimeUserReceived(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
