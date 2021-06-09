package com.example.android_2_final_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_2_final_project.R;
import com.example.android_2_final_project.adapters.RecyclerViewAdapter;
import com.example.android_2_final_project.models.Car;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ExploreFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {

    private ArrayList<Car> exploreCarList;
    private RecyclerViewAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exploreCarList = new ArrayList<>();
        updateList();

        mAdapter = new RecyclerViewAdapter(getActivity(), exploreCarList);
        mAdapter.setClickListener(this);
    }


    @Override
    public void onItemClick(View view, int position) {
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            // Navigate to carDetailsFragment
            Car car = exploreCarList.get(position);

            Bundle bundle = new Bundle();
            bundle.putSerializable("car", car); // TODO: make key static

            Navigation.findNavController(view).navigate(R.id.action_exploreFragment_to_carDetailsFragment, bundle);
        }
        else {
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

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }


    private void updateList() {

        for (int i = 0; i < 30; i++) {
            exploreCarList.add(new Car(
                    "https://i0.wp.com/pdlv.fr/wp-content/uploads/2020/05/steve-apige-dickmobile.jpg",
                    "car model " + "199" + i,
                    "some desc",
                    i
                    ));
        }
    }
}
