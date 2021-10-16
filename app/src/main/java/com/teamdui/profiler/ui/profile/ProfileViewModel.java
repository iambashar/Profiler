package com.teamdui.profiler.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.teamdui.profiler.MainActivity;

import java.time.Year;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<ProfileData> mData;

    // TODO: Implement the ViewModel
    public ProfileViewModel() {
        mData = new MutableLiveData<>();
        MainActivity.myRef
                .child(MainActivity.uid)
                .child("profile")
                .get()
                .addOnCompleteListener(task -> mData.setValue(task.getResult().getValue(ProfileData.class)));
    }

    public LiveData<ProfileData> getData() {
        return mData;
    }
}