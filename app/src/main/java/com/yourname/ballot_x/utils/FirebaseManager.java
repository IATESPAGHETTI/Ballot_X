package com.yourname.ballot_x.utils;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseManager {
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Listener interface for save operation
    public interface SaveListener {
        void onSaved();
        void onFailure(String error);
    }

    // Listener interface for retrieving user info
    public interface RetrieveUserListener {
        void onSuccess(User user);
        void onFailure(String error);
    }

    // Save user info to Firestore
    public static void saveUserInfo(String uid, String name, String email, String role, SaveListener listener) {
        db.collection("users").document(uid)
                .set(new User(name, email, role))
                .addOnSuccessListener(aVoid -> listener.onSaved())
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }

    // Retrieve user info from Firestore
    public static void getUserInfo(String uid, RetrieveUserListener listener) {
        db.collection("users").document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc.exists()) {
                            User user = doc.toObject(User.class);
                            listener.onSuccess(user);
                        } else {
                            listener.onFailure("User data not found");
                        }
                    } else {
                        listener.onFailure(task.getException() != null ? task.getException().getMessage() : "Unknown error");
                    }
                });
    }

    // User model class
    public static class User {
        public String name;
        public String email;
        public String role;

        // Required empty constructor for Firestore
        public User() {}

        public User(String name, String email, String role) {
            this.name = name;
            this.email = email;
            this.role = role;
        }
    }
}
