package com.example.mentormentee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Schedule_Meeting extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_meeting);
        toolbar = findViewById(R.id.toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        Button openTeamsButton = findViewById(R.id.openTeamsButton);
        openTeamsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teamsWebsiteUri = "https://teams.microsoft.com/";
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(teamsWebsiteUri));
                startActivity(webIntent);
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
}