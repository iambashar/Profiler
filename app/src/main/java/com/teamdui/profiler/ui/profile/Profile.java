package com.teamdui.profiler.ui.profile;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.teamdui.profiler.MainActivity;
import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.ProfileFragmentBinding;

import org.jetbrains.annotations.NotNull;

import java.time.Year;
import java.util.HashMap;

public class Profile extends Fragment {

    private ProfileViewModel mViewModel;
    private ProfileFragmentBinding binding;

    public static Profile newInstance() {
        return new Profile();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = ProfileFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mViewModel.getData().observe(getViewLifecycleOwner(), new Observer<ProfileData>() {
            @Override
            public void onChanged(ProfileData data) {
                String fullName = data.fname + " " + data.lname;
                binding.fullNameText.setText(fullName);
                int age = Integer.parseInt(Year.now().toString()) - data.dob.getYear();
                binding.ageValue.setText(Integer.toString(age));
                String height = String.format("%d' %d\"", data.heightFeet, data.heightInches);
                binding.heightValue.setText(height);
                binding.weightValue.setText(Double.toString(data.weight));
                double heightMetres = (data.heightFeet * 12 + data.heightInches) * 2.54 / 100;
                String bmi = String.format("%.2f", data.weight/(heightMetres*heightMetres));
                binding.bmiValue.setText(bmi);
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_nav_Profile_to_profileEdit);
            }
        });

        //binding.button.setOnClickListener();


        return root;
    }
}