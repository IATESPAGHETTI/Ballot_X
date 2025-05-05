package com.yourname.ballot_x.utils;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthHelper {

    private FirebaseAuth mAuth;
    private Context context;

    // Constructor to initialize FirebaseAuth and Context
    public AuthHelper(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
    }

    // Interface for callback when login/register is successful or fails
    public interface AuthListener {
        void onSuccess(FirebaseUser user);
        void onFailure(String errorMessage);
    }

    // Method to log in the user
    public void loginUser(String email, String password, AuthListener listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        listener.onSuccess(user);
                    } else {
                        String error = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        listener.onFailure(error);
                    }
                });
    }

    // Method to register a new user
    public void registerUser(String email, String password, AuthListener listener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        listener.onSuccess(user);
                    } else {
                        String error = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        listener.onFailure(error);
                    }
                });
    }

    // Sign out method
    public void signOut() {
        mAuth.signOut();
    }
}
