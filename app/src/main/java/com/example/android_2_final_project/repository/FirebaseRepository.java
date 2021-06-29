package com.example.android_2_final_project.repository;

import androidx.annotation.NonNull;

import com.example.android_2_final_project.Question;
import com.example.android_2_final_project.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FirebaseRepository {

    private final DatabaseReference mDatabase;
    private final FirebaseAuth mFirebaseAuth;

    private FireBaseRepositoryListener listener;

    public interface FireBaseRepositoryListener {

        void OnSignInSuccessful();

        void OnSignUpSuccessful(UserModel user);

        void OnUserExists(boolean isExists);

        void OnQuestionsReceived(List<Question> questions);

        void OnRealtimeUserReceived(UserModel user);

        void OnCredentialsChanged();

        void OnRealtimeSellerReceived(UserModel seller);
    }

    @Inject
    public FirebaseRepository(DatabaseReference db, FirebaseAuth auth) {
        mDatabase = db;
        mFirebaseAuth = auth;
    }

    /**
     * Saves a user instance of User Model in Realtime DB
     *
     * @param user User Model instance to save in Realtime DB
     */
    public void saveUser(UserModel user) {
        if (mFirebaseAuth.getCurrentUser() != null) {
            mDatabase.child("users")
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

            }
        });
    }

    private void signUpWithEmailAndPassword(UserModel user, String password) {
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

    public void signUp(UserModel user, String password) {
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

            }
        });
    }

    public void registerUserInRealtime(UserModel user) {
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
                UserModel user = snapshot.getValue(UserModel.class);

                if (listener != null) {
                    listener.OnRealtimeUserReceived(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getRealtimeUserByUID(String UID) {
        mDatabase.child("users").child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel seller = snapshot.getValue(UserModel.class);

                if (listener != null) {
                    listener.OnRealtimeSellerReceived(seller);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
