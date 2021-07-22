package com.teamdui.profiler.ui.goaltracker;

import android.hardware.camera2.CameraAccessException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.FragmentGoaltrackerBinding;

public class GoaltrackerFragment extends Fragment {

    private GoaltrackerViewModel goaltrackerViewModel;
    private FragmentGoaltrackerBinding binding;
    public Button goalButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goaltrackerViewModel =
                new ViewModelProvider(this).get(GoaltrackerViewModel.class);

        binding = FragmentGoaltrackerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        goalButton = binding.setGoalButton;
        goalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arg = new Bundle();
                Navigation.findNavController(root).navigate(R.id.action_navigation_goaltracker_to_navigation_goalsave, arg);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}