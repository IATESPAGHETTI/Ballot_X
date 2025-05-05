package com.yourname.ballot_x.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yourname.ballot_x.R;

public class CandidateProfileActivity extends AppCompatActivity {
    private Button btnProfile, btnElections, btnResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard); // Reuse dashboard layout

        TextView tvDashboard = findViewById(R.id.tvDashboard);
        tvDashboard.setText("Candidate Dashboard");

        btnProfile = findViewById(R.id.btnProfile);
        btnElections = findViewById(R.id.btnElections);
        btnResults = findViewById(R.id.btnResults);

        // Rename buttons for Candidate functionality
        btnProfile.setText("My Profile");
        btnElections.setText("Browse Events");
        btnResults.setText("View Results");

        btnProfile.setOnClickListener(v -> {
            showCandidateProfile();
        });

        btnElections.setOnClickListener(v -> {
            showEventsDialog();
        });

        btnResults.setOnClickListener(v -> {
            showResultsDialog();
        });
    }

    private void showCandidateProfile() {
        // Dialog with candidate info + applications status
    }

    private void showEventsDialog() {
        // Dialog to browse events and apply to them
    }

    private void showResultsDialog() {
        // Dialog with results for events candidate participated in
    }
}
