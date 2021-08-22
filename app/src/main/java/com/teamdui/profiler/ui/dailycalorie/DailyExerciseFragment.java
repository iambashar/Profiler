package com.teamdui.profiler.ui.dailycalorie;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.FragmentDailyExerciseBinding;
import com.teamdui.profiler.databinding.FragmentDailycalorieBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DailyExerciseFragment extends Fragment {
    public FragmentDailyExerciseBinding binding;

    public AutoCompleteTextView autoCompleteTextView;
    public EditText hourInput;
    public EditText minInput;
    public static int exerciseDaily = 0;
    public static int finalExerciseDaily = 0;
    AppCompatButton addButton;

    public static RecyclerView exerciseRecyclerView;
    LinearLayoutManager exerciseLayoutManager;
    public static List<Exercise> exerciseList;
    public static AdapterExercise adapterExercise;
    ImageView deleteExercisebtn;

    String category = "";
    String timeinMin = "";
    Double burnHour = 0.0;
    public static Double burnedCalorie = 0.0;
    public static TextView calorieBurnText;

    DecimalFormat df = new DecimalFormat("#.##");

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public DailyExerciseFragment(){
        if (exerciseList == null){
            exerciseList = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDailyExerciseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        autoCompleteTextView = binding.exerciseDropDown;
        String []option = getResources().getStringArray(R.array.exercise_dropdown);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_item, option);
        autoCompleteTextView.setAdapter(arrayAdapter);
        ((AutoCompleteTextView)binding.dropdownMenu.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedValue = (String) arrayAdapter.getItem(position);
                binding.categoryText.setText(selectedValue);
                binding.dropdownMenu.setHint("");
                autoCompleteTextView.clearFocus();
                setColrieText(position);
            }
        });
        autoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                    binding.categoryText.setText(autoCompleteTextView.getText().toString());
                    hideKeyboard(v);
            }
        });

        binding.categoryText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });
        binding.calorieText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });

        hourInput = binding.hourInput;
        minInput = binding.minuteInput;
        hourInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });
        minInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);

            }
        });

        calorieBurnText = binding.calorieburnText;
        exerciseRecyclerView = binding.exerciseList;
        initRecyclerView();
        addButton = binding.addButton;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfTextFilled())
                {
                    int x = 0;
                    if(!hourInput.getText().toString().isEmpty())
                    {
                        int hour = Integer.parseInt(hourInput.getText().toString());
                        exerciseDaily = exerciseDaily + hour * 60;
                        x = x + hour * 60;
                    }
                    if(!minInput.getText().toString().isEmpty())
                    {
                        int min = Integer.parseInt(minInput.getText().toString());
                        exerciseDaily = exerciseDaily + min;
                        x = x + min;
                    }
                    category = binding.categoryText.getText().toString();
                    timeinMin = Integer.toString(x) + " min";
                    calculateBurnedCalorie();
                    initExerciseList();
                    initRecyclerView();
                }
            }
        });

        burnedCalorie = Double.valueOf(df.format(burnedCalorie));
        binding.calorieburnText.setText(Double.toString(burnedCalorie));


        // Inflate the layout for this fragment
        return root;
    }


    public void setColrieText(int idx)
    {
        if(idx == 0)
        {
            binding.calorieText.setText("285");
        }
        else if(idx == 1)
        {
            binding.calorieText.setText("560");
        }
        else if(idx == 2)
        {
            binding.calorieText.setText("400");
        }
        else if(idx == 3)
        {
            binding.calorieText.setText("600");
        }

    }

    public void calculateBurnedCalorie()
    {
        double caloriePerMin= Double.parseDouble(binding.calorieText.getText().toString()) / 60;
        burnHour = Double.parseDouble(binding.calorieText.getText().toString());
        burnedCalorie = exerciseDaily * caloriePerMin;
        burnedCalorie = Double.valueOf(df.format(burnedCalorie));
        calorieBurnText.setText(Double.toString(burnedCalorie));
    }

    public void reduceTime(String toReduce, Double caloriePerHour)
    {
        toReduce = toReduce.replace(" min", "");
        exerciseDaily = exerciseDaily - Integer.parseInt(toReduce);
        double caloriePerMin = caloriePerHour / 60;
        burnedCalorie = exerciseDaily * caloriePerMin;
        burnedCalorie = Double.valueOf(df.format(burnedCalorie));
        calorieBurnText.setText(Double.toString(burnedCalorie));
    }


    public boolean checkIfTextFilled()
    {
        if(binding.categoryText.getText().toString().isEmpty() || binding.calorieText.getText().toString().isEmpty() ||
                (binding.hourInput.getText().toString().isEmpty() && binding.minuteInput.getText().toString().isEmpty()))
        {
            Toast.makeText(this.getContext(), "You must Fill All the Fields", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public void initRecyclerView()
    {
        adapterExercise = new AdapterExercise(exerciseList);
        exerciseLayoutManager = new LinearLayoutManager(this.getContext());
        exerciseLayoutManager.setOrientation(RecyclerView.VERTICAL);
        exerciseRecyclerView.setLayoutManager(exerciseLayoutManager);
        exerciseRecyclerView.setAdapter(adapterExercise);
        adapterExercise.notifyDataSetChanged();
    }

    public void initExerciseList()
    {
        exerciseList.add(new Exercise(category, timeinMin, R.drawable.ic_minus, burnHour));
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public int getExerciseDaily()
    {
        return exerciseDaily;
    }
    public double getBurnedCalorie()
    {
        return burnedCalorie;
    }
}