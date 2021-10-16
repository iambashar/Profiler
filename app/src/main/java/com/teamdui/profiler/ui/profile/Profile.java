package com.teamdui.profiler.ui.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.navigation.Navigation;

import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.ProfileFragmentBinding;

import java.time.Year;
import java.util.Base64;

import static com.teamdui.profiler.MainActivity.bytesProfileImage;

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
                if(age == 1900)
                {
                    age = 0;
                }
                binding.ageValue.setText(Integer.toString(age));
                String height = String.format("%d' %d\"", data.heightFeet, data.heightInches);
                binding.heightValue.setText(height);
                binding.weightValue.setText(Double.toString(data.weight));
                double heightMetres = (data.heightFeet * 12 + data.heightInches) * 2.54 / 100;
                String bmi2 = String.format("%.2f", data.weight/(heightMetres*heightMetres));
                binding.bmiValue.setText(bmi2);
                String base64 = data.Image;
                bytesProfileImage = Base64.getDecoder().decode(base64);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytesProfileImage, 0, bytesProfileImage.length);
                binding.profileImg.setImageBitmap(bitmap);
                double bmi = data.weight/(heightMetres*heightMetres);
                String bmiText = String.format("%.2f", data.weight/(heightMetres*heightMetres));
                String bmiRange = "";
                if(bmi < 18.5)
                {
                    bmiRange = " (Underweight)";
                }
                else if(bmi >= 18.5 && bmi <= 24.99)
                {
                    bmiRange = " (Healthy Weight)";
                }
                else if(bmi >= 25 && bmi <= 29.99)
                {
                    bmiRange = " (Overweight)";
                }
                else if(bmi >= 30 )
                {
                    bmiRange = " (Obesity)";
                }
                binding.bmiValue.setText(bmiText + bmiRange);
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