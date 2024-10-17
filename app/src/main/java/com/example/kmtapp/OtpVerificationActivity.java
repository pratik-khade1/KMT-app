package com.example.kmtapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpVerificationActivity extends AppCompatActivity {

    private EditText edtOTP;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Views
        edtOTP = findViewById(R.id.editTextOtp);
        Button btnVerifyOtp = findViewById(R.id.btnVerifyOtp);
        progressBar = findViewById(R.id.progressBarOtp);

        // Get the verification ID from the previous activity
        verificationId = getIntent().getStringExtra("verificationId");

        // OTP Verification Button Click
        btnVerifyOtp.setOnClickListener(v -> {
            String otp = edtOTP.getText().toString().trim();

            if (TextUtils.isEmpty(otp)) {
                Toast.makeText(OtpVerificationActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            verifyCode(otp);
        });
    }

    private void verifyCode(String code) {
        // Create a PhoneAuthCredential with the verification ID and code
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(OtpVerificationActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OtpVerificationActivity.this, HomePageActivity.class));
                        finish();
                    } else {
                        Toast.makeText(OtpVerificationActivity.this, "Verification failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
