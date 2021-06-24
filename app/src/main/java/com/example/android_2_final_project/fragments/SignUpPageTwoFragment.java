package com.example.android_2_final_project.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.android_2_final_project.Question;
import com.example.android_2_final_project.R;
import com.example.android_2_final_project.adapters.PagerAdapter;

import java.util.ArrayList;


public class SignUpPageTwoFragment extends Fragment {

    public SignUpPageTwoFragment(){};

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("Hey","Hey","Hello","Shalom"));
        questions.add(new Question("Bye","Bye","ByeBye","Shalom"));
        questions.add(new Question("Car?","Yes","No","Maybe"));
        questions.add(new Question("Hey","Hey","Hello","Shalom"));
        questions.add(new Question("Bye","Bye","ByeBye","Shalom"));
        questions.add(new Question("Car?","Yes","No","Maybe"));
        questions.add(new Question("Hey","Hey","Hello","Shalom"));
        questions.add(new Question("Bye","Bye","ByeBye","Shalom"));
        questions.add(new Question("Car?","Yes","No","Maybe"));
        questions.add(new Question("Hey","Hey","Hello","Shalom"));
        questions.add(new Question("Bye","Bye","ByeBye","Shalom"));
        questions.add(new Question("Car?","Yes","No","Maybe"));

        View v = inflater.inflate(R.layout.fragment_signup_page_two,container,false);

        ViewPager2 viewPager = v.findViewById(R.id.pager);

        PagerAdapter pagerAdapter = new PagerAdapter(this,questions.size(),questions);

        viewPager.setAdapter(pagerAdapter);

        return v;
    }
}
