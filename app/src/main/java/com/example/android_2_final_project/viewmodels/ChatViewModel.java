package com.example.android_2_final_project.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_2_final_project.models.ChatModel;
import com.example.android_2_final_project.repository.ChatRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChatViewModel extends ViewModel {

    private final ChatRepository chatRepository;

    @Inject
    public ChatViewModel(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }
}
