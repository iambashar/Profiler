package com.teamdui.profiler.ui.Help;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HelpViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    // TODO: Implement the ViewModel
    public HelpViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}