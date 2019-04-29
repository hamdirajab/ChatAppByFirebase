package com.app4.hamdi.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MyRecycleAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private FirebaseAuth mAuth;

    ArrayList<ChatMessages> chatMessagesArrayList;
    int itemLayoutId;


    public MyRecycleAdapter(final ArrayList<ChatMessages> chatMessagesArrayList , int itemLayoutId){

        mAuth = FirebaseAuth.getInstance();
        this.chatMessagesArrayList = chatMessagesArrayList;
        this.itemLayoutId = itemLayoutId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId , parent , false);
        MyViewHolder vh = new MyViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ChatMessages chatMessages = chatMessagesArrayList.get(position);

        holder.massgeTv.setText(chatMessages.getMessage().toString());

        FirebaseUser user = mAuth.getCurrentUser();
        if (!chatMessages.getFrom().equals(user.getEmail())){
            holder.shapTv.setText("From");
            holder.shapTv.setTextSize(9);
            holder.shapTv.setBackgroundResource(R.drawable.chat_item2);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessagesArrayList.size();
    }
}
