package com.teamdui.profiler.ui.goaltracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.FragmentGoalsaveBinding;
import com.teamdui.profiler.databinding.FragmentGoaltrackerBinding;


public class GoalsaveFragment extends Fragment {



    private FragmentGoalsaveBinding binding;

    public GoaltrackerFragment goaltrackerFragment;

    private SeekBar calorieSlider;
    private EditText calorieEditText;
    public static int calorieGoal = 0;

    private ImageView addWaterButton;
    private ImageView deleteWaterButton;
    private EditText waterEditText;
    public static int glassGoal = 0;

    private EditText hourInput;
    private EditText minuteInput;
    private TextView totalMinute;
    public static int exerciseGoal = 0;
    public static boolean isSaved = false;

    private Button saveGoalbtn;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentGoalsaveBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        //to set calorie goal
        calorieSlider = binding.calorieSlideBar;
        calorieEditText = binding.calorieInput;
        calorieSlider.setProgress(calorieGoal);
        calorieEditText.setText(String.valueOf(calorieGoal), TextView.BufferType.EDITABLE);

        calorieSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                calorieGoal = progress;
                calorieEditText.setText(String.valueOf(calorieGoal), TextView.BufferType.EDITABLE);
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
                setGlassInvisibility();
            }
        });

        waterEditText = binding.waterInput;
        waterEditText.setText(String.valueOf(glassGoal), TextView.BufferType.EDITABLE);
        waterEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String waterText = waterEditText.getText().toString();
                int noOfGlass = Integer.parseInt(waterText);
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

        //to set exercise goal
        hourInput = binding.hourInput;
        minuteInput = binding.minuteInput;
        totalMinute = binding.minText;
        isSaved = false;
        totalMinute.setText(String.valueOf(exerciseGoal));


        saveGoalbtn = binding.saveGoalButton;
        saveGoals();
        saveGoalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSaved = true;
                saveGoals();
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
        waterEditText = binding.waterInput;
        waterEditText.setText(String.valueOf(glassGoal), TextView.BufferType.EDITABLE);
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
        waterEditText = binding.waterInput;
        waterEditText.setText(String.valueOf(glassGoal), TextView.BufferType.EDITABLE);
        switch (glassGoal)
        {
            case 0:
            {
                ImageView glass = binding.glass1;
                glass.setVisibility(getView().INVISIBLE);
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
            String caloriestr = "Total calorie earned(cal): 0/" + String.valueOf(calorieGoal);
            binding.calorieEarn.setText(caloriestr);



            String waterstr = "Water taken(glasses): 0/" + String.valueOf(glassGoal) ;
            binding.waterTaken.setText(waterstr);


            calculateExercise();
            String exercisestr = binding.exerciseDone.getText().toString();
            exercisestr = "Exercise done(min): 0/" + String.valueOf(exerciseGoal) ;
            binding.exerciseDone.setText(exercisestr);
            binding.minText.setText(String.valueOf(exerciseGoal));
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
        int getCalorieGoal()
        {
            return calorieGoal;
        }
        int getGlassGoal()
        {
            return glassGoal;
        }
        int getExerciseGoal()
        {
            return exerciseGoal;
        }

        }


