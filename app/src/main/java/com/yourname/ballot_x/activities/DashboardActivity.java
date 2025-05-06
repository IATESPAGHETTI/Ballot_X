package com.yourname.ballot_x.activities;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yourname.ballot_x.R;
import com.yourname.ballot_x.utils.DatabaseHelper;

import java.util.ArrayList;

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

        btnProfile.setText("Admin Profile");
        btnElections.setText("Manage Elections");
        btnResults.setText("View All Results");

        btnProfile.setOnClickListener(v -> showAdminProfile());

        btnElections.setOnClickListener(v -> showManageElectionsDialog());

        btnResults.setOnClickListener(v -> showAllResultsDialog());
    }

    private void showAdminProfile() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Admin Profile");
        builder.setMessage("Admin features enabled.\n(More profile data can be shown here.)");
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    private void showManageElectionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_create_event, null);

        EditText titleInput = view.findViewById(R.id.eventTitle);
        EditText descInput = view.findViewById(R.id.eventDescription);
        Button createBtn = view.findViewById(R.id.btnCreateEvent);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        createBtn.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String desc = descInput.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(this, "Title required", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseHelper db = new DatabaseHelper(this);
            db.getWritableDatabase().execSQL(
                    "INSERT INTO " + DatabaseHelper.TABLE_EVENTS + " (title, description) VALUES (?, ?)",
                    new Object[]{title, desc});
            Toast.makeText(this, "Event Created", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }

    private void showAllResultsDialog() {
        DatabaseHelper db = new DatabaseHelper(this);
        SQLiteDatabase rdb = db.getReadableDatabase();

        Cursor eventCursor = rdb.rawQuery("SELECT id, title FROM " + DatabaseHelper.TABLE_EVENTS, null);
        ArrayList<String> eventTitles = new ArrayList<>();
        ArrayList<Integer> eventIds = new ArrayList<>();

        while (eventCursor.moveToNext()) {
            eventIds.add(eventCursor.getInt(0));
            eventTitles.add(eventCursor.getString(1));
        }
        eventCursor.close();

        if (eventTitles.isEmpty()) {
            Toast.makeText(this, "No events found.", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Choose Event")
                .setItems(eventTitles.toArray(new String[0]), (dialog, index) -> {
                    int eventId = eventIds.get(index);
                    Cursor voteCursor = rdb.rawQuery("SELECT candidate_id, COUNT(*) AS vote_count FROM " +
                                    DatabaseHelper.TABLE_VOTES + " WHERE event_id = ? GROUP BY candidate_id",
                            new String[]{String.valueOf(eventId)});
                    StringBuilder result = new StringBuilder();
                    while (voteCursor.moveToNext()) {
                        result.append("Candidate: ").append(voteCursor.getString(0))
                                .append(" - Votes: ").append(voteCursor.getInt(1)).append("\n");
                    }
                    voteCursor.close();

                    new AlertDialog.Builder(this)
                            .setTitle("Results")
                            .setMessage(result.toString().isEmpty() ? "No votes yet." : result.toString())
                            .setPositiveButton("OK", null)
                            .show();
                })
                .show();
    }
}
