package com.example.android_2_final_project.fragments;

import android.os.Bundle;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpPageOneFragment extends Fragment {

    private Button mSignUpBtn;
    private TextInputEditText mPassword1Tv;
    private TextInputEditText mPassword2Tv;
    private TextInputEditText mEmailTv;
    private TextInputEditText mUsernameTv;
    private AuthenticationViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_page_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AuthenticationViewModel.class);

        viewModel.getUser().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser!=null){
                    Navigation.findNavController(view).navigate(R.id.action_signupPageOne_to_signupPageTwo);
                }
            }
        });

        initViews(view);

        setListeners();
    }

    private void initViews(View v) {
        mSignUpBtn = v.findViewById(R.id.signup_page_signup_btn);
        mUsernameTv = v.findViewById(R.id.signup_page_username_edit_text);
        mEmailTv = v.findViewById(R.id.signup_page_email_edit_text);
        mPassword1Tv = v.findViewById(R.id.signup_page_password_edit_text);
        mPassword2Tv = v.findViewById(R.id.signup_page_repeat_password_edit_text);
    }

    private void setListeners() {



        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameTv.getText().toString();
                String email = mEmailTv.getText().toString();
                String password1 = mPassword1Tv.getText().toString();
                String password2 = mPassword2Tv.getText().toString();

                if (!username.trim().isEmpty() && !email.trim().isEmpty() && !email.trim().isEmpty() && !password2.trim().isEmpty()) {
                    if(password1.equals(password2)){
                        viewModel.signUp(email,password1);
                    }else{
                        //Password doesn't match
                        Snackbar.make(v,getString(R.string.missMatchPassword),Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Snackbar.make(v, getString(R.string.provide_email_and_password), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}
