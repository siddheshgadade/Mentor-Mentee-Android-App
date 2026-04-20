package com.example.mentormentee;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class Profile_Mentor extends AppCompatActivity {
    SmoothBottomBar smoothBottomBar;
    TextView TitleName,TitleEmail,TitlePass,Titlephone ;
    androidx.appcompat.widget.Toolbar toolbar;

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_mentor);

        TitleName = findViewById(R.id.textViewUser);
        TitleEmail = findViewById(R.id.textViewEmail);
        Titlephone = findViewById(R.id.textViewUserPhone);
        TitlePass = findViewById(R.id.textViewUserPass);


        databaseReference = FirebaseDatabase.getInstance().getReference("Mentor");

        smoothBottomBar = findViewById(R.id.bottomBar);
        toolbar = findViewById(R.id.toolbar);
        showUserData();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(Profile_Mentor.this, "User is Here", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Profile_Mentor.this, "User Is Not there", Toast.LENGTH_SHORT).show();
        }
        // Set the selected item to "Setting"
        smoothBottomBar.setItemActiveIndex(0);

        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0:
                        // Alrady Selected Activity

                        break;
                    case 1:
                        startActivity(new Intent(Profile_Mentor.this, Mentor_Home.class));
                        break;
                    case 2:
                        startActivity(new Intent(Profile_Mentor.this, Setting_Mentor.class));
                        break;
                    case 3:
                        startActivity(new Intent(Profile_Mentor.this, More_Mentor.class));
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