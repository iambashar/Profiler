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
import com.teamdui.profiler.databinding.HistoryFragmentBinding;
import java.util.ArrayList;

public class History extends Fragment {

    private HistoryViewModel mViewModel;
    private HistoryFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = HistoryFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mViewModel.getRawData().observe(getViewLifecycleOwner(), new Observer<ArrayList<rec>>() {
            @Override
            public void onChanged(ArrayList<rec> recs) {
                //TODO: Show in table
            }
        });

        return root;
    }
}