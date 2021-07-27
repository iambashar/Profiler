package com.teamdui.profiler.ui.dailycalorie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.teamdui.profiler.databinding.FragmentDailycalorieBinding;
import com.teamdui.profiler.ui.login.LoginActivity;

public class DailyCalorieFragment extends Fragment {

    private DailyCalorieViewModel dailyCalorieViewModel;
    private FragmentDailycalorieBinding binding;
    private Button logoutButton;
    FirebaseAuth mAuth;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dailyCalorieViewModel =
                new ViewModelProvider(this).get(DailyCalorieViewModel.class);

        binding = FragmentDailycalorieBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        logoutButton = binding.logoutButton;

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        final TextView textView = binding.textDailycalorie;

        dailyCalorieViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }
    private void logoutUser(){
        mAuth.signOut();
        Intent logoutIntent = new Intent(this.getActivity(), LoginActivity.class);
        startActivity(logoutIntent);
        getActivity().finish();
    }
}