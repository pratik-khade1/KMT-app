package com.example.kmtapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextMobile, editTextEmail;
    private RadioGroup radioGroupToggle;
    private Button btnRegister, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize views
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextEmail = findViewById(R.id.editTextEmail);
        radioGroupToggle = findViewById(R.id.radioGroupToggle);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

        // Toggle between Mobile and Email registration
        radioGroupToggle.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonMobile) {
                editTextMobile.setVisibility(View.VISIBLE);
                editTextEmail.setVisibility(View.GONE);
            } else {
                editTextMobile.setVisibility(View.GONE);
                editTextEmail.setVisibility(View.VISIBLE);
            }
        });

        // Register button action
        btnRegister.setOnClickListener(v -> {
            if (radioGroupToggle.getCheckedRadioButtonId() == R.id.radioButtonMobile) {
                String mobile = editTextMobile.getText().toString().trim();
                if (mobile.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Please enter your mobile number", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed with mobile registration (e.g., OTP)
                }
            } else {
                String email = editTextEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Please enter your email address", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed with email registration
                }
            }
        });

        // Login button action
        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        });
    }
}
