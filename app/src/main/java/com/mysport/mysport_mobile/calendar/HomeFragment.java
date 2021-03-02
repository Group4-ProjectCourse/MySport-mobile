package com.mysport.mysport_mobile.calendar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mysport.mysport_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements SportsAdapter.OnRecyclerItemClick {

    private RecyclerView recyclerView;
    private SportsAdapter sportsAdapter;
    private List<SportsModel> sportsList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        sportsList = new ArrayList<>(4);

        createSport(
                getResources().getString(R.string.sport_name_example),
                getResources().getString(R.string.sport_time_example),
                getResources().getString(R.string.participants_example)
        );
        createSport(
                getResources().getString(R.string.sport_name_example1),
                getResources().getString(R.string.sport_time_example1),
                getResources().getString(R.string.participants_example)
        );
        createSport(
                getResources().getString(R.string.sport_name_example2),
                getResources().getString(R.string.sport_time_example2),
                getResources().getString(R.string.participants_example)
        );
        createSport(
                getResources().getString(R.string.sport_name_example3),
                getResources().getString(R.string.sport_time_example3),
                getResources().getString(R.string.participants_example)
        );
        createSport(
                getResources().getString(R.string.sport_name_example4),
                getResources().getString(R.string.sport_time_example4),
                getResources().getString(R.string.participants_example)
        );
        createSport(
                getResources().getString(R.string.sport_name_example5),
                getResources().getString(R.string.sport_time_example5),
                getResources().getString(R.string.participants_example)
        );

        sportsAdapter = new SportsAdapter(sportsList, getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(sportsAdapter);
        sportsAdapter.setOnRecyclerItemClick(this);

        return view;
    }

    public void createSport(String sportName, String time, String participants){
        sportsList.add(new SportsModel()
                .setSportName(sportName)
                .setTime(time)
                .setParticipants(participants)
        );

        //for setting icons:
        //sportsModel.setView(PASS ICONS HERE e.g.: )
    }

    @Override
    public void onClick(int pos) {
        Toast.makeText(getContext(), "Pos is: " + pos, Toast.LENGTH_SHORT).show();
    }
}