package com.teamdui.profiler.ui.Help;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HelpViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    // TODO: Implement the ViewModel
    public  HelpViewModel() {
        String helpText = "Use the in-app camera to take photos of food items to calculate their " +
                "calorie content. Set goals for daily calorie consumption and exercise from the " +
                "Goal Tracker, and view progress towards said goals.\n" +
                "Meals can be added manually from the Daily Calorie view, additionally BMI can be " +
                "calculated from the BMI calculator";
        mText = new MutableLiveData<>();
        mText.setValue(helpText);
    }
    public LiveData<String> getText() {
        return mText;
    }
}