package com.teamdui.profiler.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.teamdui.profiler.MainActivity;
import com.teamdui.profiler.ui.history.date;
import com.teamdui.profiler.ui.profile.ProfileData;

import org.jetbrains.annotations.NotNull;

import static com.teamdui.profiler.MainActivity.date;
import static com.teamdui.profiler.MainActivity.uid;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<date> mData;
    private final MutableLiveData<ProfileData> mData2;

    public HomeViewModel() {
        mData = new MutableLiveData<>();
        mData2 = new MutableLiveData<>();
        MainActivity.myRef
                .child(MainActivity.uid)
                .child("profile")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        mData2.setValue(snapshot.exists() ? snapshot.getValue(ProfileData.class) : new ProfileData());
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        //
                    }
                });

        MainActivity.myRef.child(uid).child("date").child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                mData.setValue(snapshot.exists() ? snapshot.getValue(com.teamdui.profiler.ui.history.date.class) : new date());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                //
            }
        });
    }

    public MutableLiveData<date> getData() {
        return mData;
    }


    public LiveData<ProfileData> getData2() {
        return mData2;
    }
}