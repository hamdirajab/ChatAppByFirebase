package com.app4.hamdi.chat;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class displayFragment extends Fragment {

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    public displayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        final DatabaseReference dbRef = database.getReference();

        final ArrayList<String> idArrayList = new ArrayList<>();
        final ArrayList<String> idGroupArrayList = new ArrayList<>();
        dbRef.child("chats").child(user.getUid()+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                idArrayList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String id = snapshot.getKey();

                    if (id.length() > 30){
//                        idGroupArrayList.add(id);
                    }else{

                        idArrayList.add(id);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final ArrayList<User> userArrayList = new ArrayList<>();

        final ListView listView = getView().findViewById(R.id.listView);
        final MyAdapter myAdapter = new MyAdapter(getContext() , userArrayList ,R.layout.select_chat_user);

        dbRef.child("users").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userArrayList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    User user1 = snapshot.getValue(User.class);

                    if (!user1.getId().equals(user.getUid()) && idArrayList.contains(user1.getId())){

                        userArrayList.add(user1);
                        myAdapter.notifyDataSetChanged();
                    }
                }
                if (idGroupArrayList.size() > 0){

                    for(int i = 0; i < idGroupArrayList.size() ; i++){
                        User user2 = new User();
                        user2.setId(idGroupArrayList.get(i));
                        user2.setEmail("");
                        user2.setUsername("Group");
                        userArrayList.add(user2);
                        myAdapter.notifyDataSetChanged();
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                User user3 = userArrayList.get(position);
                if (user3.getUsername().equals("Group")){
                    Intent intent = new Intent(getContext() , GroupChatActivity.class);
                    intent.putExtra("GroupId" , user3.getId());
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getContext() , TwoChatActivity.class);
                    intent.putExtra("idTo" , user3.getId());
                    intent.putExtra("emailTo" , user3.getEmail());
                    intent.putExtra("nameTo" , user3.getUsername());
                    startActivity(intent);
                }
            }
        });

//        final Button chatOneBtn = getView().findViewById(R.id.button5);
//        final Button chatGruopBtn = getView().findViewById(R.id.button7);
//
//        chatOneBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext() , SelectedUserActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        chatGruopBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext() , SelectedGroupActivity.class);
//                intent.putStringArrayListExtra("usersId",null);
//                startActivity(intent);
//            }
//        });

    }
}
