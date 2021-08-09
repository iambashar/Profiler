package com.teamdui.profiler.ui.goaltracker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.view.KeyEvent;
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
    private Button resetbtn;

    public GoalsaveFragment()
    {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentGoalsaveBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor myEditor = myPreferences.edit();
        calorieGoal = myPreferences.getInt("Calorie", 0);
        glassGoal = myPreferences.getInt("Glass", 0);
        exerciseGoal = myPreferences.getInt("Exercise", 0);

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

        waterEditText = binding.waterInputGlass;
        waterEditText.setText(String.valueOf(glassGoal), TextView.BufferType.EDITABLE);
        waterEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String waterText = waterEditText.getText().toString();
                int noOfGlass = Integer.parseInt(waterText);
                hideKeyboard(v);
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
        hourInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
               calculateExercise();
            }
        });

        hourInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == 66) {
                    // Do your thing.
                    calculateExercise();
                    return false;  // So it is not propagated.
                }
                return false;
            }
        });


        minuteInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
                calculateExercise();
            }
        });
        minuteInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == 66) {
                    // Do your thing.
                    calculateExercise();
                    return false;  // So it is not propagated.
                }
                return false;
            }
        });



        saveGoalbtn = binding.saveGoalButton;
        saveGoals();
        saveGoalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSaved = true;
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


            passExerciseToSaveGoal();
            exerciseGoal = Integer.parseInt(binding.minText.getText().toString());
            String exercisestr = binding.exerciseDone.getText().toString();
            exercisestr = "Exercise done(min): 0/" + String.valueOf(exerciseGoal) ;
            binding.exerciseDone.setText(exercisestr);
            binding.minText.setText(String.valueOf(exerciseGoal));



            SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor myEditor = myPreferences.edit();
            myEditor.putInt("Calorie", calorieGoal);
            myEditor.putInt("Glass", glassGoal);
            myEditor.putInt("Exercise", exerciseGoal);
            myEditor.commit();
        }
        public void passExerciseToSaveGoal()
        {
            if(hourInput.getText().toString().length() == 0 && minuteInput.getText().toString().length() == 0)
            {
                isSaved = false;
            }
            if(isSaved)
            {
                calculateExercise();
            }
            else
            {
                binding.minText.setText(String.valueOf(exerciseGoal));
            }

        }
        public void calculateExercise()
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
            int goal = hour * 60 + minute;
            totalMinute.setText(String.valueOf(goal));
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

        public void resetGoal()
        {
            calorieGoal = 0;
            glassGoal = 0;
            exerciseGoal = 0;
            calorieSlider.setProgress(calorieGoal);
            calorieEditText.setText(String.valueOf(calorieGoal), TextView.BufferType.EDITABLE);
            setGlassInvisibility();
            waterEditText.setText(String.valueOf(glassGoal), TextView.BufferType.EDITABLE);
            binding.minText.setText(String.valueOf(exerciseGoal));
            binding.hourInput.getText().clear();
            binding.minuteInput.getText().clear();

            String caloriestr = "Total calorie earned(cal): 0/0";
            binding.calorieEarn.setText(caloriestr);
            String waterstr = "Water taken(glasses): 0/0";
            binding.waterTaken.setText(waterstr);
            String exercisestr = "Exercise done(min): 0/0" ;
            binding.exerciseDone.setText(exercisestr);

            SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor myEditor = myPreferences.edit();
            myEditor.putInt("Calorie", calorieGoal);
            myEditor.putInt("Glass", glassGoal);
            myEditor.putInt("Exercise", exerciseGoal);
            myEditor.commit();
        }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}


