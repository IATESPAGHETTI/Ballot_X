package com.yourname.ballot_x.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.yourname.ballot_x.R;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin, btnSignUp;
    Spinner spinnerRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        spinnerRole = findViewById(R.id.spinnerRole);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        // Login button click listener
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            // Check role and navigate accordingly
            if (email.contains("admin")) {
                startActivity(new Intent(this, AdminPanelActivity.class));
            } else if (email.contains("candidate")) {
                startActivity(new Intent(this, CandidateProfileActivity.class));
            } else {
                startActivity(new Intent(this, VoterProfileActivity.class));
            }
        });

        // Sign Up button click listener
        btnSignUp.setOnClickListener(v -> {
            // Navigate to the Sign Up screen
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
