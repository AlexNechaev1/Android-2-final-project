package com.example.android_2_final_project.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_2_final_project.Question;
import com.example.android_2_final_project.models.User;
import com.example.android_2_final_project.repository.FirebaseRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthenticationViewModel extends ViewModel
        implements FirebaseRepository.FireBaseRepositoryListener {

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

    public void signIn(String email, String password) {
        mFirebaseRepository.emailPasswordSignIn(email, password);
    }

    public void signUp(User user, java.lang.String password) {
        mFirebaseRepository.signUp(user, password);
    }

    public void getQuestions() {
        mFirebaseRepository.getQuestions();
    }

    @Override
    public void OnSignInSuccessful() {
        mUser.setValue(FirebaseAuth.getInstance().getCurrentUser());
    }

    @Override
    public void OnSignUpSuccessful(User user) {
        mUser.setValue(FirebaseAuth.getInstance().getCurrentUser());
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

    @Override
    public void OnCredentialsChanged() {
        FirebaseAuth.getInstance().signOut();
    }

    public LiveData<User> getRealtimeUser() {
        return mRealtimeUser;
    }


    public void getRealtimeUserFromDB() {
        mFirebaseRepository.getRealtimeUser();
    }

    public void saveUser(User user) {
        mUser.setValue(null);
        mFirebaseRepository.saveUser(user);
    }

    public void onSignOut() {
        mUser.setValue(null);
        // consider nulling out the realtime info of the user...
    }

//    public void updateUserPicture(picture){
//        mFirebaseRepository.updateUserPicture(picture);
//    }
}
