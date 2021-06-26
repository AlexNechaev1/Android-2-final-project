package com.example.android_2_final_project.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_2_final_project.Question;
import com.example.android_2_final_project.models.User;
import com.example.android_2_final_project.repository.FirebaseRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthenticationViewModel extends ViewModel
        implements FirebaseRepository.FireBaseRepositoryListener{

    private final FirebaseRepository mFirebaseRepository;

    private final MutableLiveData<FirebaseUser> mUser = new MutableLiveData<>();

    private final MutableLiveData<User> mRealtimeUser = new MutableLiveData<>();

    private AuthListener listener;

    public interface AuthListener {

        void OnCheckUserExists(boolean isExists);
        void OnQuestionsReceived(List<Question> questions);

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

    public void signUp(User user, java.lang.String password) {
        mFirebaseRepository.signUp(user, password);
    }

    public void getQuestions() {
        mFirebaseRepository.getQuestions();
    }

    @Override
    public void OnSignInSuccessful(FirebaseUser user) {
        mUser.setValue(user);
    }

    @Override
    public void OnSignUpSuccessful(FirebaseUser firebaseUser, User user) {
        mUser.setValue(firebaseUser);

        mFirebaseRepository.registerUserInRealtime(user);
    }

    @Override
    public void OnUserExists(boolean isExists) {
        listener.OnCheckUserExists(isExists);
    }

    @Override
    public void OnQuestionsReceived(List<Question> questions) {
        listener.OnQuestionsReceived(questions);
    }

    @Override
    public void OnRealtimeUserReceived(User user) {
        mRealtimeUser.setValue(user);
    }

    public LiveData<User> getRealtimeUser(){
        return mRealtimeUser;
    }

    public void getRealtimeUserFromDB() {
        mFirebaseRepository.getRealtimeUser();
    }
}
