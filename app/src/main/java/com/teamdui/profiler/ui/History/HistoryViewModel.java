package com.teamdui.profiler.ui.History;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoryViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    // TODO: Implement the ViewModel
    public HistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is History fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}