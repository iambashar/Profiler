package com.teamdui.profiler.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.HomeFragmentBinding;
import com.teamdui.profiler.ui.dailycalorie.DailyCalorieFragment;
import com.teamdui.profiler.ui.dailycalorie.DailyExerciseFragment;
import com.teamdui.profiler.ui.dailycalorie.DailyMealFragment;
import com.teamdui.profiler.ui.goaltracker.GoalsaveFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private HomeFragmentBinding binding;

    public ProgressBar calProgressBar;
    public ProgressBar waterProgressBar;
    public ProgressBar exerciseProgressBar;
    public static int caloriePercentage;
    public static int waterPercentage;
    public static int exercisePercentage;

    public TextView calDailyText;
    public TextView calGoalText;
    public TextView glassDailyText;
    public TextView glassGoalText;
    public TextView exerciseDailyText;
    public TextView exerciseGoalText;

    public static int calorieDaily = 0;
    public static int calorieGoal = 0;
    public static int glassDaily = 0;
    public static int glassGoal = 0;
    public static int exerciseDaily = 0;
    public static int exerciseGoal = 0;
    public static double netCalorie = 0;
    public static double burnedCalorie = 0;

    public DailyMealFragment dailyMealFragment = new DailyMealFragment();
    public DailyCalorieFragment dailyCalorieFragment = new DailyCalorieFragment();
    public DailyExerciseFragment dailyExerciseFragment = new DailyExerciseFragment();
    public GoalsaveFragment goalsaveFragment = new GoalsaveFragment();

    public TextView calEarnText;
    public TextView calBurnText;
    public TextView calNetText;

    private ImageView cameraIcon;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = HomeFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setVariables();

        calProgressBar = binding.calProgress;
        waterProgressBar = binding.waterProgress;
        exerciseProgressBar = binding.exerciseProgress;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                setProgressBar();
            }
        });
        thread.start();

        calDailyText = binding.homeCalDaily;
        calGoalText = binding.homeCalGoal;
        glassDailyText = binding.homewaterDaily;
        glassGoalText = binding.homewaterGoal;
        exerciseDailyText = binding.homeExDaily;
        exerciseGoalText = binding.homeExGoal;

        calEarnText = binding.homeEarnText;
        calBurnText = binding.homeBurnText;
        calNetText = binding.homeNetText;
        setCalorieText();

        cameraIcon = binding.camera;
        cameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_camera);
            }
        });

        return root;
    }

    public void setVariables()
    {
        try {
            calorieDaily = dailyMealFragment.getCalorieDaily();
        } catch (Exception e) {
            calorieDaily = 0;
        }
        try {
            calorieGoal = goalsaveFragment.getCalorieGoal();
        } catch (Exception e) {
            calorieGoal= 0;
        }try {
        glassDaily = dailyCalorieFragment.getGlassDaily();
    } catch (Exception e) {
        glassDaily = 0;
    }try {
        glassGoal = goalsaveFragment.getGlassGoal();
    } catch (Exception e) {
        glassGoal = 0;
    }try {
        exerciseDaily = dailyExerciseFragment.getExerciseDaily();
    } catch (Exception e) {
        exerciseDaily = 0;
    }try {
        exerciseGoal = goalsaveFragment.getExerciseGoal();
    } catch (Exception e) {
        exerciseGoal= 0;
    }
        try {
            burnedCalorie = dailyExerciseFragment.getBurnedCalorie();
        } catch (Exception e) {
            burnedCalorie = 0;
        }

        netCalorie = (double)calorieDaily - burnedCalorie;
    }

    public void setProgressBar()
    {
        try {
            caloriePercentage = (int)(calorieDaily * 100.0f) / calorieGoal;
        } catch (Exception e) {
            caloriePercentage = 0;
        }

        try {
            waterPercentage = (int)(glassDaily * 100.0f) / glassGoal;
        } catch (Exception e)
        {
            waterPercentage = 0;
        }

        try {
            exercisePercentage = (int)(exerciseDaily * 100.0f) / exerciseGoal;
        } catch (Exception e) {
            exercisePercentage = 0;
        }
        calProgressBar.setProgress(caloriePercentage);
        waterProgressBar.setProgress(waterPercentage);
        exerciseProgressBar.setProgress(exercisePercentage);
        setProgressText();
    }

    public void setProgressText()
    {
        calDailyText.setText(String.valueOf(calorieDaily));
        calGoalText.setText(String.valueOf(calorieGoal));
        glassDailyText.setText(String.valueOf(glassDaily));
        glassGoalText.setText(String.valueOf(glassGoal));
        exerciseDailyText.setText(String.valueOf(exerciseDaily));
        exerciseGoalText.setText(String.valueOf(exerciseGoal));
    }

    public void setCalorieText()
    {
        calEarnText.setText(calEarnText.getText().toString() + " " + String.valueOf((double)calorieDaily));
        calBurnText.setText(calBurnText.getText().toString() + " " + String.valueOf(burnedCalorie));
        if(netCalorie > 0)
        {
            calNetText.setText(calNetText.getText().toString() + " " + "+" + String.valueOf(netCalorie));
        }
        else
        {
            calNetText.setText(calNetText.getText().toString() + " " + String.valueOf(netCalorie));
        }

    }


}