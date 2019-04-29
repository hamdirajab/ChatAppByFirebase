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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupChatActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        final String groupIdTo = getIntent().getStringExtra("GroupId");
        final ArrayList<String> usersId = getIntent().getStringArrayListExtra("usersId");

        final TextView nameTv = findViewById(R.id.textView4);
        final TextView emailTv = findViewById(R.id.textView6);

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

        nameTv.setText("Group");
        emailTv.setText(user.getEmail().toString());

        dbRef.child("chats").child(user.getUid()+"").child("GroupUsersId"+groupIdTo).child(groupIdTo).addValueEventListener(new ValueEventListener() {
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


        if (usersId != null){

            for (String idTo : usersId){
                dbRef.child("chats").child(user.getUid()+"").child("GroupUsersId"+groupIdTo).push().setValue(idTo);
            }
            dbRef.child("chats").child(user.getUid()+"").child("GroupUsersId"+groupIdTo).push().setValue(user.getUid());
            for (int i = 0 ; i < usersId.size() ; i++){
                for (int k = 0 ; k < usersId.size() ; k++){
                    dbRef.child("chats").child(usersId.get(i)).child("GroupUsersId"+groupIdTo).push().setValue(usersId.get(k));
                }
                dbRef.child("chats").child(usersId.get(i)).child("GroupUsersId"+groupIdTo).push().setValue(user.getUid());
            }
        }

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sendET.getText().toString().equals("")){

                    ChatMessages chatMessages = new ChatMessages();
                    chatMessages.setFrom(user.getEmail().toString());
                    chatMessages.setTo("Group");
                    chatMessages.setMessage(sendET.getText().toString());

//                    if (usersId != null) {

                        dbRef.child("chats").child(user.getUid()+"").child("GroupUsersId"+groupIdTo).child(groupIdTo).push().setValue(chatMessages);
                        for (String idTo : usersId){
                            dbRef.child("chats").child(idTo).child("GroupUsersId"+groupIdTo).child(groupIdTo).push().setValue(chatMessages);
//                        }
//                    }else{
                /*        dbRef.child("chats").child(user.getUid()+"").child("GroupUsersId").addValueEventListener(new ValueEventListener() {
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
                        */
                    }
                }
            }
        });

    }
}
