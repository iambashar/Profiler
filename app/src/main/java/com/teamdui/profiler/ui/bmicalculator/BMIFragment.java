package com.teamdui.profiler.ui.bmicalculator;

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

import com.teamdui.profiler.databinding.FragmentBmicalculatorBinding;

public class BMIFragment extends Fragment {

    private BMIViewModel bmiViewModel;
    private FragmentBmicalculatorBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bmiViewModel =
                new ViewModelProvider(this).get(BMIViewModel.class);

        binding = FragmentBmicalculatorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textBmi;
        bmiViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}