package com.example.android_2_final_project.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_2_final_project.Question;
import com.example.android_2_final_project.repository.FirebaseRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthenticationViewModel extends ViewModel
        implements FirebaseRepository.FireBaseRepositoryListener {

    private final FirebaseRepository mFirebaseRepository;

    private final MutableLiveData<FirebaseUser> mUser = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Question>> mQuestions = new MutableLiveData<>();

    private AuthListener listener;
    public interface AuthListener {
        void OnCheckUserExists(boolean isExists);
    }

    public void setListener(AuthListener listener) {
        this.listener = listener;
    }

    @Inject
    public AuthenticationViewModel(FirebaseRepository repository) {
        this.mFirebaseRepository = repository;

        mFirebaseRepository.setListener(this);
    }

    public LiveData<FirebaseUser> getUser() {
        return mUser;
    }

    public void checkIsUserExists(java.lang.String email) {
        mFirebaseRepository.isUserExists(email);
    }

    public void signIn(java.lang.String email, java.lang.String password) {
        mFirebaseRepository.emailPasswordSignIn(email, password);
    }

    public void signUp(java.lang.String email, java.lang.String password) {
        mFirebaseRepository.signUp(email, password);
    }

    public LiveData<ArrayList<Question>> getQuestions () {
        return mQuestions;
    }

    @Override
    public void OnSignInSuccessful(FirebaseUser user) {
        mUser.setValue(user);
    }

    @Override
    public void OnSignUpSuccessful(FirebaseUser user) {
        mUser.setValue(user);

        mFirebaseRepository.registerUserInRealtime(user);
    }

    @Override
    public void OnUserExists(boolean isExists) {
        listener.OnCheckUserExists(isExists);
    }

    //    public void signIn(String email, String password) {
//        emailPasswordSignIn(email, password);
//    }

//    private void emailPasswordSignIn(String email, String password) {
//        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    if (task.getResult() != null) {
//                        user.setValue(task.getResult().getUser());
//
//                    }
//                }
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("TAG", "onFailure: " + e.getMessage());
//            }
//        });
//    }
}
