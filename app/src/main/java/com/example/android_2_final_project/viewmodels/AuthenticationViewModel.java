package com.example.android_2_final_project.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_2_final_project.Question;
import com.example.android_2_final_project.models.UserModel;
import com.example.android_2_final_project.repository.FirebaseRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthenticationViewModel extends ViewModel
        implements FirebaseRepository.FireBaseRepositoryListener {

    private final FirebaseRepository mFirebaseRepository;

    private final MutableLiveData<FirebaseUser> mUser = new MutableLiveData<>();

    private final MutableLiveData<UserModel> mRealtimeUser = new MutableLiveData<>();

    private final MutableLiveData<UserModel> mSeller = new MutableLiveData<>();

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

    public void signUp(UserModel user, java.lang.String password) {
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
    public void OnSignUpSuccessful(UserModel user) {
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
    public void OnRealtimeUserReceived(UserModel user) {
        mRealtimeUser.setValue(user);
    }

    @Override
    public void OnCredentialsChanged() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void OnRealtimeSellerReceived(UserModel seller) {
        mSeller.setValue(seller);
    }

    public LiveData<UserModel> getSeller() {
        return mSeller;
    }

    public void getUserByUID(String UID){
        mFirebaseRepository.getRealtimeUserByUID(UID);
    }

    public LiveData<UserModel> getRealtimeUser() {
        return mRealtimeUser;
    }

    public void saveRealTimeUser(UserModel user) {
        mRealtimeUser.setValue(user);
        mFirebaseRepository.saveUser(user);
    }


    public void getRealtimeUserFromDB() {
        mFirebaseRepository.getRealtimeUser();
    }

    public void saveUser(UserModel user) {
        mUser.setValue(null);
        mFirebaseRepository.saveUser(user);
    }

    public void onSignOut() {
        mUser.setValue(null);
    }
}
