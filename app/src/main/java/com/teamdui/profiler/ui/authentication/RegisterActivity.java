package com.teamdui.profiler.ui.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.ActivityRegisterBinding;
import com.teamdui.profiler.ui.history.date;
import com.teamdui.profiler.ui.profile.ProfileData;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private Button registerButton;
    private TextView backButton;
    private ProgressBar progressBar;

    static String passwordRegex;

    private static final int minPasswordLength = 8;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef2;

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        emailText = binding.registerEmail;
        passwordText = binding.registerPassword;
        confirmPasswordText = binding.confirmPassword;
        registerButton = binding.registerButton;
        progressBar = binding.registerProgress;
        backButton = binding.backButton;

        passwordRegex = getString(R.string.password_regex);

        ImageView showPass = binding.showPassButton;
        showPass.setImageResource(R.drawable.ic_hide_pwd);
        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.registerPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance()))
                {
                    binding.registerPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPass.setImageResource(R.drawable.ic_show_pwd);
                    int position = binding.registerPassword.getText().toString().length();
                    binding.registerPassword.setSelection(position);
                }
                else
                {
                    binding.registerPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPass.setImageResource(R.drawable.ic_hide_pwd);
                    int position = binding.registerPassword.getText().toString().length();
                    binding.registerPassword.setSelection(position);
                }
            }
        });

        ImageView showPassConfirm = binding.showPassConfirmButton;
        showPassConfirm.setImageResource(R.drawable.ic_hide_pwd);
        showPassConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.confirmPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance()))
                {
                    binding.confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPassConfirm.setImageResource(R.drawable.ic_show_pwd);
                    int position = binding.confirmPassword.getText().toString().length();
                    binding.confirmPassword.setSelection(position);
                }
                else
                {
                    binding.confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPassConfirm.setImageResource(R.drawable.ic_hide_pwd);
                    int position = binding.confirmPassword.getText().toString().length();
                    binding.confirmPassword.setSelection(position);
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackToLoginPage();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                String confirm = confirmPasswordText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailText.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordText.setError("Password is required");
                    return;
                }

                if (!TextUtils.equals(password, confirm)) {
                    confirmPasswordText.setError("Passwords don't match!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(100, true);


                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            myRef2 = FirebaseDatabase.getInstance("https://profiler-280f7-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("userid");
                            FirebaseUser uid1 = task.getResult().getUser();
                            String uid2 = uid1.getUid();
                            LocalDate todayDate = LocalDate.now();
                            String date = todayDate.toString();
                            date data = new date();
                            data.progress.cal = 0;
                            data.progress.exr = 0;
                            data.progress.wat = 0;
                            data.progress.calburn = 0;
                            data.set.cal = 0;
                            data.set.exr = 0;
                            data.set.wat = 0;
                            myRef2.child(uid2).child("date").child(date).setValue(data);

                            ProfileData pd= new ProfileData();

                            pd.fname = "Anonymous ";
                            pd.lname = "User";

                            pd.Image = getString(R.string.propic);
                            pd.weight= 0;
                            pd.heightInches = 0;
                            pd.heightFeet = 0;
                            myRef2.child(uid2).child("profile").setValue(pd);
                            myRef2.child(uid2).child("profile").child("dob").removeValue();
                            myRef2.child(uid2).child("profile").child("dob").child("date").setValue(10);
                            myRef2.child(uid2).child("profile").child("dob").child("month").setValue(10);
                            myRef2.child(uid2).child("profile").child("dob").child("year").setValue(1900);
                            Toast.makeText(getApplicationContext(), "Registration Success!", Toast.LENGTH_SHORT).show();
                            SendVerificationEmail(uid1);

                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "User already exists / Registration Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        emailText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!ValidateEmail(emailText.getText()) && emailText.length() != 0) {
                    emailText.setError("Invalid Email");
                } else {
                    emailText.setError(null);
                }
                hideKeyboard(v);
            }
        });

        passwordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!ValidatePassword(passwordText.getText()) && passwordText.length() !=0) {
                    passwordText.setError("Must be at least " + minPasswordLength + " characters with at least 1 capital 1 small and 1 number");
                }
                hideKeyboard(v);
            }
        });

        confirmPasswordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (passwordText.getText().toString().compareTo(confirmPasswordText.getText().toString()) != 0
                        && confirmPasswordText.length() != 0){
                    confirmPasswordText.setError("Passwords don't match!");
                }
                hideKeyboard(v);
            }
        });
    }

    private void SendVerificationEmail(FirebaseUser user) {
        if (user == null)
            return;
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("VERIFY EMAIL", "SENT");
                        }
                    }
                });
    }


    public static boolean ValidateEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean ValidatePassword(CharSequence password) {
        int len = password.length();

        Pattern p = Pattern.compile(passwordRegex);


        return p.matcher(password).matches();
    }

    private void BackToLoginPage() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}