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

import com.example.android_2_final_project.R;
import com.example.android_2_final_project.viewmodels.AuthenticationViewModel;
import com.google.firebase.auth.FirebaseUser;

//TODO: do better UI lol
public class SignUpPageTwoFragment extends Fragment {


    private Button mSignUpBtn;

    private AuthenticationViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_page_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewModel = new ViewModelProvider(requireActivity()).get(AuthenticationViewModel.class);

        viewModel.getUser().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Navigation.findNavController(view).navigate(R.id.action_signUpPageTwoFragment_to_exploreFragment);
                }
            }
        });

        //Navigation.findNavController(view).navigate(R.id.action_signupPageTwo_to_explorerFragment);


        initViews(view);

        setListeners();
    }

    private void initViews(View v) {
        mSignUpBtn = v.findViewById(R.id.signup_page_two_signup_btn);
    }

    private void setListeners() {
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = requireArguments();
                String email = args.getString("email");
                String password = args.getString("password");
                viewModel.signUp(email, password);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("markomarko", "onPause: sign up page two");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("markomarko", "onDestroy: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("markomarko", "onResume: ");
    }
}
