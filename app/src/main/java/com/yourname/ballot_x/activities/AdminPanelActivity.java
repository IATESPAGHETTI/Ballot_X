package com.yourname.ballot_x.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.yourname.ballot_x.R;


public class AdminPanelActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnManageElections;
    private Button btnViewResults;
    private Button btnManageCandidates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnManageElections = findViewById(R.id.btnManageElections);
        btnViewResults = findViewById(R.id.btnViewResults);
        btnManageCandidates = findViewById(R.id.btnManageCandidates);

        // Display admin's name or role
        tvWelcome.setText("Welcome, Admin!");

        // Handle clicks for button actions
        btnManageElections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to manage elections activity
                // Intent intent = new Intent(AdminPanelActivity.this, ManageElectionsActivity.class);
                // startActivity(intent);
            }
        });

        btnViewResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to view results activity
                // Intent intent = new Intent(AdminPanelActivity.this, ViewResultsActivity.class);
                // startActivity(intent);
            }
        });

        btnManageCandidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to manage candidates activity
                // Intent intent = new Intent(AdminPanelActivity.this, ManageCandidatesActivity.class);
                // startActivity(intent);
            }
        });
    }
}
