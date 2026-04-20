package com.example.mentormentee;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.mentormentee.databinding.ActivityChatRoomBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import java.util.UUID;

public class ChatRoom extends AppCompatActivity {
    ActivityChatRoomBinding binding;
    MessageAdapter messageAdapter;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase Database reference
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("chats").child("global_chat");

        messageAdapter = new MessageAdapter(this);
        binding.recycler.setAdapter(messageAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.message.getText().toString().trim();
                if (!message.isEmpty()) {
                    sendMessage(message);
                }
            }
        });

        // Listen for new messages in the global chat room
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                if (messageModel != null) {
                    messageAdapter.add(messageModel);
                    scrollToBottom();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            // Implement other ChildEventListener methods as needed
        });
    }

    private void sendMessage(String message) {
        String messageId = UUID.randomUUID().toString();
        String senderId = FirebaseAuth.getInstance().getUid();
        MessageModel messageModel = new MessageModel(messageId, senderId, message);

        // Push the message to the global chat room
        databaseReference.push().setValue(messageModel);

        // Clear the message input field
        binding.message.setText("");
    }

    private void scrollToBottom() {
        binding.recycler.scrollToPosition(messageAdapter.getItemCount() - 1);
    }
}
