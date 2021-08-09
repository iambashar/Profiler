package com.teamdui.profiler.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    // TODO: Implement the ViewModel
    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Profile fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}