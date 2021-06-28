package com.example.android_2_final_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_2_final_project.R;
import com.example.android_2_final_project.adapters.ExploreRecyclerViewAdapter;
import com.example.android_2_final_project.models.PostModel;
import com.example.android_2_final_project.viewmodels.ExploreViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExploreFragment extends Fragment implements ExploreRecyclerViewAdapter.ItemClickListener {

    public static final String POSTS_KEY = "explore.posts";
    private List<PostModel> exploreCarList = new ArrayList<>();
    private ExploreRecyclerViewAdapter mAdapter;

    private ExploreViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ExploreRecyclerViewAdapter(getActivity(), exploreCarList);
        mAdapter.setClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            // Navigate to carDetailsFragment
            PostModel post = exploreCarList.get(position);

            Bundle bundle = new Bundle();
            bundle.putSerializable(POSTS_KEY, post);

            Navigation.findNavController(view).navigate(R.id.action_exploreFragment_to_carDetailsFragment, bundle);
        } else {
            // Navigate to loginFragment
            Navigation.findNavController(view).navigate(R.id.action_exploreFragment_to_loginPageFragment);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_explorer_page, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.explorer_list_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        viewModel = new ViewModelProvider(this).get(ExploreViewModel.class);
        viewModel.getPost().observe(getViewLifecycleOwner(), new Observer<PostModel>() {
            @Override
            public void onChanged(PostModel post) {
                exploreCarList.add(post);
                mAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getRealtimePosts();

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }
}
