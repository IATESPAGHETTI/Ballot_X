package com.yourname.ballot_x.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Based on user role, redirect (pseudo-code)
        String role = "voter"; // Retrieve from login/session

        switch (role) {
            case "admin":
                startActivity(new Intent(this, AdminPanelActivity.class));
                break;
            case "candidate":
                startActivity(new Intent(this, CandidateProfileActivity.class));
                break;
            case "voter":
                startActivity(new Intent(this, VoterProfileActivity.class));
                break;
        }

        finish();
    }
}
