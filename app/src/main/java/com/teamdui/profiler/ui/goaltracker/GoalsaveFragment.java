package com.teamdui.profiler.ui.goaltracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teamdui.profiler.R;


public class GoalsaveFragment extends Fragment {



    public GoalsaveFragment() {

    }


    public static GoalsaveFragment newInstance() {
        GoalsaveFragment fragment = new GoalsaveFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goalsave, container, false);

    }
}