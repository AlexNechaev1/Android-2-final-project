package com.example.android_2_final_project.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.android_2_final_project.Question;
import com.example.android_2_final_project.R;
import com.example.android_2_final_project.adapters.PagerAdapter;
import com.example.android_2_final_project.models.UserModel;
import com.example.android_2_final_project.viewmodels.AuthenticationViewModel;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpPageTwoFragment extends Fragment
        implements QuestionFragment.QuestionListener {

    private Button signUpPageTwoBtn;
    private static final java.lang.String FINISH = "finish";
    private static final java.lang.String SKIP = "skip";
    private PagerAdapter pagerAdapter;
    private ViewPager2 viewPager;
    private AuthenticationViewModel viewModel;
    private ArrayList<Question> mQuestions;
    private String[] mAnswers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_page_two, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AuthenticationViewModel.class);
        viewModel.getUser().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser user) {
                if (user != null) {
                    Navigation.findNavController(view).navigate(R.id.action_signUpPageTwoFragment_to_exploreFragment);
                }
            }
        });

        initViews(view);
        setListeners(view);

        mQuestions = (ArrayList<Question>) requireArguments().getSerializable(SignUpPageOneFragment.QUESTIONS_KEY);
        mAnswers = new String[mQuestions.size()];

        pagerAdapter = new PagerAdapter(SignUpPageTwoFragment.this, mQuestions.size(), mQuestions, this);
        viewPager.setAdapter(pagerAdapter);
    }

    private void initViews(View view) {
        viewPager = view.findViewById(R.id.pager);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                signUpPageTwoBtn.setText((position == mQuestions.size() - 1) ? FINISH : SKIP);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        signUpPageTwoBtn = view.findViewById(R.id.signup_page_two_btn);
    }

    private void setListeners(View view) {
        signUpPageTwoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (signUpPageTwoBtn.getText().toString()) {
                    case SKIP:
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        break;
                    case FINISH:
                        UserModel user = (UserModel) requireArguments().getSerializable(SignUpPageOneFragment.USER_KEY);
                        String password = (String) requireArguments().getSerializable(SignUpPageOneFragment.PASSWORD_KEY);
                        user.setAnswers(Arrays.asList(mAnswers));

                        viewModel.signUp(user, password);
                        // TODO: maybe show spinner while registering user
                        break;
                }
            }
        });
    }

    @Override
    public void OnAnswerSelected(String answer) {
        int position = viewPager.getCurrentItem();

        String answerToInsert = answer != null ? answer : "";

        mAnswers[position] = answerToInsert;
        if (position != mQuestions.size() - 1)
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1,true);
    }
}
