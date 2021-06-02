package com.example.android_2_final_project.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_2_final_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPageFragment extends Fragment {


    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authStateListener;
    Button mLoginBtn;
    TextInputEditText mEmailEt;
    TextInputEditText mPasswordEt;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Log.d("markomarko", "onClick: user is not null " + FirebaseAuth.getInstance().getCurrentUser().getEmail());

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login_page, container, false);

        mEmailEt = rootView.findViewById(R.id.login_page_username_edit_text);
        mPasswordEt = rootView.findViewById(R.id.login_page_password_edit_text);
        mLoginBtn = rootView.findViewById(R.id.login_page_login_btn);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String email = mEmailEt.getText().toString();
                String password = mPasswordEt.getText().toString();

                if (!email.trim().isEmpty() && !password.trim().isEmpty()) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Snackbar.make(view, "Not Fuck you", Snackbar.LENGTH_SHORT).show();

                        }
                    });
                }
                else {
                    Snackbar.make(view, "Fuck you", Snackbar.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    public void onStop() {
        super.onStop();

        FirebaseAuth.getInstance().signOut();
    }
}
