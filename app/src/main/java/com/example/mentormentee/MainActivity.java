package com.example.mentormentee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private Button B1, B2, B3,B4;
    private ProgressBar progressBar;
    TextView Text;
    Animation topAnim,BotmAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        B1 = findViewById(R.id.mentee);
        B2 = findViewById(R.id.mentor);
        B3 = findViewById(R.id.regis);
        B4 = findViewById(R.id.regismentee);
        Text = findViewById(R.id.logmain);




        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show ProgressBar before starting the new activity
                progressBar.setVisibility(View.VISIBLE);

                // Delay the intent for a short duration to show the ProgressBar
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, logmentee.class);
                        startActivity(i);


                        // Hide ProgressBar after starting the new activity
                        progressBar.setVisibility(View.GONE);
                    }
                }, 500); // Set the delay time in milliseconds (adjust as needed)
            }
        });

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show ProgressBar before starting the new activity
                progressBar.setVisibility(View.VISIBLE);

                // Delay the intent for a short duration to show the ProgressBar
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this,
                                loginmentor.class);
                        startActivity(i);


                        // Hide ProgressBar after starting the new activity
                        progressBar.setVisibility(View.GONE);
                    }
                }, 500); // Set the delay time in milliseconds (adjust as needed)
            }
        });

        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show ProgressBar before starting the new activity
                progressBar.setVisibility(View.VISIBLE);

                // Delay the intent for a short duration to show the ProgressBar
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, Register.class);
                        startActivity(i);


                        // Hide ProgressBar after starting the new activity
                        progressBar.setVisibility(View.GONE);
                    }
                }, 500); // Set the delay time in milliseconds (adjust as needed)

            }
        });


        B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show ProgressBar before starting the new activity
                progressBar.setVisibility(View.VISIBLE);

                // Delay the intent for a short duration to show the ProgressBar
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, Register_Mentee.class);
                        startActivity(i);


                        // Hide ProgressBar after starting the new activity
                        progressBar.setVisibility(View.GONE);
                    }
                }, 500); // Set the delay time in milliseconds (adjust as needed)

            }
        });

    }
}
