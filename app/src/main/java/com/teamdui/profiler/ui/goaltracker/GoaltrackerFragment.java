package com.teamdui.profiler.ui.goaltracker;

import android.hardware.camera2.CameraAccessException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.FragmentGoaltrackerBinding;

public class GoaltrackerFragment extends Fragment {

    private GoaltrackerViewModel goaltrackerViewModel;
    private FragmentGoaltrackerBinding binding;
    public Button goalButton;
    public GoalsaveFragment goalsaveFragment;

    public static int calorieGoal = 0;
    public static int waterGoal = 0;
    public static  int exerciseGoal = 0;
    public static boolean isGoslsaveVisited = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goaltrackerViewModel =
                new ViewModelProvider(this).get(GoaltrackerViewModel.class);

        binding = FragmentGoaltrackerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        setGoalVariables();
        setGoalText();

        goalButton = binding.setGoalButton;
        goalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arg = new Bundle();
                isGoslsaveVisited = true;
                Navigation.findNavController(root).navigate(R.id.action_navigation_goaltracker_to_navigation_goalsave, arg);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setGoalVariables()
    {
        if(isGoslsaveVisited)
        {
            calorieGoal = goalsaveFragment.getCalorieGoal();
            waterGoal = goalsaveFragment.getGlassGoal();
            exerciseGoal = goalsaveFragment.getExerciseGoal();
        }
    }
    public void setGoalText()
    {
        String calorieText = binding.calorieEarninTracker.getText().toString();
        binding.calorieEarninTracker.setText(calorieText + String.valueOf(calorieGoal));
        String waterText = binding.waterTakeninTracker.getText().toString();
        binding.waterTakeninTracker.setText(waterText + String.valueOf(waterGoal));
        String exerciseText = binding.exerciseDoneinTracker.getText().toString();
        binding.exerciseDoneinTracker.setText(exerciseText + String.valueOf(exerciseGoal));
    }

}