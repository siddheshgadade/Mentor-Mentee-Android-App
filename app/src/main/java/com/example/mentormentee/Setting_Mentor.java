package com.example.mentormentee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Setting_Mentor extends AppCompatActivity {
    Toolbar toolbar;
    TextView contactUsTextView;
    TextView aboutUsTextView;
    TextView termsandcondition;
    Button logOutButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_mentor);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Find views by ID
        contactUsTextView = findViewById(R.id.contactusmentor);
        aboutUsTextView = findViewById(R.id.AboutUsTextViewmentor);
        termsandcondition = findViewById(R.id.terms_and_conditionmentor);
        logOutButton = findViewById(R.id.logOutButton);

        // Set click listeners
        contactUsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle "Contact Us" click
                openContactUs();
            }
        });


        aboutUsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle "About Us" click
                openAboutUs();
            }
        });
        termsandcondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTermsAndConditions();
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle "Log Out" click
                logOut();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle the back button click
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Method to handle "Contact Us" click
    private void openContactUs() {
        // Handle the "Contact Us" action, e.g., open a contact form or email
        // Example: Open an email intent
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "siddheshgadade3@gmail.com", null));
        startActivity(Intent.createChooser(emailIntent, "Send Email"));
    }

    // Method to handle "App Description" click

    // Method to handle "About Us" click
    private void openAboutUs() {
        // Handle the "About Us" action
        // Example: Open a new activity or fragment to display "About Us" information
        startActivity(new Intent(this, About_Us.class));

    }
    private void openTermsAndConditions() {
        Intent intent = new Intent(Setting_Mentor.this, Terms_And_Condition.class);
        startActivity(intent);
    }


    // Method to handle "Log Out" click
    private void logOut() {
        FirebaseAuth.getInstance().signOut();

        Toast.makeText(Setting_Mentor.this, "LogOut Successfully", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(Setting_Mentor.this, MainActivity.class);
        startActivity(i);
        finish();
        // Handle the "Log Out" action, e.g., clear user session and return to the login screen
        // Example: Log the user out and start the LoginActivity
        // logoutUser();
        // startActivity(new Intent(this, LoginActivity.class));
    }
}
