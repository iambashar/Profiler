package com.teamdui.profiler.ui.register;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.ActivityRegisterBinding;
import com.teamdui.profiler.ui.login.LoginActivity;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private Button registerButton;
    private Button backButton;
    private ProgressBar progressBar;

    static String passwordRegex;

    private static final int minPasswordLength = 8;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef2;

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        emailText = binding.registerEmail;
        passwordText = binding.registerPassword;
        confirmPasswordText = binding.confirmPassword;
        registerButton = binding.registerButton;
        progressBar = binding.registerProgress;
        backButton = binding.backButton;

        passwordRegex = getString(R.string.password_regex);

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

                if(TextUtils.isEmpty(email))
                {
                    emailText.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    passwordText.setError("Password is required");
                    return;
                }

                if(!TextUtils.equals(password, confirm))
                {
                    confirmPasswordText.setError("Passwords don't match!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(100, true);



                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            myRef2 = FirebaseDatabase.getInstance("https://profiler-280f7-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("userid");
                            FirebaseUser uid1 = task.getResult().getUser();
                            String uid2 = uid1.getUid().toString();
                            LocalDate todayDate = LocalDate.now();
                            String date = todayDate.toString();
                            myRef2.child(uid2).child("date").child(date).child("set").child("cal").setValue(0);
                            myRef2.child(uid2).child("date").child(date).child("set").child("exr").setValue(0);
                            myRef2.child(uid2).child("date").child(date).child("set").child("wat").setValue(0);
                            myRef2.child(uid2).child("calburn").setValue(0);

                            myRef2.child(uid2).child("date").child(date).child("progress").child("cal").setValue(0);
                            myRef2.child(uid2).child("date").child(date).child("progress").child("exr").setValue(0);
                            myRef2.child(uid2).child("date").child(date).child("progress").child("wat").setValue(0);
                            Toast.makeText(getApplicationContext(), "Registration Success!", Toast.LENGTH_SHORT).show();
                            SendVerificationEmail(uid1);
                            //BackToLoginPage();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "User already exists / Registration Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!ValidateEmail(emailText.getText()))
                {
                    emailText.setError("Invalid Email");
                }
                else
                {
                    emailText.setError(null);
                }
            }
        });

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!ValidatePassword(passwordText.getText()))
                {
                    passwordText.setError("Must be at least " + minPasswordLength + " characters with at least 1 capital 1 small and 1 number");
                }
            }
        });




    }

    private void SendVerificationEmail(FirebaseUser user)
    {
        if(user == null)
            return;
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Log.d("VERIFY EMAIL", "SENT");
                        }
                    }
                });
    }


    public static boolean ValidateEmail(CharSequence email)
    {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean ValidatePassword(CharSequence password)
    {
        int len = password.length();

        Pattern p = Pattern.compile(passwordRegex);


        if(!p.matcher(password).matches())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private void BackToLoginPage()
    {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }
}