package com.example.android_2_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android_2_final_project.fragments.LoginPageFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.root,new LoginPageFragment()).commit();

    }
}