package com.yourname.ballot_x.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yourname.ballot_x.R;
import com.yourname.ballot_x.utils.FirebaseManager;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button loginButton;
    private TextView signupText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupText = findViewById(R.id.signupText);

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(v -> {
            String email = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            loginButton.setEnabled(false);

            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user == null) {
                                Toast.makeText(LoginActivity.this, "User not found after login", Toast.LENGTH_SHORT).show();
                                loginButton.setEnabled(true);
                                return;
                            }
                            // Fetch user role from Firestore
                            FirebaseManager.getUserInfo(user.getUid(), new FirebaseManager.RetrieveUserListener() {
                                @Override
                                public void onSuccess(FirebaseManager.User userData) {
                                    redirectBasedOnRole(userData.role);
                                    loginButton.setEnabled(true);
                                }

                                @Override
                                public void onFailure(String error) {
                                    Toast.makeText(LoginActivity.this, "Failed to get user info: " + error, Toast.LENGTH_SHORT).show();
                                    loginButton.setEnabled(true);
                                }
                            });
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            loginButton.setEnabled(true);
                        }
                    });
        });

        signupText.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void redirectBasedOnRole(String role) {
        Intent intent;
        switch (role) {
            case "Admin":
                intent = new Intent(this, DashboardActivity.class);
                break;
            case "Voter":
                intent = new Intent(this, VoterProfileActivity.class);
                break;
            case "Candidate":
                intent = new Intent(this, CandidateProfileActivity.class);
                break;
            default:
                Toast.makeText(this, "Unknown role: " + role, Toast.LENGTH_SHORT).show();
                return;
        }
        startActivity(intent);
        finish();
    }
}
