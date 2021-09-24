package com.teamdui.profiler.ui.dailycalorie;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.FragmentDailycalorieBinding;

import static com.teamdui.profiler.MainActivity.calorieDaily;
import static com.teamdui.profiler.MainActivity.calorieGoal;
import static com.teamdui.profiler.MainActivity.exerciseDaily;
import static com.teamdui.profiler.MainActivity.exerciseGoal;

import static com.teamdui.profiler.MainActivity.glassDaily;
import static com.teamdui.profiler.MainActivity.glassGoal;
import static com.teamdui.profiler.MainActivity.myRef;
import static com.teamdui.profiler.MainActivity.uid;
import static com.teamdui.profiler.MainActivity.date;

public class DailyCalorieFragment extends Fragment {

    private DailyCalorieViewModel dailyCalorieViewModel;
    private FragmentDailycalorieBinding binding;
    public static boolean isSaved = false;

    public AppCompatButton addMealbtn;
    public AppCompatButton addExercisebtn;

    public ImageView addWaterbtn;
    public ImageView deleteWaterbtn;
    public static Integer mlDaily = 0;

    public TextView waterGlassInput;
    public TextView waterMlInput;
    public final int waterFactor = 150;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dailyCalorieViewModel =
                new ViewModelProvider(this).get(DailyCalorieViewModel.class);

        binding = FragmentDailycalorieBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setUpperText();
        mlDaily = glassDaily * waterFactor;

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
                myRef.child(uid).child("date").child(date).child("progress").child("wat").setValue(glassDaily);
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
                myRef.child(uid).child("date").child(date).child("progress").child("wat").setValue(glassDaily);
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
                myRef.child(uid).child("date").child(date).child("progress").child("wat").setValue(glassDaily);
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
                myRef.child(uid).child("date").child(date).child("progress").child("wat").setValue(glassDaily);
            }
        });

        return root;
    }

    public void setGlassVisibility()
    {
        Integer gd = glassDaily;
        binding.waterInputGlass.setText(gd.toString());
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
        binding.waterInputGlass.setText(((Integer)glassDaily).toString());
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
        waterGlassInput.setText(((Integer)glassDaily).toString());
        waterMlInput.setText(mlDaily.toString());
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setUpperText()
    {
        TextView calorieText = binding.calorieEarn;
        TextView waterText = binding.waterTaken;
        TextView exerciseText = binding.exerciseDone;
        calorieText.setText("Total calorie earned(cal): " + String.valueOf(calorieDaily) + "/" + String.valueOf(calorieGoal));
        waterText.setText("Water taken(glasses): " + String.valueOf(glassDaily) + "/" + String.valueOf(glassGoal));
        exerciseText.setText("Exercise done(min): "+ String.valueOf(exerciseDaily) + "/" + String.valueOf(exerciseGoal));
    }

}