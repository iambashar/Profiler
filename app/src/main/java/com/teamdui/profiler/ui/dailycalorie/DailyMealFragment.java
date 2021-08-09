package com.teamdui.profiler.ui.dailycalorie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.FragmentDailyMealBinding;


public class DailyMealFragment extends Fragment {

    private FragmentDailyMealBinding binding;

    public AppCompatButton addbtn;
    TextView takePhoto;



    public DailyMealFragment()
    {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDailyMealBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        addbtn = binding.addButton;
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfTextFilled())
                {
                    String food = binding.foodText.getText().toString();
                    Integer calorie = Integer.parseInt(binding.calorieText.getText().toString());
                }

            }
        });

        takePhoto = binding.phototext;
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_dailymeal_to_navigation_camera);
            }
        });

        // Inflate the layout for this fragment
        return root;
    }


    public boolean checkIfTextFilled()
    {
        if(binding.calorieText.getText().toString().isEmpty() || binding.foodText.getText().toString().isEmpty())
        {
            Toast.makeText(this.getContext(), "Food Name and Calorie must be Filled", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}