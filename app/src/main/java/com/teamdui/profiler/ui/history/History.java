package com.teamdui.profiler.ui.history;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.HistoryFragmentBinding;
import com.teamdui.profiler.ui.dailycalorie.AdapterFood;
import com.teamdui.profiler.ui.dailycalorie.Food;

import java.util.ArrayList;
import java.util.Date;

import static com.teamdui.profiler.MainActivity.myRef;
import static com.teamdui.profiler.MainActivity.uid;

public class History extends Fragment {

    private HistoryViewModel mViewModel;
    private HistoryFragmentBinding binding;


    adapterRecord records;
    LinearLayoutManager recLayoutManager;
    RecyclerView recRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = HistoryFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recRecyclerView = binding.historyList;

        mViewModel.getRawData().observe(getViewLifecycleOwner(), new Observer<ArrayList<rec>>() {
            @Override
            public void onChanged(ArrayList<rec> recs) {
                records = new adapterRecord(recs);
                recLayoutManager = new LinearLayoutManager(getContext());
                recLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recRecyclerView.setLayoutManager(recLayoutManager);
                recRecyclerView.setAdapter(records);
                records.notifyDataSetChanged();
            }
        });

        return root;
    }
}