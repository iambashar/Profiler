package com.teamdui.profiler.ui.dailycalorie;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.FragmentDailyExerciseBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.teamdui.profiler.MainActivity.burnedCalorie;
import static com.teamdui.profiler.MainActivity.date;
import static com.teamdui.profiler.MainActivity.exerciseDaily;
import static com.teamdui.profiler.MainActivity.myRef;
import static com.teamdui.profiler.MainActivity.uid;

public class DailyExerciseFragment extends Fragment {
    public FragmentDailyExerciseBinding binding;

    public AutoCompleteTextView autoCompleteTextView;
    public EditText hourInput;
    public EditText minInput;
    AppCompatButton addButton;

    public static RecyclerView exerciseRecyclerView;
    LinearLayoutManager exerciseLayoutManager;
    public static ArrayList<Exercise> exerciseList;
    public static AdapterExercise adapterExercise;

    String category = "";
    String timeinMin = "";
    Double burnHour = 0.0;
    public static TextView calorieBurnText;

    DecimalFormat df = new DecimalFormat("#.##");

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

        burnedCalorie = Double.valueOf(df.format(burnedCalorie));
        binding.calorieburnText.setText(Double.toString(burnedCalorie));
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
                        exerciseDaily += hour * 60;
                        x = x + hour * 60;
                    }
                    if(!minInput.getText().toString().isEmpty())
                    {
                        int min = Integer.parseInt(minInput.getText().toString());
                        exerciseDaily += min;
                        x = x + min;
                    }
                    category = binding.categoryText.getText().toString();
                    timeinMin = Integer.toString(x) + " min";
                    calculateBurnedCalorie(x);
                    initExerciseList();
                    initRecyclerView();
                    myRef.child(uid).child("date").child(date).child("progress").child("exr").setValue(exerciseDaily);
                }
            }
        });

        burnedCalorie = Double.valueOf(df.format(burnedCalorie));
        binding.calorieburnText.setText(Double.toString(burnedCalorie));

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

    public void calculateBurnedCalorie(int x) {
        double caloriePerMin= Double.parseDouble(binding.calorieText.getText().toString()) / 60.0;
        burnHour = Double.parseDouble(binding.calorieText.getText().toString());
        burnedCalorie += x * caloriePerMin;
        myRef.child(uid).child("calburn").setValue(burnedCalorie);
        burnedCalorie = Double.valueOf(df.format(burnedCalorie));
        calorieBurnText.setText(Double.toString(burnedCalorie));
    }

    public void reduceTime(String toReduce, Double caloriePerHour)
    {
        toReduce = toReduce.replace(" min", "");
        exerciseDaily -= Integer.parseInt(toReduce);
        myRef.child(uid).child("date").child(date).child("progress").child("exr").setValue(exerciseDaily);
        double caloriePerMin = caloriePerHour / 60.0;
        burnedCalorie -= Integer.parseInt(toReduce) * caloriePerMin;
        myRef.child(uid).child("calburn").setValue(burnedCalorie);
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
        String key = myRef.child(uid).child("date").child(date).child("Exercise").push().getKey();
        exerciseList.add(new Exercise(category, timeinMin, R.drawable.ic_minus, burnHour, key));
        myRef.child(uid).child("Exercise").child(key).child("catName").setValue(category);
        myRef.child(uid).child("Exercise").child(key).child("timeEach").setValue(timeinMin);
        myRef.child(uid).child("Exercise").child(key).child("deleteIcon").setValue(R.drawable.ic_minus);
        myRef.child(uid).child("Exercise").child(key).child("burnHour").setValue(burnHour);
        myRef.child(uid).child("Exercise").child(key).child("key").setValue(key);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}