package com.teamdui.profiler.ui.bmicalculator;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
    public TextView rangeText;
    public Button getBMIbtn;
    public Button resetbtn;
    public Button againbtn;
    public float bmi;

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
        againbtn = binding.againButton;
        rangeText = binding.rangeText;

        getBMIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weightInput.getText().toString() .isEmpty()|| heightInput.getText().toString().isEmpty())
                {
                    rangeText.setText("You must input weight and height..");
                    rangeText.setTextColor(Color.parseColor("#E05668"));
                    getBMIbtn.setVisibility(View.INVISIBLE);
                    againbtn.setVisibility(View.VISIBLE);
                }
                else
                {
                    float weight = Float.parseFloat(weightInput.getText().toString());
                    float height =Float.parseFloat(heightInput.getText().toString());
                    bmi = weight / (height * height);
                    bmi = Math.round((bmi * 100));
                    bmi = bmi / 100;
                    BMIText.setText("BMI: " + String.valueOf(bmi));
                    resetbtn.setVisibility(View.VISIBLE);
                    getBMIbtn.setVisibility(View.INVISIBLE);
                    showRange();
                }
            }
        });
        getBMIbtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBMI();
            }
        });
        bmiViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        againbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBMI();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void showRange()
    {
        if(bmi < 18.5)
        {
            binding.rangeText.setText("Underweight!!");
        }
        else if(bmi >= 18.5 && bmi <= 24.99)
        {
            binding.rangeText.setText("Healthy Weight..");
        }
        else if(bmi >= 25 && bmi <= 29.99)
        {
            binding.rangeText.setText("Overweight..");
        }
         else if(bmi >= 30 )
        {
            binding.rangeText.setText("Obesity!!");
        }
         else
        {
            binding.rangeText.setText("Wrong Weight/Height");
        }
    }
    public void resetBMI()
    {
        weightInput.getText().clear();
        heightInput.getText().clear();
        getBMIbtn.setVisibility(View.VISIBLE);
        resetbtn.setVisibility(View.INVISIBLE);
        againbtn.setVisibility(View.INVISIBLE);
        BMIText.setText("");
        rangeText.setText("");
    }
}