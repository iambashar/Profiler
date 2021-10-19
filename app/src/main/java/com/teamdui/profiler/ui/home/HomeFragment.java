package com.teamdui.profiler.ui.home;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.HomeFragmentBinding;
import com.teamdui.profiler.ui.dailycalorie.DailyCalorieFragment;
import com.teamdui.profiler.ui.dailycalorie.DailyExerciseFragment;
import com.teamdui.profiler.ui.dailycalorie.DailyMealFragment;
import com.teamdui.profiler.ui.history.date;
import com.teamdui.profiler.ui.profile.ProfileData;

import java.text.DecimalFormat;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.teamdui.profiler.MainActivity.burnedCalorie;
import static com.teamdui.profiler.MainActivity.bytesProfileImage;
import static com.teamdui.profiler.MainActivity.calorieDaily;
import static com.teamdui.profiler.MainActivity.calorieGoal;
import static com.teamdui.profiler.MainActivity.exerciseDaily;
import static com.teamdui.profiler.MainActivity.exerciseGoal;
import static com.teamdui.profiler.MainActivity.fullName;
import static com.teamdui.profiler.MainActivity.glassDaily;
import static com.teamdui.profiler.MainActivity.glassGoal;
import static com.teamdui.profiler.MainActivity.headerView;
import static com.teamdui.profiler.MainActivity.netCalorie;
import static com.teamdui.profiler.ui.util.TextUpdater.textSetter;

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
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = HomeFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeViewModel.getData2().observe(getViewLifecycleOwner(), new Observer<ProfileData>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(ProfileData profileData) {
                bytesProfileImage = Base64.getDecoder().decode(profileData.Image);
                fullName = profileData.fname + profileData.lname;
                if (bytesProfileImage != null)
                    binding.profileImg.setImageBitmap(BitmapFactory.decodeByteArray(bytesProfileImage, 0, bytesProfileImage.length));

                binding.userNameText.setText(fullName);
                TextView name = headerView.findViewById(R.id.fullName);
                name.setText(fullName);
                CircleImageView profileImage = headerView.findViewById(R.id.profileImg);
                profileImage.setImageBitmap(BitmapFactory.decodeByteArray(bytesProfileImage, 0, bytesProfileImage.length));
            }
        });

        homeViewModel.getData().observe(getViewLifecycleOwner(), new Observer<date>() {
            @Override
            public void onChanged(date data) {
                calorieDaily = data.progress.cal;
                calorieGoal = data.set.cal;
                glassDaily = data.progress.wat;
                glassGoal = data.set.wat;
                exerciseDaily = data.progress.exr;
                exerciseGoal = data.set.exr;
                burnedCalorie = data.progress.calburn;
                netCalorie = (double) data.progress.cal - data.progress.calburn;


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

            }
        });

        cameraIcon = binding.camera;
        cameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_camera);
            }
        });


        return root;
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
        calProgressBar.setProgress(caloriePercentage);
        waterProgressBar.setProgress(waterPercentage);
        exerciseProgressBar.setProgress(exercisePercentage);
        setProgressText();
    }

    public void setProgressText() {
        textSetter(calDailyText, String.valueOf(calorieDaily));
        textSetter(calGoalText, String.valueOf(calorieGoal));
        textSetter(glassDailyText, String.valueOf(glassDaily));
        textSetter(glassGoalText, String.valueOf(glassGoal));
        textSetter(exerciseDailyText, String.valueOf(exerciseDaily));
        textSetter(exerciseGoalText, String.valueOf(exerciseGoal));
    }

    public void setCalorieText() {
        textSetter(calEarnText, calEarnText.getText().toString() + " " + (double) calorieDaily);
        burnedCalorie = Double.valueOf(df.format(burnedCalorie));
        netCalorie = Double.valueOf(df.format(netCalorie));
        textSetter(calBurnText, calBurnText.getText().toString() + " " + burnedCalorie);
        if (netCalorie > 0) {
            textSetter(calNetText, calNetText.getText().toString() + " " + "+" + netCalorie);
        } else {
            textSetter(calNetText, calNetText.getText().toString() + " " + netCalorie);
        }
    }


}