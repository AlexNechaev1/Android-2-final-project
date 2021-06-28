package com.example.android_2_final_project.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_2_final_project.models.PostModel;
import com.example.android_2_final_project.repository.ExploreRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ExploreViewModel extends ViewModel implements ExploreRepository.ExploreRepositoryListener {

    private final List<PostModel> mPostsList = new ArrayList<>();

    private final MutableLiveData<List<PostModel>> mPosts = new MutableLiveData<>();

    private final MutableLiveData<PostModel> mPost = new MutableLiveData<>();

    private final ExploreRepository mExploreRepository;

    @Inject
    public ExploreViewModel(ExploreRepository exploreRepository) {
        mExploreRepository = exploreRepository;

        mExploreRepository.setListener(this);
    }

    @Override
    public void OnPostAdded(PostModel post) {
//        mPostsList.add(post);
//        mPosts.setValue(mPostsList);

        mPost.setValue(post);
    }

    public LiveData<List<PostModel>> getPosts() {
        return mPosts;
    }

    public void getRealtimePosts() {
        mExploreRepository.getRealtimePosts();
    }

    public LiveData<PostModel> getPost () {
        return mPost;
    }
}
