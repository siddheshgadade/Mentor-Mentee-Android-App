package com.example.mentormentee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// ...

public class loginmentor extends AppCompatActivity {
    Button B1;
    DatabaseReference databaseReference;

    EditText Emailmen, Passwordmen, Username;

    private ProgressBar progressBar;
    private FirebaseAuth mAuth; // Add this line for Firebase Authentication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginmentor);

        B1 = findViewById(R.id.Log_btn);
        progressBar = findViewById(R.id.progressBar);
        Emailmen = findViewById(R.id.Em);
        Passwordmen = findViewById(R.id.Pas);
        Username = findViewById(R.id.Username);

        databaseReference = FirebaseDatabase.getInstance().getReference("Mentor");
        mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Authentication

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = Emailmen.getText().toString();
                String password = Passwordmen.getText().toString();

                if (email.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(loginmentor.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(loginmentor.this, "Please enter your password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(loginmentor.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        // User is authenticated, now validate username and password
                                        String username = Username.getText().toString();
                                        validateUsernameAndPassword(username, password);
                                    }
                                } else {
                                    // If sign in fails, display a custom message to the user.
                                    String errorMessage = getErrorMessage(task.getException());
                                    Toast.makeText(loginmentor.this, errorMessage, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(loginmentor.this, "Welcome Mentor.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(loginmentor.this, Profile_Mentor.class);
                            intent.putExtra("Email", user.getEmail());
                            intent.putExtra("Password", user.getPassword());
                            intent.putExtra("USERName", user.getUSERName());
                            intent.putExtra("PhoneNo", user.getPhoneNo());
                            startActivity(intent);
                            finish();
                        } else {
                            // Invalid password
                            Toast.makeText(loginmentor.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // Invalid username (mentor username)
                    Toast.makeText(loginmentor.this, "Invalid mentor username.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }
    private String getErrorMessage(Exception exception) {
        String message = "Authentication failed. Please try again.";
        if (exception instanceof FirebaseAuthException) {
            String errorCode = ((FirebaseAuthException) exception).getErrorCode();
            switch (errorCode) {
                case "ERROR_INVALID_CUSTOM_TOKEN":
                    message = "Invalid custom token.";
                    break;
                case "ERROR_CUSTOM_TOKEN_MISMATCH":
                    message = "Custom token mismatch.";
                    break;
                case "ERROR_INVALID_CREDENTIAL":
                    message = "Invalid credential.";
                    break;
                // Add more cases for other error codes if needed
                default:
                    message = "Authentication failed: " + errorCode;
            }
        }
        return message;
    }

}
