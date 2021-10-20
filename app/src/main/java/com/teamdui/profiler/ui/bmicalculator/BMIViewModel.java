package com.teamdui.profiler.ui.bmicalculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BMIViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BMIViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is BMI Calculator fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}