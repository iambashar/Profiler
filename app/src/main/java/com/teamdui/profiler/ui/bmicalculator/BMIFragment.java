package com.teamdui.profiler.ui.bmicalculator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.FragmentBmicalculatorBinding;

public class BMIFragment extends Fragment {

    private BMIViewModel bmiViewModel;
    private FragmentBmicalculatorBinding binding;

    public EditText weightInput;
    public EditText heightInputFeet;
    public EditText heightInputInch;
    public RadioGroup  weightRadio;
    public RadioGroup  heightRadio;
    public RadioButton weightRadioButtonKG;
    public RadioButton weightRadioButtonPound;
    public RadioButton heightRadioButtonFeet;
    public RadioButton heightRadioButtonMeter;
    public TextView BMIText;
    public TextView rangeText;
    public Button getBMIbtn;
    public Button resetbtn;
    private boolean kg, lb, ft, met;
    public float bmi;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bmiViewModel =
                new ViewModelProvider(this).get(BMIViewModel.class);

        binding = FragmentBmicalculatorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        weightInput = binding.weightInput;
        heightInputFeet = binding.heightInputFeet;
        heightInputInch = binding.heightInputInch;
        weightRadio = binding.radioGroupWeight;
        heightRadio = binding.radioGroupHeight;
        weightRadioButtonKG = binding.radioKG;
        weightRadioButtonPound = binding.radioPound;
        heightRadioButtonFeet = binding.radiofeet;
        heightRadioButtonMeter = binding.radiometer;

        BMIText = binding.bmiText;
        getBMIbtn = binding.getBMIButton;
        resetbtn = binding.resetBMIButton;
        rangeText = binding.rangeText;
        kg = true;
        ft = true;

        getBMIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weightInput.getText().toString() .isEmpty()|| (heightRadioButtonFeet.isChecked() &&
                        (heightInputFeet.getText().toString().isEmpty()&& heightInputInch.getText().toString().isEmpty()))
                   || (heightRadioButtonMeter.isChecked() && heightRadioButtonFeet.getText().toString().isEmpty()))
                        {
                    rangeText.setText(R.string.giveinput);
                    rangeText.setTextColor(Color.parseColor("#E05668"));
                }
                else
                {
                    float weight = Float.parseFloat(weightInput.getText().toString());
                    float height = 1, feet, inch;

                    if (kg && ft){
                        if (heightInputFeet.getText().toString().isEmpty()) {
                            feet = 0;
                        }
                        else {
                            feet = Float.parseFloat(heightInputFeet.getText().toString());
                        }
                        if (heightInputInch.getText().toString().isEmpty()) {
                            inch = 0;
                        }
                        else {
                            inch = Float.parseFloat(heightInputInch.getText().toString());
                        }
                        height = (float) ((feet * 12.0 + inch) * 0.0254);
                    }else if (kg && met){
                        height = Float.parseFloat(heightInputFeet.getText().toString());
                    }else if (lb && ft){
                        weight = (float) (weight * 0.453592);
                        if (heightInputFeet.getText().toString().isEmpty()) {
                            feet = 0;
                        }
                        else {
                            feet = Float.parseFloat(heightInputFeet.getText().toString());
                        }
                        if (heightInputInch.getText().toString().isEmpty()) {
                            inch = 0;
                        }
                        else {
                            inch = Float.parseFloat(heightInputInch.getText().toString());
                        }
                        height = (float) ((feet * 12.0 + inch) * 0.0254);
                    }else if (lb && met){
                        weight = (float) (weight * 0.453592);
                        height = Float.parseFloat(heightInputFeet.getText().toString());
                    }

                    bmi = weight / (height * height);
                    bmi = Math.round((bmi * 100));
                    bmi = bmi / 100;
                    BMIText.setText("BMI: " + String.valueOf(bmi));
                    resetbtn.setVisibility(View.VISIBLE);
                    getBMIbtn.setVisibility(View.INVISIBLE);
                    rangeText.setTextColor(Color.parseColor("#ED551C"));
                    showRange();
                }
            }
        });
        weightInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        heightInputFeet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        heightInputInch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        weightRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                kg = weightRadioButtonKG.isChecked();
                lb = weightRadioButtonPound.isChecked();
                if (kg){
                    weightInput.setHint("kg");
                }else if (lb){
                    weightInput.setHint("lb");
                }
            }
        });
        heightRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ft = heightRadioButtonFeet.isChecked();
                met = heightRadioButtonMeter.isChecked();
                if (met){
                    heightInputInch.setVisibility(View.INVISIBLE);
                    heightInputFeet.setHint("meter");
                }else if (ft){
                    heightInputInch.setVisibility(View.VISIBLE);
                    heightInputFeet.setHint("feet");
                    heightInputInch.setHint("inch");
                }
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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @SuppressLint("ResourceAsColor")
    public void showRange()
    {
        if(bmi < 18.5)
        {
            rangeText.setText("Underweight!!");
            binding.underweight.setTextColor(Color.parseColor("#ffffff"));
            binding.underweight.setTextSize(22);
        }
        else if(bmi >= 18.5 && bmi <= 24.99)
        {
            rangeText.setText("Healthy Weight..");
            binding.healthyWeight.setTextColor(Color.parseColor("#ffffff"));
            binding.healthyWeight.setTextSize(22);
        }
        else if(bmi >= 25 && bmi <= 29.99)
        {
            rangeText.setText("Overweight..");
            binding.overweight.setTextColor(Color.parseColor("#ffffff"));
            binding.overweight.setTextSize(22);
        }
         else if(bmi >= 30 )
        {
            rangeText.setText("Obesity!!");
            binding.obesity.setTextColor(Color.parseColor("#ffffff"));
            binding.obesity.setTextSize(22);
        }
         else
        {
            rangeText.setText("Wrong Weight/Height");
        }
    }
    public void resetBMI()
    {
        weightInput.getText().clear();
        heightInputFeet.getText().clear();
        heightInputInch.getText().clear();
        getBMIbtn.setVisibility(View.VISIBLE);
        resetbtn.setVisibility(View.INVISIBLE);
        BMIText.setText("");
        rangeText.setText("");
        binding.underweight.setTextColor(Color.parseColor("#A6A2A2"));
        binding.healthyWeight.setTextColor(Color.parseColor("#A6A2A2"));
        binding.overweight.setTextColor(Color.parseColor("#A6A2A2"));
        binding.obesity.setTextColor(Color.parseColor("#A6A2A2"));
        binding.underweight.setTextSize(18);
        binding.healthyWeight.setTextSize(18);
        binding.obesity.setTextSize(18);
        binding.overweight.setTextSize(18);
    }
}