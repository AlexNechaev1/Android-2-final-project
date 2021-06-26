package com.example.android_2_final_project.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.android_2_final_project.Question;
import com.example.android_2_final_project.fragments.QuestionFragment;

import java.util.ArrayList;


public class PagerAdapter extends FragmentStateAdapter {

    private final int numOfTabs;
    private final ArrayList<Question> questions;
    private QuestionFragment.QuestionListener listener;

    public PagerAdapter(@NonNull Fragment fragment,
                        int numOfTabs,
                        ArrayList<Question> questions,
                        QuestionFragment.QuestionListener listener) {
        super(fragment);
        this.numOfTabs = numOfTabs;
        this.questions = questions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Fragment createFragment(int i) {
        QuestionFragment instance = QuestionFragment.newInstance(questions.get(i));
        instance.setListener(listener);
        return instance;
    }

    @Override
    public int getItemCount() {
        return numOfTabs;
    }
}
