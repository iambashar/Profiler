package com.teamdui.profiler.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.teamdui.profiler.MainActivity;
import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.ActivityLoginBinding;
import com.teamdui.profiler.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.io.Console;
import java.io.FileInputStream;

public class LoginActivity extends AppCompatActivity {

    EditText emailText;
    EditText passwordText;
    Button loginButton;
    ProgressBar loginProgressBar;

    FirebaseAuth mAuth;

    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        emailText = binding.editTextTextEmailAddress;
        passwordText = binding.editTextTextPassword;
        loginButton = binding.loginButton;
        loginProgressBar = binding.loginProgressBar;

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null)
        {
            updateUI(mAuth.getCurrentUser());
            finish();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

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

                loginProgressBar.setVisibility(View.VISIBLE);
                loginProgressBar.setProgress(100, true);

                mAuth.signInWithEmailAndPassword(email, password).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                }
                                else
                                {
                                    updateUI(null);
                                }


                                loginProgressBar.setVisibility(View.INVISIBLE);
                            }
                        });


            }
        });
        emailText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        passwordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if(user == null)
        {
            Toast.makeText(getApplicationContext(), "Authentication Failed!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent loginSuccessIntent = new Intent(this, MainActivity.class);
            startActivity(loginSuccessIntent);
            finish();
        }
    }


}