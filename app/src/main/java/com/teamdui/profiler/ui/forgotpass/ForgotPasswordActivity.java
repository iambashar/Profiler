package com.teamdui.profiler.ui.forgotpass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.teamdui.profiler.databinding.ActivityForgotPasswordBinding;
import com.teamdui.profiler.databinding.ActivityMainBinding;
import com.teamdui.profiler.ui.login.LoginActivity;

import java.io.Console;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;

    private EditText emailText;
    private Button sendEmailButton;
    private Button backButton;
    private TextView instructionText;
    private FirebaseAuth mAuth;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        instructionText = binding.forgotInstructions;
        emailText = binding.resetEmail;
        sendEmailButton = binding.sendButton;
        backButton = binding.backButton;

        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMail();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToLogin();
            }
        });

    }


    private void SendMail()
    {
        userEmail = emailText.getText().toString().trim();
        mAuth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //instructionText.setText("Email Sent");
                            Log.d("FORGOT PASS", "EMAIL SENT");
                            GoToLogin();
                        }
                    }
                });
    }

    private void GoToLogin()
    {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }




}