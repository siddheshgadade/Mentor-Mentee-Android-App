package com.example.mentormentee;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Myviewholder> {
    private Context context;
    private List<MessageModel> MessageModellist;

    public MessageAdapter(Context context) {
        this.context = context;
        MessageModellist = new ArrayList<>();
    }

    public void add(MessageModel messageModel) {
        MessageModellist.add(messageModel);
        notifyDataSetChanged();
    }

    public void clear() {
        MessageModellist.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row, parent, false);
        return new Myviewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        MessageModel messageModel = MessageModellist.get(position);
        holder.msg.setText(messageModel.getMessage());

        // Check if the message was sent by the current user
        boolean isSentByCurrentUser = messageModel.getSenderId().equals(FirebaseAuth.getInstance().getUid());

        // Set the background color based on whether it's a sent or received message
        int backgroundColor = isSentByCurrentUser ? Color.parseColor("#0379FF") : Color.WHITE;
        holder.main.setBackgroundColor(backgroundColor);

        // Set the text color to white for all messages
        holder.msg.setTextColor(Color.BLACK);
    }


    @Override
    public int getItemCount() {
        return MessageModellist.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        private TextView msg;
        private LinearLayout main;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.message);
            main = itemView.findViewById(R.id.mainMessageLayout);
        }
    }
}
