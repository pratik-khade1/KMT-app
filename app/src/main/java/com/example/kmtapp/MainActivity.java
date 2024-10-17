package com.example.kmtapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
//import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private RadioButton radioButtonMobile, radioButtonEmail;
    private EditText editTextMobile, editTextEmail;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        RadioGroup radioGroupToggle = findViewById(R.id.radioGroupToggle);
        radioButtonMobile = findViewById(R.id.radioButtonMobile);
        radioButtonEmail = findViewById(R.id.radioButtonEmail);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextEmail = findViewById(R.id.editTextEmail);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);

        // Hide email field by default
        editTextEmail.setVisibility(View.GONE);

        // Set default checked button to mobile
        radioButtonMobile.setChecked(true); // Set mobile button as default checked

        // Toggle between mobile and email registration
        radioGroupToggle.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonMobile) {
                editTextMobile.setVisibility(View.VISIBLE);
                editTextEmail.setVisibility(View.GONE);
            } else {
                editTextMobile.setVisibility(View.GONE);
                editTextEmail.setVisibility(View.VISIBLE);
                // Show the email EditText when email button is checked
                editTextEmail.setVisibility(View.VISIBLE);
            }
        });

        // Handle Register Button Click (either mobile or email registration)
        btnRegister.setOnClickListener(v -> {
            if (radioButtonMobile.isChecked()) {
                String mobileNumber = editTextMobile.getText().toString().trim();
                if (TextUtils.isEmpty(mobileNumber)) {
                    Toast.makeText(MainActivity.this, "Enter a valid mobile number", Toast.LENGTH_SHORT).show();
                } else {
                    // Show progress bar and initiate OTP process
                    progressBar.setVisibility(View.VISIBLE);
                    sendVerificationCode(mobileNumber);
                }
            } else if (radioButtonEmail.isChecked()) {
                String email = editTextEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
                } else {
                    // Show progress bar and perform email registration
                    progressBar.setVisibility(View.VISIBLE);
                    registerWithEmail(email);
                }
            }
        });

        // Handle Login Button Click
        btnLogin.setOnClickListener(v -> {
            // Redirect to login activity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    // Method to send OTP to the provided mobile number
    private void sendVerificationCode(String mobileNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+91" + mobileNumber)  // Change country code if necessary
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        progressBar.setVisibility(View.GONE);  // Hide progress bar
                        // Handle auto OTP verification
                        String code = phoneAuthCredential.getSmsCode();
                        if (code != null) {
                            verifyCode(code);
                        }
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        progressBar.setVisibility(View.GONE);  // Hide progress bar
                        Toast.makeText(MainActivity.this, "Verification Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        progressBar.setVisibility(View.GONE);  // Hide progress bar
                        verificationId = s;
                        Toast.makeText(MainActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                        // Redirect to OTP Verification Activity
                        Intent intent = new Intent(MainActivity.this, OtpVerificationActivity.class);
                        intent.putExtra("verificationId", verificationId);
                        startActivity(intent);
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // Method to verify the OTP code
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    // Method to sign in with phone credential
    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // OTP verified, proceed to the next screen
                        Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Method for email registration (this is just an example, you should implement actual logic)
    private void registerWithEmail(String email) {
        // Example: Firebase authentication for email and password (you need to set a password in your app)
        // Replace this with actual logic
        mAuth.createUserWithEmailAndPassword(email, "password123")
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);  // Hide progress bar
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        // Redirect to home activity
                        Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
