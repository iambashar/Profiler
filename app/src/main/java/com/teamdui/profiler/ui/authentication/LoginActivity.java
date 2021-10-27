package com.teamdui.profiler.ui.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamdui.profiler.MainActivity;
import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.ActivityLoginBinding;
import com.teamdui.profiler.ui.forgotpass.ForgotPasswordActivity;
import com.teamdui.profiler.ui.history.date;
import com.teamdui.profiler.ui.profile.ProfileData;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public class LoginActivity extends Activity {

    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;
    private ProgressBar loginProgressBar;
    private TextView registerButton;
    private TextView forgotPasswordLink;
    private TextView continueWithoutLink;

    private SignInButton googleSignInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private String signInText;
    private final int RC_SIGN_IN = 123;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        emailText = binding.editTextTextEmailAddress;
        passwordText = binding.editTextTextPassword;
        loginButton = binding.loginButton;
        loginProgressBar = binding.loginProgressBar;
        registerButton = binding.signUp;
        forgotPasswordLink = binding.forgetPassword;
        continueWithoutLink = binding.continueWithoutText;


        //Button
        googleSignInButton = binding.signInButton;
        googleSignInButton.setSize(SignInButton.SIZE_STANDARD);
        //Google signin options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        ImageView showPass = binding.showPassButton;
        showPass.setImageResource(R.drawable.ic_hide_pwd);
        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.editTextTextPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance()))
                {
                    binding.editTextTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPass.setImageResource(R.drawable.ic_show_pwd);
                    int position = binding.editTextTextPassword.getText().toString().length();
                    binding.editTextTextPassword.setSelection(position);
                }
                else
                {
                    binding.editTextTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPass.setImageResource(R.drawable.ic_hide_pwd);
                    int position = binding.editTextTextPassword.getText().toString().length();
                    binding.editTextTextPassword.setSelection(position);
                }
            }
        });


        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            updateUI(mAuth.getCurrentUser());
            finish();
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailText.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordText.setError("Password is required");
                    return;
                }

                loginProgressBar.setVisibility(View.VISIBLE);
                loginProgressBar.setProgress(100, true);

                EnableUIButtons(false);


                mAuth.signInWithEmailAndPassword(email, password).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    updateUI(user);
                                } else {
                                    updateUI(null);
                                }


                                loginProgressBar.setVisibility(View.INVISIBLE);
                                EnableUIButtons(true);
                            }
                        });


            }
        });
        emailText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        passwordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.sign_in_button) {
                    EnableUIButtons(false);
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

        forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToForgotPassword();
            }
        });

        continueWithoutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInAnonymously();
                EnableUIButtons(false);
            }
        });

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Toast.makeText(getApplicationContext(), "Authentication Failed!", Toast.LENGTH_SHORT).show();
            emailText.getText().clear();
            passwordText.getText().clear();
        } else if (!user.isEmailVerified() && !user.isAnonymous()) {
            Toast.makeText(getApplicationContext(), "Please verify your email!", Toast.LENGTH_SHORT).show();
            emailText.getText().clear();
            passwordText.getText().clear();


            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();

        } else {
            Intent loginSuccessIntent = new Intent(this, MainActivity.class);
            startActivity(loginSuccessIntent);
            finish();
        }
    }

    private void processGoogleAccount(GoogleSignInAccount account) {
        if (account == null) {
            return;
        } else {
            firebaseAuthWithGoogle(account.getIdToken());
        }
    }


    private void signInWithGoogle() {
        loginProgressBar.setVisibility(View.VISIBLE);
        loginProgressBar.setProgress(100, true);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            processGoogleAccount(account);
        } catch (ApiException e) {
            Log.w("GOOGLE SIGNIN:", "Google signin failed!" + e.getStatusCode());
        }

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Authentication Success!", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();

                            databaseReference = FirebaseDatabase.getInstance("https://profiler-280f7-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                    .getReference("userid");

                            String userId = user.getUid();
                            LocalDate todayDate = LocalDate.now();
                            String date = todayDate.toString();

                            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        Log.d("USER DATA CREATE:", "USER ALREADY HAS DATA");

                                    } else {
                                        com.teamdui.profiler.ui.history.date data = new date();
                                        data.progress.cal = 0;
                                        data.progress.exr = 0;
                                        data.progress.wat = 0;
                                        data.progress.calburn = 0;
                                        data.set.cal = 0;
                                        data.set.exr = 0;
                                        data.set.wat = 0;
                                        databaseReference.child(userId).child("date").child(date).setValue(data);

                                        ProfileData pd= new ProfileData();

                                        pd.fname = "Anonymous ";
                                        pd.lname = "User";
                                        pd.Image = getString(R.string.propic);
                                        pd.weight= 0;
                                        pd.heightInches = 0;
                                        pd.heightFeet = 0;
                                        databaseReference.child(userId).child("profile").setValue(pd);
                                        databaseReference.child(userId).child("profile").child("dob").removeValue();
                                        databaseReference.child(userId).child("profile").child("dob").child("date").setValue(10);
                                        databaseReference.child(userId).child("profile").child("dob").child("month").setValue(10);
                                        databaseReference.child(userId).child("profile").child("dob").child("year").setValue(1900);
                                        Log.d("USER DATA CREATE:", "CREATE NEW USER DATA");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e("USER DATA CREATE:", "CREATE NEW USER DATA CANCELLED");
                                }

                            });

                            updateUI(user);
                        } else {
                            updateUI(null);
                        }

                        loginProgressBar.setVisibility(View.INVISIBLE);
                        EnableUIButtons(true);
                    }
                });

    }

    private void SignInAnonymously() {
        loginProgressBar.setVisibility(View.VISIBLE);
        loginProgressBar.setProgress(100, true);
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("SIGN IN ANONYMOUS", "Success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w("SIGN IN ANONYMOUS FAIL", task.getException());

                            updateUI(null);
                        }

                        loginProgressBar.setVisibility(View.INVISIBLE);
                        EnableUIButtons(true);
                    }
                });
    }

    private void GoToForgotPassword() {
        Intent forgotPasswordIntent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(forgotPasswordIntent);
    }


    private void GoToRegisterPage() {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    private void EnableUIButtons(boolean value) {
        loginButton.setClickable(value);
        registerButton.setClickable(value);
        forgotPasswordLink.setClickable(value);
        continueWithoutLink.setClickable(value);
        googleSignInButton.setClickable(value);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}