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

public class VoterProfileActivity extends AppCompatActivity {
    private Button btnProfile, btnElections, btnResults;
    private String voterId = "voter_123"; // Replace with actual logged-in user/session ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard); // Reuse common dashboard layout

        TextView tvDashboard = findViewById(R.id.tvDashboard);
        tvDashboard.setText("Voter Dashboard");

        btnProfile = findViewById(R.id.btnProfile);
        btnElections = findViewById(R.id.btnElections);
        btnResults = findViewById(R.id.btnResults);

        btnProfile.setText("My Profile");
        btnElections.setText("Vote Now");
        btnResults.setText("View Results");

        btnProfile.setOnClickListener(v -> showVoterProfile());
        btnElections.setOnClickListener(v -> showVotingDialog());
        btnResults.setOnClickListener(v -> showResultsDialog());
    }

    private void showVoterProfile() {
        new AlertDialog.Builder(this)
                .setTitle("Voter Profile")
                .setMessage("ID: " + voterId + "\n(More profile data can be added here.)")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showVotingDialog() {
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor eventCursor = db.getReadableDatabase().rawQuery(
                "SELECT id, title FROM " + DatabaseHelper.TABLE_EVENTS, null);

        if (eventCursor.getCount() == 0) {
            Toast.makeText(this, "No events found.", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] eventTitles = new String[eventCursor.getCount()];
        int[] eventIds = new int[eventCursor.getCount()];
        int i = 0;
        while (eventCursor.moveToNext()) {
            eventIds[i] = eventCursor.getInt(0);
            eventTitles[i] = eventCursor.getString(1);
            i++;
        }
        eventCursor.close();

        new AlertDialog.Builder(this)
                .setTitle("Select Event")
                .setItems(eventTitles, (dialog, which) -> {
                    int eventId = eventIds[which];
                    showCandidateList(eventId);
                }).show();
    }

    private void showCandidateList(int eventId) {
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor candidatesCursor = db.getReadableDatabase().rawQuery(
                "SELECT user_id FROM " + DatabaseHelper.TABLE_PARTICIPATIONS +
                        " WHERE event_id = ? AND role = 'candidate'",
                new String[]{String.valueOf(eventId)});

        if (candidatesCursor.getCount() == 0) {
            Toast.makeText(this, "No candidates available for this event.", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] candidateIds = new String[candidatesCursor.getCount()];
        int j = 0;
        while (candidatesCursor.moveToNext()) {
            candidateIds[j++] = candidatesCursor.getString(0);
        }
        candidatesCursor.close();

        new AlertDialog.Builder(this)
                .setTitle("Vote for Candidate")
                .setItems(candidateIds, (dialog, index) -> {
                    String candidateId = candidateIds[index];
                    registerVote(eventId, candidateId);
                })
                .show();
    }

    private void registerVote(int eventId, String candidateId) {
        DatabaseHelper db = new DatabaseHelper(this);
        try {
            // Check if voter is registered for the event
            Cursor cursor = db.getReadableDatabase().rawQuery(
                    "SELECT id FROM " + DatabaseHelper.TABLE_PARTICIPATIONS +
                            " WHERE event_id = ? AND user_id = ? AND role = 'voter'",
                    new String[]{String.valueOf(eventId), voterId});
            if (!cursor.moveToFirst()) {
                Toast.makeText(this, "You are not registered for this event.", Toast.LENGTH_SHORT).show();
                cursor.close();
                return;
            }
            cursor.close();

            // Register vote
            db.getWritableDatabase().execSQL(
                    "INSERT INTO " + DatabaseHelper.TABLE_VOTES + " (event_id, candidate_id, voter_id) VALUES (?, ?, ?)",
                    new Object[]{eventId, candidateId, voterId});
            Toast.makeText(this, "Vote recorded successfully.", Toast.LENGTH_SHORT).show();

        } catch (SQLiteConstraintException e) {
            Toast.makeText(this, "You have already voted in this event.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to record vote.", Toast.LENGTH_SHORT).show();
            Log.e("VoterProfileActivity", "Error recording vote", e);
        }
    }

    private void showResultsDialog() {
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor cursor = db.getReadableDatabase().rawQuery(
                "SELECT e.title, v.candidate_id, COUNT(*) AS votes " +
                        "FROM " + DatabaseHelper.TABLE_VOTES + " v " +
                        "JOIN " + DatabaseHelper.TABLE_EVENTS + " e ON e.id = v.event_id " +
                        "GROUP BY v.event_id, v.candidate_id", null);

        StringBuilder result = new StringBuilder();
        while (cursor.moveToNext()) {
            result.append("Event: ").append(cursor.getString(0)).append("\n")
                    .append("Candidate: ").append(cursor.getString(1)).append(" - Votes: ")
                    .append(cursor.getInt(2)).append("\n\n");
        }
        cursor.close();

        new AlertDialog.Builder(this)
                .setTitle("Election Results")
                .setMessage(result.length() > 0 ? result.toString() : "No results available yet.")
                .setPositiveButton("OK", null)
                .show();
    }
}
