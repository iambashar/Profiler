package com.teamdui.profiler.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthCredential;
import com.teamdui.profiler.MainActivity;
import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.ActivityLoginBinding;
import com.teamdui.profiler.ui.register.RegisterActivity;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends Activity {

    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;
    private ProgressBar loginProgressBar;
    private TextView registerButton;
    private SignInButton googleSignInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN = 123;

    private FirebaseAuth mAuth;

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
        registerButton = binding.signUp;



        //Button
        googleSignInButton = binding.signInButton;
        googleSignInButton.setSize(SignInButton.SIZE_STANDARD);
        //Google signin options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


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

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.sign_in_button)
                {
                    signInWithGoogle();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToRegisterPage();
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void updateUI(FirebaseUser user) {
        if(user == null)
        {
            Toast.makeText(getApplicationContext(), "Authentication Failed!", Toast.LENGTH_SHORT).show();
            emailText.getText().clear();
            passwordText.getText().clear();
        }
        else
        {
            Intent loginSuccessIntent = new Intent(this, MainActivity.class);
            startActivity(loginSuccessIntent);
            finish();
        }
    }

    private void processGoogleAccount(GoogleSignInAccount account)
    {
        if(account == null)
        {
            return;
        }
        else
        {
            firebaseAuthWithGoogle(account.getIdToken());
        }
    }


    private void signInWithGoogle()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try{
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            processGoogleAccount(account);
        }
        catch (ApiException e)
        {
            Log.w("GOOGLE SIGNIN:", "Google signin failed!" + e.getStatusCode());
        }

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Authentication Success!", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else
                        {
                            updateUI(null);
                        }
                    }
                });

    }





    private void GoToRegisterPage()
    {
        Intent registerIntent = new Intent( this, RegisterActivity.class);
        startActivity(registerIntent);
    }
}