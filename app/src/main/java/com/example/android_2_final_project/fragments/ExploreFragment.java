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
import com.example.android_2_final_project.adapters.ExploreRecyclerViewAdapter;
import com.example.android_2_final_project.models.Car;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ExploreFragment extends Fragment implements ExploreRecyclerViewAdapter.ItemClickListener {

    private ArrayList<Car> exploreCarList;
    private ExploreRecyclerViewAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exploreCarList = new ArrayList<>();
        updateList();
        updateList();
        updateList();
        updateList();
        updateList();
        updateList();
        updateList();
        updateList();
        updateList();
        updateList();


        mAdapter = new ExploreRecyclerViewAdapter(getActivity(), exploreCarList);
        mAdapter.setClickListener(this);
    }


    @Override
    public void onItemClick(View view, int position) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            // Navigate to carDetailsFragment
            Car car = exploreCarList.get(position);

            Bundle bundle = new Bundle();
            bundle.putSerializable("car", car); // TODO: make key static

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

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }


    private void updateList() {

        for (int i = 0; i < 9; i++) {
            exploreCarList.add(new Car(
                    "https://loremflickr.com/60" + i + "/500/car",
                    "car model",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam sed suscipit libero. Quisque malesuada mattis augue in tempus. Morbi massa tortor, feugiat at pretium eget, maximus in elit. Aliquam id sagittis eros. Nam pretium vel tortor eu auctor. Nam orci nisl, facilisis sit amet elementum a, tincidunt vel risus. Etiam maximus in quam in commodo. Phasellus a justo sed nunc molestie egestas ac quis sem. Phasellus dignissim euismod suscipit. Nulla sit amet aliquam ante. Cras fermentum neque sed tincidunt bibendum.\n" +
                            "\n" +
                            "Sed interdum augue ut est laoreet commodo vehicula et felis. Pellentesque pharetra varius tortor, sed finibus odio egestas nec. Phasellus sagittis risus eget posuere tincidunt. Ut eros lacus, ultrices sit amet nulla non, feugiat congue ipsum. Curabitur dui orci, ultricies sed dui eu, finibus pulvinar neque. Maecenas sagittis mollis arcu, sit amet maximus felis varius vel. Praesent luctus lectus quis est elementum, eleifend commodo augue malesuada. Pellentesque sit amet sem dolor.\n" +
                            "\n" +
                            "Vivamus sit amet est ut tortor porta congue at quis purus. Morbi mattis erat egestas, scelerisque lectus ut, ultricies sapien. Morbi imperdiet, enim sit amet ultricies mollis, lectus augue eleifend metus, in sodales erat massa et lacus. Proin purus massa, congue et eros congue, pellentesque facilisis elit. Nullam volutpat lectus et sapien scelerisque consequat. Fusce lobortis pharetra dolor, et gravida nisl malesuada at. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam lacinia nibh a dui venenatis, sit amet varius tellus efficitur. Quisque accumsan orci at eros eleifend gravida. In aliquam enim nec libero rhoncus euismod. Praesent risus tellus, dictum nec tortor a, congue interdum sem. Interdum et malesuada fames ac ante ipsum primis in faucibus. Vestibulum faucibus, libero quis vehicula dignissim, risus diam ornare purus, ac vulputate dolor elit at ligula. In in ligula id nulla porta fringilla eget eu dui. Aliquam erat volutpat. Suspendisse potenti."
                    ,Integer.parseInt("199"+i)
            ));
        }
    }
}
