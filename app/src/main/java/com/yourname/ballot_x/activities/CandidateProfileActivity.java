package com.yourname.ballot_x.activities;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yourname.ballot_x.R;
import com.yourname.ballot_x.utils.DatabaseHelper;

public class CandidateProfileActivity extends AppCompatActivity {
    private Button btnProfile, btnElections, btnResults;
    private String candidateId = "candidate_123"; // Replace this with real session ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard); // Reuse dashboard layout

        TextView tvDashboard = findViewById(R.id.tvDashboard);
        tvDashboard.setText("Candidate Dashboard");

        btnProfile = findViewById(R.id.btnProfile);
        btnElections = findViewById(R.id.btnElections);
        btnResults = findViewById(R.id.btnResults);

        btnProfile.setText("My Profile");
        btnElections.setText("Browse Events");
        btnResults.setText("View Results");

        btnProfile.setOnClickListener(v -> showCandidateProfile());
        btnElections.setOnClickListener(v -> showEventsDialog());
        btnResults.setOnClickListener(v -> showResultsDialog());
    }

    private void showCandidateProfile() {
        new AlertDialog.Builder(this)
                .setTitle("Candidate Profile")
                .setMessage("ID: " + candidateId + "\n(More profile data can go here.)")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showEventsDialog() {
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT id, title FROM " + DatabaseHelper.TABLE_EVENTS, null);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No events available", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }

        String[] events = new String[cursor.getCount()];
        int[] eventIds = new int[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            eventIds[i] = cursor.getInt(0);
            events[i] = cursor.getString(1);
            i++;
        }
        cursor.close();

        new AlertDialog.Builder(this)
                .setTitle("Available Events")
                .setItems(events, (dialog, which) -> {
                    int eventId = eventIds[which];
                    applyForEvent(eventId);
                })
                .show();
    }

    private void applyForEvent(int eventId) {
        DatabaseHelper db = new DatabaseHelper(this);
        try {
            // Check if the event exists
            Cursor cursor = db.getReadableDatabase().rawQuery(
                    "SELECT id FROM " + DatabaseHelper.TABLE_EVENTS + " WHERE id = ?",
                    new String[]{String.valueOf(eventId)}
            );

            if (cursor.getCount() == 0) {
                Toast.makeText(this, "Event not found", Toast.LENGTH_SHORT).show();
                cursor.close();
                return;
            }
            cursor.close();

            // Insert participation
            db.getWritableDatabase().execSQL(
                    "INSERT INTO " + DatabaseHelper.TABLE_PARTICIPATIONS + " (event_id, user_id, role) VALUES (?, ?, ?)",
                    new Object[]{eventId, candidateId, "candidate"}
            );

            Toast.makeText(this, "Applied to event", Toast.LENGTH_SHORT).show();

        } catch (SQLiteConstraintException e) {
            Toast.makeText(this, "Already applied to this event.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error applying for event", Toast.LENGTH_SHORT).show();
            Log.e("CandidateProfile", "Error applying for event", e);
        }
    }

    private void showResultsDialog() {
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor cursor = db.getReadableDatabase().rawQuery(
                "SELECT e.title, COUNT(v.id) AS vote_count " +
                        "FROM " + DatabaseHelper.TABLE_EVENTS + " e " +
                        "LEFT JOIN " + DatabaseHelper.TABLE_VOTES + " v " +
                        "ON e.id = v.event_id AND v.candidate_id = ? " +
                        "GROUP BY e.id", new String[]{candidateId});

        StringBuilder result = new StringBuilder();
        while (cursor.moveToNext()) {
            result.append(cursor.getString(0))
                    .append(": ")
                    .append(cursor.getInt(1))
                    .append(" votes\n");
        }
        cursor.close();

        new AlertDialog.Builder(this)
                .setTitle("Your Results")
                .setMessage(result.length() > 0 ? result.toString() : "No participation or votes yet.")
                .setPositiveButton("OK", null)
                .show();
    }
}
