package com.example.android_2_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.android_2_final_project.fragments.CarDetailsFragment;
import com.example.android_2_final_project.fragments.ExploreFragment;
import com.example.android_2_final_project.fragments.LoginPageFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

//    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private LoginPageFragment loginPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d("markomarko", "onAuthStateChanged: ");
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Log.d("markomarko", "onAuthStateChanged: " + user.getEmail());
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();
        }

        FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
    }

//    @Override
//    public void onLoginSuccess() {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .remove(loginPageFragment)
//                .commit();
//
//        getSupportFragmentManager().popBackStack();
//    }
}