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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamdui.profiler.MainActivity;
import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.HomeFragmentBinding;
import com.teamdui.profiler.ui.dailycalorie.DailyCalorieFragment;
import com.teamdui.profiler.ui.dailycalorie.DailyExerciseFragment;
import com.teamdui.profiler.ui.dailycalorie.DailyMealFragment;
import com.teamdui.profiler.ui.goaltracker.GoalsaveFragment;
import com.teamdui.profiler.ui.profile.ProfileData;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.teamdui.profiler.MainActivity.burnedCalorie;
import static com.teamdui.profiler.MainActivity.calorieDaily;
import static com.teamdui.profiler.MainActivity.calorieGoal;
import static com.teamdui.profiler.MainActivity.exerciseDaily;
import static com.teamdui.profiler.MainActivity.exerciseGoal;
import static com.teamdui.profiler.MainActivity.glassDaily;
import static com.teamdui.profiler.MainActivity.glassGoal;
import static com.teamdui.profiler.MainActivity.netCalorie;
import static com.teamdui.profiler.MainActivity.setVariables;
import static com.teamdui.profiler.MainActivity.uid;

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

    public DailyMealFragment dailyMealFragment = new DailyMealFragment();
    public DailyCalorieFragment dailyCalorieFragment = new DailyCalorieFragment();
    public DailyExerciseFragment dailyExerciseFragment = new DailyExerciseFragment();

    public TextView calEarnText;
    public TextView calBurnText;
    public TextView calNetText;
    private ImageView cameraIcon;
    public ProfileData data;

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

        netCalorie = (double)calorieDaily - burnedCalorie;

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
        textSetter(calDailyText, String.valueOf(calorieDaily));
        textSetter(calGoalText,String.valueOf(calorieGoal));
        textSetter(glassDailyText,String.valueOf(glassDaily));
        textSetter(glassGoalText,String.valueOf(glassGoal));
        textSetter(exerciseDailyText,String.valueOf(exerciseDaily));
        textSetter(exerciseGoalText,String.valueOf(exerciseGoal));
    }

    public void setCalorieText()
    {
        textSetter(calEarnText, calEarnText.getText().toString() + " " + String.valueOf((double)calorieDaily));
        textSetter(calBurnText,calBurnText.getText().toString() + " " + String.valueOf(burnedCalorie));
        if(netCalorie > 0)
        {
            textSetter(calNetText,calNetText.getText().toString() + " " + "+" + String.valueOf(netCalorie));
        }
        else
        {
            textSetter(calNetText,calNetText.getText().toString() + " " + String.valueOf(netCalorie));
        }
    }


    private void textSetter(TextView view, String text)
    {
        if(view != null)
        {
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.setText(text);
                }
            });
        }
    }
}