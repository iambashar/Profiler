package com.teamdui.profiler.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.ProfileEditFragmentBinding;
import com.teamdui.profiler.databinding.ProfileFragmentBinding;

public class ProfileEdit extends Fragment {

    private ProfileViewModel mViewModel;
    private ProfileEditFragmentBinding binding;

    public static ProfileEdit newInstance() {
        return new ProfileEdit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ProfileEditFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        return root;
    }
}