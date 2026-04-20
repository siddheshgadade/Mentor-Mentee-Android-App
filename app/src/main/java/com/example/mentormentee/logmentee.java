package com.example.mentormentee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class logmentee extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button bntee;
    ProgressBar progressBar;
    DatabaseReference databaseReference;

    EditText Emailmtee, Passwordmtee, UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logmentee);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        Emailmtee = findViewById(R.id.emai);
        Passwordmtee = findViewById(R.id.pass);
        bntee = findViewById(R.id.Log_btn);
        UserName = findViewById(R.id.Username);

        databaseReference = FirebaseDatabase.getInstance().getReference("Mentee");

        bntee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = Emailmtee.getText().toString();
                String password = Passwordmtee.getText().toString();

                if (email.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(logmentee.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(logmentee.this, "Please enter your password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(logmentee.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        // User is authenticated, now validate username and password
                                        String username = UserName.getText().toString();
                                        validateUsernameAndPassword(username, password);
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                    Toast.makeText(logmentee.this, "Authentication failed: " + errorCode, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void validateUsernameAndPassword(String username, String password) {
        databaseReference.orderByChild("USERName").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        if (user != null && user.getPassword().equals(password)) {
                            // Valid user login
                            Toast.makeText(logmentee.this, "Welcome Mentee.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(logmentee.this, Profile_Mentee.class);
                            intent.putExtra("Email", user.getEmail());
                            intent.putExtra("Password", user.getPassword());
                            intent.putExtra("USERName", user.getUSERName());
                            intent.putExtra("PhoneNo", user.getPhoneNo());
                            startActivity(intent);
                            finish();
                        } else {
                            // Invalid password
                            Toast.makeText(logmentee.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // Invalid username (mentor username)
                    Toast.makeText(logmentee.this, "Invalid mentor username.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }
    }
