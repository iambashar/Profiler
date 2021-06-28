package com.teamdui.profiler.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.teamdui.profiler.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    public String s;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        s = "On create view";
        return root;
    }

    public void onAttach() {
        s = "OnAttach";
    }

    public void onCreate() {
        s = "On Create";
    }

    public void onViewCreated() {
        s = "On View Created";
    }

    public void onActivityCreated() {
        s = "On Activity Created";
    }


    @Override
    public void onStart() {
        super.onStart();
        s = "On Start";
    }

    @Override
    public void onResume() {
        super.onResume();
        s = "On Resume";
    }

    @Override
    public void onPause() {
        super.onPause();
        s = "On Pause";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        s = "on Destroy View";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        s = "on destroy";

    }

    @Override
    public void onDetach() {
        super.onDetach();
        s = "On Detach";
    }
}