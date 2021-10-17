package com.teamdui.profiler.ui.dailycalorie;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.FragmentDailyMealBinding;

import java.util.ArrayList;

import static com.teamdui.profiler.MainActivity.calorieDaily;
import static com.teamdui.profiler.MainActivity.date;
import static com.teamdui.profiler.MainActivity.myRef;
import static com.teamdui.profiler.MainActivity.uid;

public class DailyMealFragment extends Fragment {

    public FragmentDailyMealBinding binding;

    public AppCompatButton addbtn;
    TextView takePhoto;
    public static TextView calorieUpperText;

    String food = "";
    String calorie = "";

    RecyclerView foodRecyclerView;
    LinearLayoutManager foodLayoutManager;
    public static ArrayList<Food> foodList;
    AdapterFood adapterFood;

    public DailyMealFragment(){
        if (foodList == null){
            foodList = new ArrayList<>();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDailyMealBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        calorieUpperText = binding.calorieUpperText;

        foodRecyclerView = binding.foodList;
        addbtn = binding.addButton;

        calorieUpperText.setText(((Integer)calorieDaily).toString());

        initRecyclerView();

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfTextFilled())
                {
                    food = binding.foodText.getText().toString();
                    calorie = binding.calorieText.getText().toString();
                    calorieDaily = calorieDaily + Integer.parseInt(calorie);
                    myRef.child(uid).child("date").child(date).child("progress").child("cal").setValue(calorieDaily);
                    calorieUpperText.setText(((Integer)calorieDaily).toString());
                    hideKeyboard(v);
                    initFoodList(food, calorie);
                    initRecyclerView();
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

        return root;
    }

    public void reduceCalorie(String toReduce)
    {
        toReduce = toReduce.replace(" cal", "");
        calorieDaily = calorieDaily - Integer.parseInt(toReduce);
        myRef.child(uid).child("date").child(date).child("progress").child("cal").setValue(calorieDaily);
        calorieUpperText.setText(((Integer)calorieDaily).toString());
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

    public void initFoodList(String food, String calorie)
    {
        String key = myRef.child(uid).child("date").child(date).child("Meal").push().getKey();
        if (foodList == null){
            foodList = new ArrayList<>();
        }
        foodList.add(new Food(food, calorie, R.drawable.ic_minus, key));
        myRef.child(uid).child("Meal").child(key).child("foodName").setValue(food);
        myRef.child(uid).child("Meal").child(key).child("key").setValue(key);
        myRef.child(uid).child("Meal").child(key).child("calorieEach").setValue(calorie);
        myRef.child(uid).child("Meal").child(key).child("deleteIcon").setValue(R.drawable.ic_minus);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}