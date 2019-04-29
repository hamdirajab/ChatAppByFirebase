package com.app4.hamdi.chat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class TwoChatActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_chat);

        final String idTo = getIntent().getStringExtra("idTo");
        final String emailTo = getIntent().getStringExtra("emailTo");
        final String nameTo = getIntent().getStringExtra("nameTo");

        final TextView nameTv = findViewById(R.id.textView4);
        final TextView emailTv = findViewById(R.id.textView6);

        nameTv.setText(nameTo);
        emailTv.setText(emailTo);

        final Button sendBtn = findViewById(R.id.button4);
        final EditText sendET = findViewById(R.id.editText3);

        // Firebase
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        final DatabaseReference dbRef = database.getReference();

        // List
        final ArrayList<ChatMessages> chatMessagesArrayList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        final MyRecycleAdapter recycleAdapter = new MyRecycleAdapter(chatMessagesArrayList ,R.layout.chat_item_desgin);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //

        dbRef.child("chats").child(user.getUid()+"").child(idTo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                chatMessagesArrayList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ChatMessages chatMessages = snapshot.getValue(ChatMessages.class);
                    chatMessagesArrayList.add(chatMessages);
                    recycleAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sendET.getText().toString().equals("")){

                    ChatMessages chatMessages = new ChatMessages();
                    chatMessages.setFrom(user.getEmail().toString());
                    chatMessages.setTo(emailTo);
                    chatMessages.setMessage(sendET.getText().toString());

                    dbRef.child("chats").child(user.getUid()+"").child(idTo).push().setValue(chatMessages);
                    dbRef.child("chats").child(idTo).child(user.getUid()+"").push().setValue(chatMessages);

                }
            }
        });
    }
}
