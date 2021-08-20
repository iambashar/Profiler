package com.teamdui.profiler.ui.goaltracker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.teamdui.profiler.MainActivity;
import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.FragmentGoalsaveBinding;
import com.teamdui.profiler.databinding.FragmentGoaltrackerBinding;
import com.teamdui.profiler.ui.dailycalorie.DailyCalorieFragment;
import com.teamdui.profiler.ui.dailycalorie.DailyExerciseFragment;
import com.teamdui.profiler.ui.dailycalorie.DailyMealFragment;


public class GoalsaveFragment extends Fragment {



    private FragmentGoalsaveBinding binding;

    public GoaltrackerFragment goaltrackerFragment;

    private SeekBar calorieSlider;
    private EditText calorieEditText;
    public static int calorieGoal = 0;
    public static int finalCalorieGoal = 0;

    private ImageView addWaterButton;
    private ImageView deleteWaterButton;
    private EditText waterEditText;
    private EditText waterEditTextml;
    public static int glassGoal = 0;
    public static int finalGlassGoal = 0;

    private EditText hourInput;
    private EditText minuteInput;
    private TextView totalMinute;
    public static int exerciseGoal = 0;
    public static int finalExerciseGoal = 0;
    public static boolean isSaved = false;
    public static boolean isWaterModified = false;

    private Button saveGoalbtn;
    private Button resetbtn;

    public DailyCalorieFragment dailyCalorieFragment = new DailyCalorieFragment();
    public DailyExerciseFragment dailyExerciseFragment = new DailyExerciseFragment();
    public DailyMealFragment dailyMealFragment = new DailyMealFragment();
    public static int calorieDaily;
    public static int exerciseDaily;
    public static int glassDaily;
    public final int waterFactor = 150;

    public GoalsaveFragment()
    {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentGoalsaveBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        calorieGoal = dailyMealFragment.getCalorieDaily();
        //glassGoal = dailyCalorieFragment.getGlassDaily();
        exerciseGoal = dailyExerciseFragment.getExerciseDaily();


        //to set calorie goal
        calorieSlider = binding.calorieSlideBar;
        calorieEditText = binding.calorieInput;
        calorieSlider.setProgress(finalCalorieGoal);
        calorieEditText.setText(String.valueOf(finalCalorieGoal), TextView.BufferType.EDITABLE);
        if(finalCalorieGoal > 3000)
        {
            calorieSlider.getProgressDrawable().setTint(Color.rgb(224, 86, 104));
            calorieSlider.getThumb().setTint(Color.rgb(224, 86, 104));
            //Toast.makeText(getActivity().getApplicationContext(), "High Calorie", Toast.LENGTH_LONG).show();

        }
        if(finalCalorieGoal < 3000)
        {
            calorieSlider.getProgressDrawable().setTint(Color.rgb(72, 83, 146));
            calorieSlider.getThumb().setTint(Color.rgb(72, 83, 146));
        }

        calorieSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                calorieGoal = progress;
                calorieEditText.setText(String.valueOf(calorieGoal), TextView.BufferType.EDITABLE);

                if(calorieGoal > 3000)
                {
                    calorieSlider.getProgressDrawable().setTint(Color.rgb(224, 86, 104));
                    calorieSlider.getThumb().setTint(Color.rgb(224, 86, 104));
                    //Toast.makeText(getActivity().getApplicationContext(), "High Calorie", Toast.LENGTH_LONG).show();

                }
                if(calorieGoal < 3000)
                {
                    calorieSlider.getProgressDrawable().setTint(Color.rgb(72, 83, 146));
                    calorieSlider.getThumb().setTint(Color.rgb(72, 83, 146));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //to set water goal
        addWaterButton = binding.addWater;
        glassGoal = finalGlassGoal;
        setGlassVisibility();
        addWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glassGoal++;
                setGlassVisibility();
            }
        });

        deleteWaterButton = binding.deleteWater;
        deleteWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glassGoal--;
                if(glassGoal == 0)
                {
                    binding.waterInputGlass.getText().clear();
                    binding.waterInputMl.getText().clear();
                }
                if(glassGoal < 0)
                {
                    glassGoal = 0;
                }
                setGlassInvisibility();
            }
        });

        waterEditText = binding.waterInputGlass;
        waterEditTextml = binding.waterInputMl;
        waterEditText.setText(String.valueOf(finalGlassGoal), TextView.BufferType.EDITABLE);
        waterEditTextml.setText(String.valueOf(finalGlassGoal * waterFactor), TextView.BufferType.EDITABLE);
        waterEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String waterText = waterEditText.getText().toString();
                waterEditTextml.setText(String.valueOf(glassGoal * waterFactor), TextView.BufferType.EDITABLE);
                int noOfGlass;
                if(isWaterModified)
                {
                    noOfGlass = Integer.parseInt(waterText);
                }
                else
                {
                    noOfGlass = 0;
                }
                hideKeyboard(v);
                isWaterModified = true;
                if(noOfGlass > glassGoal)
                {
                    glassGoal = noOfGlass;
                    setGlassVisibility();
                }
                else if(noOfGlass < glassGoal)
                {
                    glassGoal = noOfGlass;
                    setGlassInvisibility();
                }
            }
        });
        waterEditTextml.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String waterText = waterEditTextml.getText().toString();
                int y;
                hideKeyboard(v);
                int noOfGlass;
                if(isWaterModified)
                {
                    y = Integer.parseInt(waterText);
                    noOfGlass = (int) Math.ceil((double) y / waterFactor);
                }
                else
                {
                    noOfGlass = 0;
                }
                isWaterModified = true;
                if(noOfGlass > glassGoal)
                {
                    glassGoal = noOfGlass;
                    setGlassVisibility();
                }
                else if(noOfGlass < glassGoal)
                {
                    glassGoal = noOfGlass;
                    setGlassInvisibility();
                }
            }
        });
        if(glassGoal == 0)
        {
            binding.waterInputGlass.getText().clear();
            binding.waterInputMl.getText().clear();
        }

        //to set exercise goal
        hourInput = binding.hourInput;
        minuteInput = binding.minuteInput;
        totalMinute = binding.minText;
        isSaved = false;
        totalMinute.setText(String.valueOf(finalExerciseGoal));
        hourInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
                int hour = 0;
                int minute = 0;
                if (hourInput.getText().toString().length() == 0)
                {
                    hour = 0;
                }
                else
                {
                    hour = Integer.parseInt(hourInput.getText().toString());
                }
                if(minuteInput.getText().toString().length() == 0)
                {
                    minute = 0;
                }
                else
                {
                    minute = Integer.parseInt(minuteInput.getText().toString());
                }
                exerciseGoal = hour * 60 + minute;
                totalMinute.setText(String.valueOf(exerciseGoal));
            }
        });
        minuteInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
                int hour = 0;
                int minute = 0;
                if (hourInput.getText().toString().length() == 0)
                {
                    hour = 0;
                }
                else
                {
                    hour = Integer.parseInt(hourInput.getText().toString());
                }
                if(minuteInput.getText().toString().length() == 0)
                {
                    minute = 0;
                }
                else
                {
                    minute = Integer.parseInt(minuteInput.getText().toString());
                }
                exerciseGoal = hour * 60 + minute;
                totalMinute.setText(String.valueOf(exerciseGoal));
            }
        });


        saveGoalbtn = binding.saveGoalButton;
        saveGoals();
        saveGoalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSaved = true;
                finalCalorieGoal = calorieGoal;
                finalGlassGoal = glassGoal;
                finalExerciseGoal = exerciseGoal;
                saveGoals();
            }
        });

        resetbtn = binding.resetGoalButton;
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGoal();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setGlassVisibility()
    {
        waterEditText = binding.waterInputGlass;
        waterEditText.setText(String.valueOf(glassGoal), TextView.BufferType.EDITABLE);
        binding.waterInputMl.setText(String.valueOf(glassGoal * waterFactor), TextView.BufferType.EDITABLE);
        switch (glassGoal)
        {
            case 16:
            {
                ImageView glass = binding.glass16;
                glass.setVisibility(getView().VISIBLE);
            }
            case 15:
            {
                ImageView glass = binding.glass15;
                glass.setVisibility(getView().VISIBLE);
            }
            case 14:
            {
                ImageView glass = binding.glass14;
                glass.setVisibility(getView().VISIBLE);
            }
            case 13:
            {
                ImageView glass = binding.glass13;
                glass.setVisibility(getView().VISIBLE);
            }
            case 12:
            {
                ImageView glass = binding.glass12;
                glass.setVisibility(getView().VISIBLE);
            }
            case 11:
            {
                ImageView glass = binding.glass11;
                glass.setVisibility(getView().VISIBLE);
            }
            case 10:
            {
                ImageView glass = binding.glass10;
                glass.setVisibility(getView().VISIBLE);
            }
            case 9:
            {
                ImageView glass = binding.glass9;
                glass.setVisibility(getView().VISIBLE);
            }
            case 8:
            {
                ImageView glass = binding.glass8;
                glass.setVisibility(getView().VISIBLE);
            }
            case 7:
            {
                ImageView glass = binding.glass7;
                glass.setVisibility(getView().VISIBLE);
            }
            case 6:
            {
                ImageView glass = binding.glass6;
                glass.setVisibility(getView().VISIBLE);
            }
            case 5:
            {
                ImageView glass = binding.glass5;
                glass.setVisibility(getView().VISIBLE);
            }
            case 4:
            {
                ImageView glass = binding.glass4;
                glass.setVisibility(getView().VISIBLE);
            }
            case 3:
            {
                ImageView glass = binding.glass3;
                glass.setVisibility(getView().VISIBLE);
            }
            case 2:
            {
                ImageView glass = binding.glass2;
                glass.setVisibility(getView().VISIBLE);
            }
            case 1:
            {
                ImageView glass = binding.glass1;
                glass.setVisibility(getView().VISIBLE);
            }
        }
    }

    public void setGlassInvisibility()
    {
        waterEditText = binding.waterInputGlass;
        waterEditText.setText(String.valueOf(glassGoal), TextView.BufferType.EDITABLE);
        binding.waterInputMl.setText(String.valueOf(glassGoal * waterFactor), TextView.BufferType.EDITABLE);

        switch (glassGoal)
        {
            case 0:
            {
                ImageView glass = binding.glass1;
                glass.setVisibility(getView().INVISIBLE);
                binding.waterInputMl.getText().clear();
                binding.waterInputGlass.getText().clear();
            }
            case 1:
            {
                ImageView glass = binding.glass2;
                glass.setVisibility(getView().INVISIBLE);
            }
            case 2:
            {
                ImageView glass = binding.glass3;
                glass.setVisibility(getView().INVISIBLE);
            }
            case 3:
            {
                ImageView glass = binding.glass4;
                glass.setVisibility(getView().INVISIBLE);
            }
            case 4:
            {
                ImageView glass = binding.glass5;
                glass.setVisibility(getView().INVISIBLE);
            }
            case 5:
            {
                ImageView glass = binding.glass6;
                glass.setVisibility(getView().INVISIBLE);
            }
            case 6:
            {
                ImageView glass = binding.glass7;
                glass.setVisibility(getView().INVISIBLE);
            }
            case 7:
            {
                ImageView glass = binding.glass8;
                glass.setVisibility(getView().INVISIBLE);
            }
            case 8:
            {
                ImageView glass = binding.glass9;
                glass.setVisibility(getView().INVISIBLE);
            }
            case 9:
            {
                ImageView glass = binding.glass10;
                glass.setVisibility(getView().INVISIBLE);
            }
            case 10:
            {
                ImageView glass = binding.glass11;
                glass.setVisibility(getView().INVISIBLE);
            }
            case 11:
            {
                ImageView glass = binding.glass12;
                glass.setVisibility(getView().INVISIBLE);
            }
            case 12:
            {
                ImageView glass = binding.glass13;
                glass.setVisibility(getView().INVISIBLE);
            }
            case 13:
            {
                ImageView glass = binding.glass14;
                glass.setVisibility(getView().INVISIBLE);
            }
            case 14:
            {
                ImageView glass = binding.glass15;
                glass.setVisibility(getView().INVISIBLE);
            }
            case 15:
            {
                ImageView glass = binding.glass16;
                glass.setVisibility(getView().INVISIBLE);
            }

        }
    }

    public void saveGoals()
    {
        calorieDaily = dailyMealFragment.getCalorieDaily();
        String caloriestr = "Total calorie earned(cal): "+ String.valueOf(calorieDaily) + "/" + String.valueOf(finalCalorieGoal);
        binding.calorieEarn.setText(caloriestr);


        glassDaily = dailyCalorieFragment.getGlassDaily();
        String waterstr = "Water taken(glasses): " +String.valueOf(glassDaily) + "/" +  String.valueOf(finalGlassGoal) ;
        binding.waterTaken.setText(waterstr);


        calculateExercise();
        String exercisestr = binding.exerciseDone.getText().toString();
        exerciseDaily = dailyExerciseFragment.getExerciseDaily();
        exercisestr = "Exercise done(min): " + String.valueOf(exerciseDaily) + "/" + String.valueOf(finalExerciseGoal) ;
        binding.exerciseDone.setText(exercisestr);
        binding.minText.setText(String.valueOf(finalExerciseGoal));
    }
    public void calculateExercise()
    {
        if(hourInput.getText().toString().length() == 0 && minuteInput.getText().toString().length() == 0)
        {
            isSaved = false;
        }
        if(isSaved)
        {
            int hour = 0;
            int minute = 0;
            if (hourInput.getText().toString().length() == 0)
            {
                hour = 0;
            }
            else
            {
                hour = Integer.parseInt(hourInput.getText().toString());
            }
            if(minuteInput.getText().toString().length() == 0)
            {
                minute = 0;
            }
            else
            {
                minute = Integer.parseInt(minuteInput.getText().toString());
            }
            exerciseGoal = hour * 60 + minute;
        }
        else
        {
            binding.minText.setText(String.valueOf(exerciseGoal));
        }

    }
    public int getCalorieGoal()
    {
        return finalCalorieGoal;
    }
    public int getGlassGoal()
    {
        return finalGlassGoal;
    }
    public int getExerciseGoal()
    {
        return finalExerciseGoal;
    }

    public void resetGoal()
    {
        calorieGoal = 0;
        glassGoal = 0;
        exerciseGoal = 0;
        finalCalorieGoal = 0;
        finalGlassGoal = 0;
        finalExerciseGoal = 0;
        calorieSlider.setProgress(calorieGoal);
        calorieEditText.setText(String.valueOf(calorieGoal), TextView.BufferType.EDITABLE);
        binding.waterInputMl.getText().clear();
        binding.waterInputGlass.getText().clear();
        setGlassInvisibility();
        waterEditText.setText(String.valueOf(glassGoal), TextView.BufferType.EDITABLE);
        binding.minText.setText(String.valueOf(exerciseGoal));
        binding.hourInput.getText().clear();
        binding.minuteInput.getText().clear();

        String caloriestr = "Total calorie earned(cal): " + String.valueOf(calorieDaily) + "/0";
        binding.calorieEarn.setText(caloriestr);
        String waterstr = "Water taken(glasses): " + String.valueOf(glassDaily) + "/0";
        binding.waterTaken.setText(waterstr);
        String exercisestr = "Exercise Done(min): " + String.valueOf(exerciseDaily) + "/0";
        binding.exerciseDone.setText(exercisestr);
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}