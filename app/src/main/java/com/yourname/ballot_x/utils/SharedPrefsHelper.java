package com.yourname.ballot_x.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsHelper {

    private static final String PREF_NAME = "BallotPrefs";
    private static final String KEY_ROLE = "role";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SharedPrefsHelper(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    // Save user session
    public void saveUserSession(String role, String userId, String userName) {
        editor.putString(KEY_ROLE, role);
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, userName);
        editor.apply();
    }

    public String getUserRole() {
        return prefs.getString(KEY_ROLE, "");
    }

    public String getUserId() {
        return prefs.getString(KEY_USER_ID, "");
    }

    public String getUserName() {
        return prefs.getString(KEY_USER_NAME, "");
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
