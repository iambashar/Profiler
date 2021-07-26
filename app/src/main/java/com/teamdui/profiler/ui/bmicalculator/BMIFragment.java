package com.teamdui.profiler.ui.bmicalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.teamdui.profiler.databinding.FragmentBmicalculatorBinding;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BMIFragment extends Fragment {

    private BMIViewModel bmiViewModel;
    private FragmentBmicalculatorBinding binding;

    public EditText weightInput;
    public EditText heightInput;
    public TextView BMIText;
    public Button getBMIbtn;
    public Button resetbtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bmiViewModel =
                new ViewModelProvider(this).get(BMIViewModel.class);

        binding = FragmentBmicalculatorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        weightInput = binding.weightInput;
        heightInput = binding.heightInput;
        BMIText = binding.bmiText;
        getBMIbtn = binding.getBMIButton;
        resetbtn = binding.resetBMIButton;

        getBMIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float weight = Float.parseFloat(weightInput.getText().toString());
                float height =Float.parseFloat(heightInput.getText().toString());
                float bmi = weight / (height * height);
                bmi = Math.round((bmi * 100));
                bmi = bmi / 100;
                BMIText.setText("BMI: " + String.valueOf(bmi));
                resetbtn.setVisibility(View.VISIBLE);
                getBMIbtn.setVisibility(View.INVISIBLE);
            }
        });
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightInput.getText().clear();
                heightInput.getText().clear();
                getBMIbtn.setVisibility(View.VISIBLE);
                resetbtn.setVisibility(View.INVISIBLE);
                BMIText.setText("");
            }
        });
        bmiViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

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