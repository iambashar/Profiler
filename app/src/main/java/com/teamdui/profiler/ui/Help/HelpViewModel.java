package com.teamdui.profiler.ui.Help;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HelpViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    // TODO: Implement the ViewModel
    public  HelpViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Help fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}