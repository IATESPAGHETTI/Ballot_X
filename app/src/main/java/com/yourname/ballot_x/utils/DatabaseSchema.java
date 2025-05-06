package com.yourname.ballot_x.utils;

public class DatabaseSchema {

    public static final String TABLE_USERS = "users";

    public static final String USERS_COLUMN_ID = "id";
    public static final String USERS_COLUMN_USER_ID = "user_id"; // Firebase UID
    public static final String USERS_COLUMN_NAME = "name";
    public static final String USERS_COLUMN_EMAIL = "email";
    public static final String USERS_COLUMN_ROLE = "role";

    public static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    USERS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USERS_COLUMN_USER_ID + " TEXT NOT NULL UNIQUE, " +
                    USERS_COLUMN_NAME + " TEXT NOT NULL, " +
                    USERS_COLUMN_EMAIL + " TEXT NOT NULL, " +
                    USERS_COLUMN_ROLE + " TEXT NOT NULL" +
                    ")";
}
