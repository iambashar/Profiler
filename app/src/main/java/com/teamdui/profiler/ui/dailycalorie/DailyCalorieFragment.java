package com.teamdui.profiler.ui.dailycalorie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.FragmentDailycalorieBinding;
import com.teamdui.profiler.ui.goaltracker.GoalsaveFragment;
import com.teamdui.profiler.ui.login.LoginActivity;

public class DailyCalorieFragment extends Fragment {

    private DailyCalorieViewModel dailyCalorieViewModel;
    private FragmentDailycalorieBinding binding;
    public static boolean isSaved = false;

    public AppCompatButton addMealbtn;
    public AppCompatButton addExercisebtn;

    public ImageView addWaterbtn;
    public ImageView deleteWaterbtn;
    public static Integer glassDaily = 0;
    public static Integer mlDaily = 0;

    public TextView waterGlassInput;
    public TextView waterMlInput;


    public static int calorieDaily = 0;
    public static int exerciseDaily = 0;
    public static int glassGoal = 0;
    public static int calorieGoal = 0;
    public static int exerciseGoal = 0;

    public final int waterFactor = 150;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dailyCalorieViewModel =
                new ViewModelProvider(this).get(DailyCalorieViewModel.class);

        binding = FragmentDailycalorieBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setUpperText();

        addMealbtn = binding.addMealButton;
        addMealbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_dailycalorie_to_navigation_dailymeal);
            }
        });


        addWaterbtn  = binding.addWater;
        deleteWaterbtn = binding.deleteWater;
        addWaterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glassDaily++;
                mlDaily = glassDaily * waterFactor;
                isSaved = true;
                setWaterText();
                if(glassDaily == 0)
                {
                    binding.waterInputGlass.getText().clear();
                    binding.waterInputMl.getText().clear();
                }
                setUpperText();
                setGlassVisibility();
            }
        });
        deleteWaterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glassDaily--;
                if(glassDaily < 0)
                {
                    glassDaily = 0;
                }
                mlDaily = glassDaily * waterFactor;
                setUpperText();
                setWaterText();
                setGlassInvisibility();
            }
        });


        addExercisebtn = binding.addExerciseButton;
        addExercisebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_dailycalorie_to_navigation_dailyexercise);
            }
        });



        waterGlassInput = binding.waterInputGlass;
        waterMlInput = binding.waterInputMl;
        waterGlassInput.setText(Integer.toString(glassDaily));
        waterMlInput.setText(Integer.toString(glassDaily * waterFactor));
        setGlassVisibility();
        waterGlassInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);

                int noOfGlass = glassDaily;
                if(!waterGlassInput.getText().toString().isEmpty())
                {
                    glassDaily = Integer.parseInt(waterGlassInput.getText().toString());
                }
                else
                {
                    glassDaily = 0;
                }
                setUpperText();
                mlDaily = glassDaily * waterFactor;
                if(noOfGlass < glassDaily)
                {
                    setGlassVisibility();
                }
                else if(noOfGlass > glassDaily)
                {
                    setGlassInvisibility();
                }
            }

        });
        waterMlInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
                int noOfGlass = glassDaily;
                if(!waterMlInput.getText().toString().isEmpty())
                {
                    mlDaily = Integer.parseInt(waterMlInput.getText().toString());
                }
                else
                {
                    mlDaily = 0;
                }
                glassDaily = (int) Math.ceil((double) mlDaily / waterFactor);
                setUpperText();
                if(noOfGlass < glassDaily)
                {
                    setGlassVisibility();
                }
                else if(noOfGlass > glassDaily)
                {
                    setGlassInvisibility();
                }
            }
        });





        return root;
    }

    public void setGlassVisibility()
    {
        binding.waterInputGlass.setText(glassDaily.toString());
        binding.waterInputMl.setText(mlDaily.toString());
        isSaved = true;
        if(glassDaily == 0)
        {
            binding.waterInputMl.getText().clear();
            binding.waterInputGlass.getText().clear();
            isSaved = false;
        }
        switch (glassDaily)
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
        binding.waterInputGlass.setText(glassDaily.toString());
        binding.waterInputMl.setText(mlDaily.toString());
        isSaved = true;
        if(glassDaily == 0)
        {
            binding.waterInputMl.getText().clear();
            binding.waterInputGlass.getText().clear();
            isSaved = false;
        }
        switch (glassDaily)
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

    public void setWaterText()
    {
        waterGlassInput.setText(glassDaily.toString());
        waterMlInput.setText(mlDaily.toString());
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public int getGlassDaily()
    {
        return glassDaily;
    }


    public void setUpperText()
    {
        try{
            DailyExerciseFragment dailyExerciseFragment = new DailyExerciseFragment();
            DailyMealFragment dailyMealFragment = new DailyMealFragment();
            GoalsaveFragment goalsaveFragment = new GoalsaveFragment();
            calorieDaily = dailyMealFragment.getCalorieDaily();
            exerciseDaily = dailyExerciseFragment.getExerciseDaily();
            glassGoal = goalsaveFragment.getGlassGoal();
            calorieGoal = goalsaveFragment.getCalorieGoal();
            exerciseGoal = goalsaveFragment.getExerciseGoal();
        }
        catch (Exception e)
        {
            calorieDaily = 0;
            exerciseDaily = 0;
            glassGoal = 0;
            calorieGoal = 0;
            exerciseGoal = 0;
        }
        TextView calorieText = binding.calorieEarn;
        TextView waterText = binding.waterTaken;
        TextView exerciseText = binding.exerciseDone;
        calorieText.setText("Total calorie earned(cal): " + String.valueOf(calorieDaily) + "/" + String.valueOf(calorieGoal));
        waterText.setText("Water taken(glasses): " + String.valueOf(glassDaily) + "/" + String.valueOf(glassGoal));
        exerciseText.setText("Exercise done(min): "+ String.valueOf(exerciseDaily) + "/" + String.valueOf(exerciseGoal));
    }

}