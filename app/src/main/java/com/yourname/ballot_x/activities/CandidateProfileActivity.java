package com.yourname.ballot_x.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.yourname.ballot_x.R;


public class CandidateProfileActivity extends AppCompatActivity {

    private TextView tvCandidateName;
    private TextView tvCandidateBio;
    private TextView tvCandidateAgenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile);

        // Initialize Views
        tvCandidateName = findViewById(R.id.tvCandidateName);
        tvCandidateBio = findViewById(R.id.tvCandidateBio);
        tvCandidateAgenda = findViewById(R.id.tvCandidateAgenda);

        // Sample data for candidate (this could be fetched from Firebase or a database)
        String candidateName = "John Doe"; // Replace with dynamic data
        String candidateBio = "I am John Doe, a candidate for the president position. I believe in a transparent and student-focused approach.";
        String candidateAgenda = "1. Improve campus facilities\n2. Increase student participation in university decisions\n3. Enhance campus security";

        // Set data to the views
        tvCandidateName.setText(candidateName);
        tvCandidateBio.setText(candidateBio);
        tvCandidateAgenda.setText(candidateAgenda);
    }
}
