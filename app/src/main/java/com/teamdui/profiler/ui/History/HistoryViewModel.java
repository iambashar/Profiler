package com.teamdui.profiler.ui.History;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import static com.teamdui.profiler.MainActivity.uid;

public class HistoryViewModel extends ViewModel {
    private MutableLiveData<List<BarEntry>> dataEntries;
    private MutableLiveData<TreeMap<String, set>> rawData;
    private MutableLiveData<Integer> daysRange;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public HistoryViewModel() {
        dataEntries = new MutableLiveData<>();
        daysRange = new MutableLiveData<>();
        rawData = new MutableLiveData<>();

        queryData();

        ArrayList<BarEntry> list = new ArrayList<>();
        list.add(new BarEntry(1, 234));
        list.add(new BarEntry(2, 235));
        list.add(new BarEntry(1, 231));
        list.add(new BarEntry(4, 232));
        dataEntries.setValue(list);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void queryData() {
        TreeMap<String, set> data = new TreeMap<>(Comparator.reverseOrder());
        DatabaseReference dbRef =
                FirebaseDatabase
                        .getInstance()
                        .getReference(String.format("userid/%s/date", uid));

        LocalDate date = LocalDate.now();
        date.minusDays(30).toString();


    }

    public LiveData<List<BarEntry>> getDataEntries() { return dataEntries; }
    public LiveData<Integer> getDaysRange() { return daysRange; }
    public LiveData<TreeMap<String, set>> getRawData() { return rawData; }
}