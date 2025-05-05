package com.yourname.ballot_x.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yourname.ballot_x.R;

public class DashboardActivity extends AppCompatActivity {
    private Button btnProfile, btnElections, btnResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TextView tvDashboard = findViewById(R.id.tvDashboard);
        tvDashboard.setText("Admin Dashboard");

        btnProfile = findViewById(R.id.btnProfile);
        btnElections = findViewById(R.id.btnElections);
        btnResults = findViewById(R.id.btnResults);

        // Rename buttons for Admin functionality
        btnProfile.setText("Admin Profile");
        btnElections.setText("Manage Elections");
        btnResults.setText("View All Results");

        btnProfile.setOnClickListener(v -> {
            // Show admin profile in dialog or fragment
            showAdminProfile();
        });

        btnElections.setOnClickListener(v -> {
            // Use the same activity but pass extra to indicate admin mode
            showManageElectionsDialog();
        });

        btnResults.setOnClickListener(v -> {
            // Show results in a dialog
            showAllResultsDialog();
        });
    }

    private void showAdminProfile() {
        // Use AlertDialog to show profile info
        // Get user data from FirebaseFirestore
    }

    private void showManageElectionsDialog() {
        // Show dialog to create/manage elections
        // Use FirebaseFirestore to store event data
    }

    private void showAllResultsDialog() {
        // Show dialog with results from all events
        // Query FirebaseFirestore for results
    }
}
