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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.android_2_final_project.Question;
import com.example.android_2_final_project.R;
import com.example.android_2_final_project.adapters.PagerAdapter;
import com.example.android_2_final_project.viewmodels.AuthenticationViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpPageTwoFragment extends Fragment {

    private Button signUpPageTwoBtn;
    private static final String FINISH = "finish";
    private static final String SKIP = "skip";
    private PagerAdapter pagerAdapter;
    private ViewPager2 viewPager;
    private AuthenticationViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_page_two, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AuthenticationViewModel.class);

        initViews(view);
        setListeners(view);

    }

    private void initViews(View view) {

        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("Hey", "Hey", "Hello", "Shalom"));
        questions.add(new Question("Bye", "Bye", "ByeBye", "Shalom"));
        questions.add(new Question("Car?", "Yes", "No", "Maybe"));
        questions.add(new Question("Hey", "Hey", "Hello", "Shalom"));
        questions.add(new Question("Bye", "Bye", "ByeBye", "Shalom"));

        viewPager = view.findViewById(R.id.pager);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                signUpPageTwoBtn.setText((position == questions.size() - 1) ? FINISH : SKIP);
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

        pagerAdapter = new PagerAdapter(this, questions.size(), questions);

        viewPager.setAdapter(pagerAdapter);
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
                        String username = getArguments().getString(SignUpPageOneFragment.USERNAME_KEY,"");
                        String password = getArguments().getString(SignUpPageOneFragment.PASSWORD_KEY,"");
                        String email = getArguments().getString(SignUpPageOneFragment.EMAIL_KEY,"");



                        Navigation.findNavController(v).navigate(R.id.action_signUpPageTwoFragment_to_exploreFragment);
                        break;
                }
            }
        });
    }
}
