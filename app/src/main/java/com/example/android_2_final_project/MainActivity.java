package com.example.android_2_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.android_2_final_project.fragments.AddPostDialogFragment;
import com.example.android_2_final_project.fragments.ChatFragment;
import com.example.android_2_final_project.viewmodels.AuthenticationViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements ChatFragment.ChatFragmentListener {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private AuthenticationViewModel viewModel;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton addPostFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);

        addPostFab = findViewById(R.id.add_post_fab);
        addPostFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddPostDialog();
            }
        });

        bottomNavigationView = findViewById(R.id.nav_menu);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    addPostFab.setVisibility(View.VISIBLE);
                    viewModel.getRealtimeUserFromDB();
                } else {
                    viewModel.onSignOut();
                    bottomNavigationView.setVisibility(View.GONE);
                    addPostFab.setVisibility(View.GONE);
                }
            }
        };
    }

    private void showAddPostDialog() {
        AddPostDialogFragment dialogFragment = new AddPostDialogFragment();

        dialogFragment.show(getSupportFragmentManager(), null);
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void OnChatOpen() {
        bottomNavigationView.setVisibility(View.GONE);
        addPostFab.setVisibility(View.GONE);
    }

    @Override
    public void OnChatClose() {
        bottomNavigationView.setVisibility(View.VISIBLE);
        addPostFab.setVisibility(View.VISIBLE);
    }
}