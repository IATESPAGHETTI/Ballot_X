package com.yourname.ballot_x.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.yourname.ballot_x.R;


public class VoterProfileActivity extends AppCompatActivity {

    private TextView tvVoterName;
    private TextView tvVoterEmail;
    private TextView tvVoterStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_profile);

        // Initialize Views
        tvVoterName = findViewById(R.id.tvVoterName);
        tvVoterEmail = findViewById(R.id.tvVoterEmail);
        tvVoterStatus = findViewById(R.id.tvVoterStatus);

        // Sample data for voter (this could be fetched from Firebase or a database)
        String voterName = "Jane Smith"; // Replace with dynamic data
        String voterEmail = "janesmith@university.com"; // Replace with dynamic data
        boolean hasVoted = true; // Replace with dynamic data (true or false)

        // Set data to the views
        tvVoterName.setText(voterName);
        tvVoterEmail.setText(voterEmail);
        tvVoterStatus.setText(hasVoted ? "You have voted" : "You have not voted yet");
    }
}
