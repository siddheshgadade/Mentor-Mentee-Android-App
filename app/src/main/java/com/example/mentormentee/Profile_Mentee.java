package com.example.mentormentee;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class Profile_Mentee extends AppCompatActivity {
    SmoothBottomBar smoothBottomBar;
    TextView TitleName,TitleEmail,TitlePass,Titlephone ;
    androidx.appcompat.widget.Toolbar toolbar;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_mentee);
        TitleName = findViewById(R.id.textViewUserm);
        TitleEmail = findViewById(R.id.textViewEmailm);
        Titlephone = findViewById(R.id.textViewUserPhonem);
        TitlePass = findViewById(R.id.textViewUserPassm);

        smoothBottomBar = findViewById(R.id.bottomBar);


        databaseReference = FirebaseDatabase.getInstance().getReference("Mentee");

        toolbar = findViewById(R.id.toolbar);
        showUserData();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(Profile_Mentee.this, "User is Here", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Profile_Mentee.this, "User Is Not there", Toast.LENGTH_SHORT).show();
        }
        smoothBottomBar.setItemActiveIndex(0);

        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0:
                        // Alrady Selected Activity

                        break;
                    case 1:
                        startActivity(new Intent(Profile_Mentee.this, Mentee_Home.class));
                        break;
                    case 2:
                        startActivity(new Intent(Profile_Mentee.this, Settings_Mentee.class));
                        break;
                    case 3:
                        startActivity(new Intent(Profile_Mentee.this, More_Mentee.class));
                        break;
                }
                return true;
            }
        });
    }

    public void showUserData(){
        Intent intent = getIntent();

        String newUser = intent.getStringExtra("USERName");
        String emailUser = intent.getStringExtra("Email");
        String passUser = intent.getStringExtra("Password");
        String phoneUser = intent.getStringExtra("PhoneNo");

        TitleName.setText("Name: " + newUser);
        TitlePass.setText("Password: " + passUser);
        Titlephone.setText("Phone: " + phoneUser);
        TitleEmail.setText("Email: " + emailUser);
    }
}