package com.teamdui.profiler.ui.goaltracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.FragmentGoaltrackerBinding;

import static com.teamdui.profiler.MainActivity.calorieDaily;
import static com.teamdui.profiler.MainActivity.calorieGoal;
import static com.teamdui.profiler.MainActivity.exerciseDaily;
import static com.teamdui.profiler.MainActivity.exerciseGoal;
import static com.teamdui.profiler.MainActivity.glassDaily;
import static com.teamdui.profiler.MainActivity.glassGoal;
import static com.teamdui.profiler.ui.util.TextUpdater.textSetter;

public class GoaltrackerFragment extends Fragment {

    private GoaltrackerViewModel goaltrackerViewModel;
    private FragmentGoaltrackerBinding binding;
    public Button goalButton;
    public GoalsaveFragment goalsaveFragment = new GoalsaveFragment();

    public static boolean isGoslsaveVisited = false;

    public ProgressBar calorieProgressBar;
    public ProgressBar waterProgressBar;
    public ProgressBar exerciseProgressBar;
    public TextView caloriePercentText;
    public TextView waterPercentText;
    public TextView exercisePercentText;
    public static int caloriePercentage;
    public static int waterPercentage;
    public static int exercisePercentage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goaltrackerViewModel =
                new ViewModelProvider(this).get(GoaltrackerViewModel.class);

        binding = FragmentGoaltrackerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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

        calorieProgressBar = binding.calorieProgressBar;
        waterProgressBar = binding.waterProgressbar;
        exerciseProgressBar = binding.exerciseProgressbar;
        caloriePercentText = binding.caloriePercentage;
        waterPercentText = binding.waterPercentage;
        exercisePercentText = binding.exercisePercentage;

        setProgressBar();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setGoalText() {
        String calorieText = binding.calorieEarninTracker.getText().toString();
        textSetter(binding.calorieEarninTracker, calorieText + calorieDaily + "/" + calorieGoal);
        String waterText = binding.waterTakeninTracker.getText().toString();
        textSetter(binding.waterTakeninTracker, waterText + glassDaily + "/" + glassGoal);
        String exerciseText = binding.exerciseDoneinTracker.getText().toString();
        textSetter(binding.exerciseDoneinTracker, exerciseText + exerciseDaily + "/" + exerciseGoal);
    }

    public void setProgressBar() {
        try {
            caloriePercentage = (int) (calorieDaily * 100.0f) / calorieGoal;
        } catch (Exception e) {
            caloriePercentage = 0;
        }

        try {
            waterPercentage = (int) (glassDaily * 100.0f) / glassGoal;
        } catch (Exception e) {
            waterPercentage = 0;
        }

        try {
            exercisePercentage = (int) (exerciseDaily * 100.0f) / exerciseGoal;
        } catch (Exception e) {
            exercisePercentage = 0;
        }

        calorieProgressBar.setProgress(caloriePercentage);
        waterProgressBar.setProgress(waterPercentage);
        exerciseProgressBar.setProgress(exercisePercentage);
        setPercentText();
    }

    public void setPercentText() {
        textSetter(caloriePercentText, caloriePercentage + "%");
        textSetter(waterPercentText, waterPercentage + "%");
        textSetter(exercisePercentText, exercisePercentage + "%");
    }
}
