package com.yourname.ballot_x.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "ballotx.db";
    public static final int DB_VERSION = 2;

    public static final String TABLE_EVENTS = "events";
    public static final String TABLE_PARTICIPATIONS = "participations";
    public static final String TABLE_VOTES = "votes";
    public static final String TABLE_USERS = "users";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");

        String createUsersTable = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
                "id TEXT PRIMARY KEY, " +
                "username TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "role TEXT NOT NULL CHECK(role IN ('admin', 'candidate', 'voter'))" +
                ")";
        db.execSQL(createUsersTable);

        String createEventsTable = "CREATE TABLE IF NOT EXISTS " + TABLE_EVENTS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "description TEXT" +
                ")";
        db.execSQL(createEventsTable);

        String createParticipationsTable = "CREATE TABLE IF NOT EXISTS " + TABLE_PARTICIPATIONS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "event_id INTEGER NOT NULL, " +
                "user_id TEXT NOT NULL, " +
                "role TEXT NOT NULL, " +
                "UNIQUE(event_id, user_id), " +
                "FOREIGN KEY(event_id) REFERENCES " + TABLE_EVENTS + "(id) ON DELETE CASCADE, " +
                "FOREIGN KEY(user_id) REFERENCES " + TABLE_USERS + "(id) ON DELETE CASCADE" +
                ")";
        db.execSQL(createParticipationsTable);

        String createVotesTable = "CREATE TABLE IF NOT EXISTS " + TABLE_VOTES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "event_id INTEGER NOT NULL, " +
                "candidate_id TEXT NOT NULL, " +
                "voter_id TEXT NOT NULL, " +
                "FOREIGN KEY(event_id) REFERENCES " + TABLE_EVENTS + "(id) ON DELETE CASCADE, " +
                "FOREIGN KEY(candidate_id) REFERENCES " + TABLE_USERS + "(id) ON DELETE CASCADE, " +
                "FOREIGN KEY(voter_id) REFERENCES " + TABLE_USERS + "(id) ON DELETE CASCADE, " +
                "UNIQUE(event_id, voter_id)" +
                ")";
        db.execSQL(createVotesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTICIPATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // ✅ Utility function: Automatically register user as a voter in a given event
    public boolean ensureUserIsRegisteredAsVoter(String userId, int eventId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT id FROM " + TABLE_PARTICIPATIONS +
                " WHERE user_id = ? AND event_id = ? AND role = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userId, String.valueOf(eventId), "voter"});

        boolean isRegistered = cursor.moveToFirst();
        cursor.close();

        if (!isRegistered) {
            ContentValues values = new ContentValues();
            values.put("user_id", userId);
            values.put("event_id", eventId);
            values.put("role", "voter");

            long result = db.insert(TABLE_PARTICIPATIONS, null, values);
            return result != -1;
        }

        return true;
    }
}
