package com.teamdui.profiler.ui.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.teamdui.profiler.MainActivity;

import org.jetbrains.annotations.NotNull;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<ProfileData> mData;

    public ProfileViewModel() {
        mData = new MutableLiveData<>();
        MainActivity.myRef
                .child(MainActivity.uid)
                .child("profile")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        mData.setValue(snapshot.exists() ? snapshot.getValue(ProfileData.class) : new ProfileData());
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        //
                    }
                });
    }

    public LiveData<ProfileData> getData() {
        return mData;
    }
}