package com.example.mentormentee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;



public class Splash extends AppCompatActivity {
    ImageView Im;
    TextView log;
    Animation topAnim,BotmAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Im = findViewById(R.id.logo);
        log = findViewById(R.id.Title);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        BotmAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        Im.setAnimation(topAnim);
        log.setAnimation(BotmAnim);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            } ,1000);
        }
}