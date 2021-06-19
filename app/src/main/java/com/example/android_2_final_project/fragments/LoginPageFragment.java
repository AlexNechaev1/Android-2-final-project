package com.example.android_2_final_project.fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.android_2_final_project.R;
import com.example.android_2_final_project.viewmodels.AuthenticationViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginPageFragment extends Fragment {

    private Button mLoginBtn;
    private TextInputEditText mEmailEt;
    private TextInputEditText mPasswordEt;
    private ImageView mLogo;
    private Animation animSlide;
    private Animation progressBarSlideDown;
    private Button mNewUserBtn;
    private ProgressBar mProgressBar;
    private TextWatcher mEmailWatcher;
    private TextWatcher mPasswordWatcher;

    private AuthenticationViewModel authenticationViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressBarSlideDown = AnimationUtils.loadAnimation(getContext(), R.anim.progress_slide_down);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login_page, container, false);

        mEmailEt = rootView.findViewById(R.id.login_page_username_edit_text);
        mPasswordEt = rootView.findViewById(R.id.login_page_password_edit_text);
        mLoginBtn = rootView.findViewById(R.id.login_page_login_btn);
        mLogo = rootView.findViewById(R.id.login_logo);
        mNewUserBtn = rootView.findViewById(R.id.login_page_new_user_btn);
        animSlide = AnimationUtils.loadAnimation(getContext(), R.anim.car_slide_left);
        mLogo.startAnimation(animSlide);
        mProgressBar = rootView.findViewById(R.id.login_page_progress_bar);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(view);
        authenticationViewModel = new ViewModelProvider(requireActivity()).get(AuthenticationViewModel.class);
        authenticationViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    hideProgressBar();
//                    NavController navController = navHostFragment.getNavController();
//                    navController.popBackStack();
                    navController.popBackStack();
                }

            }
        });

        setListeners(view);

        mEmailWatcher = setInputWatcher(mEmailEt);
        mPasswordWatcher = setInputWatcher(mPasswordEt);

        mEmailEt.addTextChangedListener(mEmailWatcher);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mEmailEt != null && mPasswordEt != null) {

            mEmailEt.removeTextChangedListener(mEmailWatcher);
            mEmailEt.removeTextChangedListener(mPasswordWatcher);
            mEmailEt.setText("");
            mPasswordEt.setText("");
        }
    }

    private void setListeners(View view) {

//        navHostFragment = (NavHostFragment) requireActivity()
//                .getSupportFragmentManager()
//                .findFragmentById(R.id.nav_host_fragment);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmailEt.getText().toString();
                String password = mPasswordEt.getText().toString();

                if (!email.trim().isEmpty() && !password.trim().isEmpty()) {

                    mProgressBar.startAnimation(progressBarSlideDown);
                    mProgressBar.setVisibility(View.VISIBLE);

                    authenticationViewModel.signIn(email, password);
//                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//
//                            hideProgressBar();
//
//                            FirebaseUser user = task.getResult().getUser();
//                            if (task.isSuccessful()) {
//                                // Navigate to exploreFragment
//                                NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
//                                        .getSupportFragmentManager()
//                                        .findFragmentById(R.id.nav_host_fragment);
//
//                                NavController navController = navHostFragment.getNavController();
//
//                                navController.popBackStack();
//                            }
//                        }
//                    });
                }
                else {
                    Snackbar.make(view, getString(R.string.provide_email_and_password), Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        mNewUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginPageFragment_to_signUpPageOneFragment);
            }
        });
    }

    private void hideProgressBar() {
        mProgressBar.clearAnimation();
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private TextWatcher setInputWatcher(TextInputEditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    editText.setError("This field is required");

                }
                else {
                    editText.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }
}
