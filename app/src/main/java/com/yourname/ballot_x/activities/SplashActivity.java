package com.yourname.ballot_x.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.yourname.ballot_x.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Find the TextView for the app name or logo
        TextView tvAppName = findViewById(R.id.tvAppName);

        // Load the fade-in animation from the resources
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Apply the fade-in animation to the TextView
        tvAppName.startAnimation(fadeIn);

        // Set a delay to show the splash screen for 3 seconds before transitioning
        new Handler().postDelayed(() -> {
            // After the splash screen, open the LoginActivity
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();  // Close the splash screen so the user can't go back to it
        }, 2000); // 3000 milliseconds = 3 seconds
    }
}
