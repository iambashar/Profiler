package com.teamdui.profiler.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    // TODO: Implement the ViewModel
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Home fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}