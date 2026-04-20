package com.example.mentormentee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register_Mentee extends AppCompatActivity {
    private EditText USERMENTEE, Email, Password, editTextConfirmPassword, PhoneNo;
    private Button buttonRegister;

    // Firebase
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // com.example.mentormentee.Register_Mentee.User is already signed in, handle as needed
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_mentee);
        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Initialize UI Elements
        USERMENTEE = findViewById(R.id.username);
        Email = findViewById(R.id.Emailres);
        Password = findViewById(R.id.Passwordres);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        PhoneNo = findViewById(R.id.Phonen);


        // Register Button Click Listener
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String usermentee = USERMENTEE.getText().toString();
                String Phone = PhoneNo.getText().toString();

                if (email.isEmpty() || password.isEmpty() || usermentee.isEmpty() || Phone.isEmpty()) {
                    Toast.makeText(Register_Mentee.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidEmail(email)) {
                    Toast.makeText(Register_Mentee.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if phone number is exactly 10 digits
                if (Phone.length() != 10) {
                    Toast.makeText(Register_Mentee.this, "Phone number must be 10 digits", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the usermentor exists in the database
                databaseReference.child("Mentee").child(usermentee).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // com.example.mentormentee.Register_Mentee.User is already registered
                            Toast.makeText(Register_Mentee.this, "User Is Already Registered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register_Mentee.this, logmentee.class);
                            startActivity(intent);
                        } else {
                            // Store user data in the database
                            databaseReference.child("Mentee").child(usermentee).child("USERName").setValue(usermentee);
                            databaseReference.child("Mentee").child(usermentee).child("Email").setValue(email);
                            databaseReference.child("Mentee").child(usermentee).child("Password").setValue(password);
                            databaseReference.child("Mentee").child(usermentee).child("PhoneNo").setValue(Phone);

                            // Create user in Firebase Authentication
                            mAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // com.example.mentormentee.Register_Mentee.User registration successful
                                                Toast.makeText(Register_Mentee.this, "User Registration Successful!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Register_Mentee.this, logmentee.class);
                                                startActivity(intent);
                                            } else {
                                                // Authentication failed
                                                Toast.makeText(Register_Mentee.this, "Enter The Correct Password", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle the error
                    }
                });
            }
        });
    }
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

}