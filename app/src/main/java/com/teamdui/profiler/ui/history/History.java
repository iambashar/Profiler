package com.teamdui.profiler.ui.history;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.HistoryFragmentBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class History extends Fragment {

    private HistoryViewModel mViewModel;
    private HistoryFragmentBinding binding;


    adapterRecord records;
    LinearLayoutManager recLayoutManager;
    ImageView downloadButton;
    RecyclerView recRecyclerView;
    AutoCompleteTextView autoCompleteTextView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = HistoryFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recRecyclerView = binding.historyList;

        autoCompleteTextView = binding.historyRangeDropDown;
        String[] ranges = getResources().getStringArray(R.array.history_dropdown);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.getContext(), R.layout.history_dropdown, ranges);
        autoCompleteTextView.setAdapter(arrayAdapter);
        downloadButton = binding.downloadButton;

        ((AutoCompleteTextView) binding.dropdownRange.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedValue = (String) arrayAdapter.getItem(position);
                binding.dropdownRange.setHint("");
                autoCompleteTextView.clearFocus();
                showRecords(position);
            }
        });

        return root;
    }

    public void showRecords(int idx)
    {
        if (idx == 0){
            mViewModel.getRawData7().observe(getViewLifecycleOwner(), new Observer<ArrayList<rec>>() {
                @Override
                public void onChanged(ArrayList<rec> recs) {
                    records = new adapterRecord(recs);
                    recLayoutManager = new LinearLayoutManager(getContext());
                    recLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    recRecyclerView.setLayoutManager(recLayoutManager);
                    recRecyclerView.setAdapter(records);
                    records.notifyDataSetChanged();

                    downloadButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            savefileascsv(recs);
                        }
                    });
                }
            });}
        else if (idx == 1){
            mViewModel.getRawData30().observe(getViewLifecycleOwner(), new Observer<ArrayList<rec>>() {
                @Override
                public void onChanged(ArrayList<rec> recs) {
                    records = new adapterRecord(recs);
                    recLayoutManager = new LinearLayoutManager(getContext());
                    recLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    recRecyclerView.setLayoutManager(recLayoutManager);
                    recRecyclerView.setAdapter(records);
                    records.notifyDataSetChanged();

                    downloadButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            savefileascsv(recs);
                        }
                    });
                }
            });
        }
        else{
            mViewModel.getRawData180().observe(getViewLifecycleOwner(), new Observer<ArrayList<rec>>() {
                @Override
                public void onChanged(ArrayList<rec> recs) {
                    records = new adapterRecord(recs);
                    recLayoutManager = new LinearLayoutManager(getContext());
                    recLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    recRecyclerView.setLayoutManager(recLayoutManager);
                    recRecyclerView.setAdapter(records);
                    records.notifyDataSetChanged();

                    downloadButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            savefileascsv(recs);
                        }
                    });
                }
            });
        }
    }

    public void savefileascsv(ArrayList<rec> recs){
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss", Locale.getDefault());
        String ts = sdf.format(new Date());
        File file = new File(Environment.getExternalStorageDirectory() + "/Documents" + File.separator  + "/" + "Profiler-" + ts + ".csv");
        FileOutputStream outputStream;

        try {
            outputStream = new FileOutputStream(file, true);
            outputStream.write(("Date,").getBytes());
            outputStream.write(("Earned Calorie,").getBytes());
            outputStream.write(("Burned Calorie,").getBytes());
            outputStream.write(("Net Calorie\n").getBytes());
            for(rec r: recs) {
                String date2 = r.date.substring(8,10)+"/"+r.date.substring(5,7)+"/"+r.date.substring(0,4);
                outputStream.write((date2+ ",").getBytes());
                outputStream.write((r.cal + ",").getBytes());
                outputStream.write((r.calburn + ",").getBytes());
                outputStream.write((r.cal-r.calburn + "\n").getBytes());
            }
            outputStream.close();
            Toast.makeText(getContext(), "Records are saved in \"Documents/Profiler-" + ts + ".csv\"", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}