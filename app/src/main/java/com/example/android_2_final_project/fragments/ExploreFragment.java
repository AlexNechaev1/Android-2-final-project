package com.example.android_2_final_project.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_2_final_project.ExploreCellData;
import com.example.android_2_final_project.R;
import com.example.android_2_final_project.adapters.RecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ExploreFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {

    public interface ExploreListener {
        void onCardClicked(int position);
    }

    public ExploreFragment newInstance() {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    private ExploreListener exploreListener;
    private ArrayList<ExploreCellData> exploreCarList;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            this.exploreListener = (ExploreListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement ExploreListener interface");
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exploreCarList = new ArrayList<>();
        updateList();
    }


    @Override
    public void onItemClick(View view, int position) {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_explorer_page, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.explorer_list_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), exploreCarList);

        adapter.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                exploreListener.onCardClicked(position);



            }
        });

        recyclerView.setAdapter(adapter);

        return rootView;
    }


    private void updateList() {

        for (int i = 0; i < 30; i++) {
            exploreCarList.add(new ExploreCellData(
                    "Title" + (i + 1),
                    "first description",
                    "second description"));
        }
    }
}
