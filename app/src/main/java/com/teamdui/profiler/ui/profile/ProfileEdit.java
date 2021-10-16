package com.teamdui.profiler.ui.profile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.teamdui.profiler.databinding.ProfileEditFragmentBinding;

import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.teamdui.profiler.MainActivity.myRef;
import static com.teamdui.profiler.MainActivity.uid;

public class ProfileEdit extends Fragment {

    private ProfileViewModel mViewModel;
    private ProfileEditFragmentBinding binding;
    private CircleImageView profileImage;
    private TextView fName;
    private TextView lName;
    private TextView heightFeet;
    private TextView heightInch;
    private TextView weight;
    private ImageView dob;
    private AppCompatButton profileSaveButton;
    private AppCompatButton cancelButton;
    final Calendar myCalendar = Calendar.getInstance();
    final Date dobDate = new Date();

    public static ProfileEdit newInstance() {
        return new ProfileEdit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ProfileEditFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dob = binding.profileEditDob;
        profileSaveButton = binding.profileSaveButton;
        fName = binding.profileEditFname;
        lName = binding.profileEditLname;
        heightFeet = binding.profileHeightFeet;
        heightInch = binding.profileHeightInch;
        weight = binding.profileWeight;
        profileImage = binding.profileImg;

        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        mViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        mViewModel.getData().observe(getViewLifecycleOwner(), new Observer<ProfileData>() {
            @Override
            public void onChanged(ProfileData data) {
                fName.setText(data.fname);
                lName.setText(data.lname);
                heightFeet.setText(Integer.toString(data.heightFeet));
                heightInch.setText(Integer.toString(data.heightInches));
                weight.setText(Double.toString(data.weight));
            }
        });
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dobDate.setYear(year);
                dobDate.setMonth(month);
                dobDate.setDate(dayOfMonth);
            }
        };

        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date,  myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        profileSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(uid).child("profile").child("fname").setValue(fName.getText().toString());
                myRef.child(uid).child("profile").child("lname").setValue(lName.getText().toString());
                try {
                    myRef.child(uid).child("profile").child("heightFeet").setValue(Integer.parseInt(heightFeet.getText().toString()));
                }
                catch (Exception e){
                    myRef.child(uid).child("profile").child("heightFeet").setValue(0);
                }
                try {
                    myRef.child(uid).child("profile").child("heightInches").setValue(Integer.parseInt(heightInch.getText().toString()));
                }
                catch (Exception e){
                    myRef.child(uid).child("profile").child("heightInches").setValue(0);
                }
                try {
                    myRef.child(uid).child("profile").child("weight").setValue(Double.parseDouble(weight.getText().toString()));
                }
                catch (Exception e){
                    myRef.child(uid).child("profile").child("weight").setValue(0);
                }

                myRef.child(uid).child("profile").child("dob").child("date").setValue(dobDate.getDay());
                myRef.child(uid).child("profile").child("dob").child("month").setValue(dobDate.getMonth());
                myRef.child(uid).child("profile").child("dob").child("year").setValue(dobDate.getYear());
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }

        });

        fName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });
        lName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });
        heightFeet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });
        heightInch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });

        return root;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}