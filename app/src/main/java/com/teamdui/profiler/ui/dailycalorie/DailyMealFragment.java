package com.teamdui.profiler.ui.dailycalorie;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.FragmentDailyMealBinding;

import java.util.ArrayList;
import java.util.List;


public class DailyMealFragment extends Fragment {

    public FragmentDailyMealBinding binding;

    public AppCompatButton addbtn;
    TextView takePhoto;
    public static TextView calorieUpperText;

    String food = "";
    String calorie = "";

    public static Integer calorieDaily = 0;


    RecyclerView foodRecyclerView;
    LinearLayoutManager foodLayoutManager;
    List<Food>foodList;
    AdapterFood adapterFood;
    ImageView deleteMealbtn;


    public DailyMealFragment()
    {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDailyMealBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        calorieUpperText = binding.calorieUpperText;
        calorieUpperText.setText(calorieDaily.toString());

        foodList = new ArrayList<>();
        foodRecyclerView = binding.foodList;
        addbtn = binding.addButton;
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfTextFilled())
                {
                    food = binding.foodText.getText().toString();
                    calorie = binding.calorieText.getText().toString();
                    calorieDaily = calorieDaily + Integer.parseInt(calorie);
                    calorieUpperText.setText(calorieDaily.toString());
                    hideKeyboard(v);
                    initFoodList();
                    initRecyclerView();
                }
                System.out.println("ga");

            }
        });

        takePhoto = binding.phototext;
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_dailymeal_to_navigation_camera);
            }
        });

        binding.foodText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });
        binding.foodText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == 66) {
                    // Do your thing.
                    hideKeyboard(v);
                    return false;  // So it is not propagated.
                }
                return false;
            }
        });
        binding.calorieText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == 66) {
                    // Do your thing.
                    hideKeyboard(v);
                    return false;  // So it is not propagated.
                }
                return false;
            }
        });
        binding.calorieText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });



        // Inflate the layout for this fragment
        return root;
    }

    int getCalorieDaily()
    {
        return calorieDaily;
    }

    public void reduceCalorie(String toReduce)
    {
        calorieDaily = calorieDaily - Integer.parseInt(toReduce);
        calorieUpperText.setText(calorieDaily.toString());
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

    public void initRecyclerView()
    {
        adapterFood = new AdapterFood(foodList);
        foodLayoutManager = new LinearLayoutManager(this.getContext());
        foodLayoutManager.setOrientation(RecyclerView.VERTICAL);
        foodRecyclerView.setLayoutManager(foodLayoutManager);
        foodRecyclerView.setAdapter(adapterFood);
        adapterFood.notifyDataSetChanged();
    }

    public void initFoodList()
    {
        foodList.add(new Food(food, calorie, R.drawable.ic_minus));
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}