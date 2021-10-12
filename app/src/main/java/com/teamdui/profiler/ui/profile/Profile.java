package com.teamdui.profiler.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.teamdui.profiler.MainActivity;
import com.teamdui.profiler.databinding.ProfileFragmentBinding;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Profile extends Fragment {

    private ProfileViewModel mViewModel;
    private ProfileFragmentBinding binding;

    private ProfileData data;

    public static Profile newInstance() {
        return new Profile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = ProfileFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MainActivity.myRef
                .child(MainActivity.uid)
                .child("profile")
                .get()
                .addOnCompleteListener(task -> {
                    data = task.getResult().getValue(ProfileData.class);
                    String fullName = data.fname + " " + data.lname;
                    binding.textView4.setText(fullName);
                });

        return root;
    }
}