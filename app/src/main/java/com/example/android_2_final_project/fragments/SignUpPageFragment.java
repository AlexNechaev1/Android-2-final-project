package com.example.android_2_final_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_2_final_project.R;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpPageFragment extends Fragment {

    private Button mSignUpBtn;
    private TextInputEditText mPassword1Tv;
    private TextInputEditText mPassword2Tv;
    private TextInputEditText mEmailTv;
    private TextInputEditText mUsernameTv;

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

        initViews(view);

        setListeners();
    }

    private void initViews(View v) {
        mSignUpBtn = v.findViewById(R.id.signup_page_signup_btn);
        mPassword1Tv = v.findViewById(R.id.signup_page_password_edit_text);
        mPassword2Tv = v.findViewById(R.id.signup_page_repeat_password_edit_text);
        mEmailTv = v.findViewById(R.id.signup_page_email_edit_text);
        mUsernameTv = v.findViewById(R.id.signup_page_username_edit_text);
    }

    private void setListeners() {

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
