package com.teamdui.profiler.ui.dailycalorie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.teamdui.profiler.databinding.FragmentDailycalorieBinding;

public class DailyCalorieFragment extends Fragment {

    private DailyCalorieViewModel dailyCalorieViewModel;
    private FragmentDailycalorieBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dailyCalorieViewModel =
                new ViewModelProvider(this).get(DailyCalorieViewModel.class);

        binding = FragmentDailycalorieBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDailycalorie;

        dailyCalorieViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }
}