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

import java.time.LocalDate;
import java.util.ArrayList;

import static com.teamdui.profiler.MainActivity.myRef;
import static com.teamdui.profiler.MainActivity.uid;

public class HistoryViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<rec>> rawData = new MutableLiveData<>();
    ArrayList<rec> raw = new ArrayList<>();

    private final MutableLiveData<ArrayList<rec>> rawData7;
    ArrayList<rec> raw7 = new ArrayList<>();

    private final MutableLiveData<ArrayList<rec>> rawData30;
    ArrayList<rec> raw30 = new ArrayList<>();
    private final MutableLiveData<ArrayList<rec>> rawData180;
    ArrayList<rec> raw180 = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public HistoryViewModel() {
        raw7.clear();
        raw30.clear();
        raw180.clear();
        rawData7 = new MutableLiveData<>();
        rawData30 = new MutableLiveData<>();
        rawData180 = new MutableLiveData<>();
        myRef.child(uid).child("date").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot myDataSnapshot : snapshot.getChildren()) {
                        date dataPoint = myDataSnapshot.getValue(date.class);//create datapoint object
                        raw.add(new rec(dataPoint.progress.cal, dataPoint.progress.calburn, myDataSnapshot.getKey()));
                    }

                    for (rec r : raw) {
                        if ((r.date.compareTo(LocalDate.now().toString()) <= 0) &&
                                (r.date.compareTo(LocalDate.now().plusDays(7).toString()) < 0)) {
                            raw7.add(r);
                        }
                        if ((r.date.compareTo(LocalDate.now().toString()) <= 0) &&
                                (r.date.compareTo(LocalDate.now().plusDays(30).toString()) < 0)) {
                            raw30.add(r);
                        }
                        if ((r.date.compareTo(LocalDate.now().toString()) <= 0) &&
                                (r.date.compareTo(LocalDate.now().plusDays(180).toString()) < 0)) {
                            raw180.add(r);
                        }
                    }
                    rawData7.setValue(raw7);
                    rawData30.setValue(raw30);
                    rawData180.setValue(raw180);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    public LiveData<ArrayList<rec>> getRawData7() {
        return rawData7;
    }

    public LiveData<ArrayList<rec>> getRawData30() {
        return rawData30;
    }

    public LiveData<ArrayList<rec>> getRawData180() {
        return rawData180;
    }
}