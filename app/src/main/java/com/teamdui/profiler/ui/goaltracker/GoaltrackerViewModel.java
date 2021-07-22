package com.teamdui.profiler.ui.goaltracker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GoaltrackerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GoaltrackerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is goal tracker fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}