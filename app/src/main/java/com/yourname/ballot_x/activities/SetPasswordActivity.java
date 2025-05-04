package com.yourname.ballot_x.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.yourname.ballot_x.R;


public class SetPasswordActivity extends AppCompatActivity {

    private EditText etPassword, etConfirmPassword;
    private Button btnSetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);  // Make sure you create this layout

        // Initialize views
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSetPassword = findViewById(R.id.btnSetPassword);

        // Set button click listener
        btnSetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                if (password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(SetPasswordActivity.this, "Please fill both fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SetPasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle successful password set, like saving the password
                    Toast.makeText(SetPasswordActivity.this, "Password set successfully", Toast.LENGTH_SHORT).show();
                    finish();  // Optionally, finish the activity to return to previous screen
                }
            }
        });
    }
}
