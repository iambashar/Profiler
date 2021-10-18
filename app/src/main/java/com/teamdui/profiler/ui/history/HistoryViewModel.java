package com.teamdui.profiler.ui.history;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.teamdui.profiler.MainActivity.myRef;
import static com.teamdui.profiler.MainActivity.uid;

public class HistoryViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<rec>> rawData = new MutableLiveData<>();
    ArrayList<rec> raw = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public HistoryViewModel() {

        try {
            myRef.child(uid).child("date").addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot myDataSnapshot : snapshot.getChildren()) {
                            date dataPoint = myDataSnapshot.getValue(date.class);//create datapoint object
                            raw.add(new rec(dataPoint.progress.cal, dataPoint.progress.calburn, myDataSnapshot.getKey()));
                        }
                        rawData.setValue(raw);
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        } catch (Exception e) {

        }
    }

    public LiveData<ArrayList<rec>> getRawData() {
        return rawData;
    }
}