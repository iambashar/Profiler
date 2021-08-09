package com.teamdui.profiler.ui.dailycalorie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.navigation.NavigationBarView;
import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.FragmentDailyExerciseBinding;
import com.teamdui.profiler.databinding.FragmentDailycalorieBinding;

public class DailyExerciseFragment extends Fragment {
    private FragmentDailyExerciseBinding binding;

    public AutoCompleteTextView autoCompleteTextView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDailyExerciseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        autoCompleteTextView = binding.exerciseDropDown;
        String []option = getResources().getStringArray(R.array.exercise_dropdown);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.adapter_exercise, option);
        autoCompleteTextView.setAdapter(arrayAdapter);
        ((AutoCompleteTextView)binding.dropdownMenu.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedValue = (String) arrayAdapter.getItem(position);
                binding.calorieText.setText(selectedValue);
            }
        });
        autoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!autoCompleteTextView.getText().toString().equals("Select Category\0"))
                {
                    binding.calorieText.setText(autoCompleteTextView.getText().toString());
                }
            }
        });

        // Inflate the layout for this fragment
        return root;
    }
}