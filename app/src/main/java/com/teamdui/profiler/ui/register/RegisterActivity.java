package com.teamdui.profiler.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.teamdui.profiler.databinding.ActivityRegisterBinding;
import com.teamdui.profiler.ui.login.LoginActivity;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private Button registerButton;
    private Button backButton;
    private ProgressBar progressBar;

    private static final int minPasswordLength = 5;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

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
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser user = task.getResult().getUser();
                            myRef = FirebaseDatabase.getInstance("https://profiler-280f7-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("userid");
                            myRef.child(user.getUid().toString()).setValue("date");
                            Toast.makeText(getApplicationContext(), "Registration Success!", Toast.LENGTH_SHORT).show();
                            BackToLoginPage();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Registration Failed!", Toast.LENGTH_SHORT).show();
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
                    passwordText.setError("Must be at least " + minPasswordLength + " characters.");
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
        if(len < minPasswordLength)
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