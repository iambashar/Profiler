package com.teamdui.profiler.ui.History;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.teamdui.profiler.databinding.HistoryFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class History extends Fragment {

    private HistoryViewModel mViewModel;
    private HistoryFragmentBinding binding;
    public  static ArrayList<rec> r = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = HistoryFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mViewModel.getDaysRange().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(Integer integer) {
                ArrayList<BarEntry> list = new ArrayList<>();
            }
        });

        mViewModel.getDataEntries().observe(getViewLifecycleOwner(), new Observer<List<BarEntry>>() {
            @Override
            public void onChanged(List<BarEntry> entries) {
                BarDataSet dataSet = new BarDataSet(entries, "Calories");
                BarData data = new BarData(dataSet);
                binding.historyBarChart.setData(data);
            }
        });

        return root;
    }
}