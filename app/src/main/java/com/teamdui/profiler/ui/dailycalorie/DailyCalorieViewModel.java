package com.teamdui.profiler.ui.dailycalorie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DailyCalorieViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DailyCalorieViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Daily Calorie fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}