package com.yourname.ballot_x.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yourname.ballot_x.R;

public class VoterProfileActivity extends AppCompatActivity {
    private Button btnProfile, btnElections, btnResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard); // Reuse dashboard layout

        TextView tvDashboard = findViewById(R.id.tvDashboard);
        tvDashboard.setText("Voter Dashboard");

        btnProfile = findViewById(R.id.btnProfile);
        btnElections = findViewById(R.id.btnElections);
        btnResults = findViewById(R.id.btnResults);

        // Rename buttons for Voter functionality
        btnProfile.setText("My Profile");
        btnElections.setText("Vote Now");
        btnResults.setText("View Results");

        btnProfile.setOnClickListener(v -> {
            showVoterProfile();
        });

        btnElections.setOnClickListener(v -> {
            showVotingDialog();
        });

        btnResults.setOnClickListener(v -> {
            showResultsDialog();
        });
    }

    private void showVoterProfile() {
        // Dialog with voter info + voting history
    }

    private void showVotingDialog() {
        // Dialog to browse active elections and vote
    }

    private void showResultsDialog() {
        // Dialog with election results
    }
}
