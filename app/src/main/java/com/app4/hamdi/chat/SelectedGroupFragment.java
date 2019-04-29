package com.app4.hamdi.chat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SelectedGroupFragment extends Fragment {

    private FirebaseDatabase database;
    private FirebaseAuth auth;

    public SelectedGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected_group, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final SearchView emailSH = getView().findViewById(R.id.searchView);

        final TextView nameTv = getView().findViewById(R.id.textView13);
        final TextView emailTv = getView().findViewById(R.id.textView15);
        final TextView userIdTv = getView().findViewById(R.id.textView8);

        final Button chatBtn = getView().findViewById(R.id.button3);
        final Button addBtn = getView().findViewById(R.id.button6);
        final Button cancelBtn = getView().findViewById(R.id.button8);

        database = FirebaseDatabase.getInstance();
        final DatabaseReference dbRef = database.getReference();

        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();

        final ArrayList<String> userid = new ArrayList<>();

        emailSH.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Query fireQuery = dbRef.child("users").orderByChild("email").equalTo(query);

                fireQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.getValue() == null){

                            Toast.makeText(getContext(), "Not Found", Toast.LENGTH_SHORT).show();

                            nameTv.setText("");
                            emailTv.setText("");

                        }else{

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                                User user = snapshot.getValue(User.class);

                                userIdTv.setText(user.getId());
                                nameTv.setText(user.getUsername());
                                emailTv.setText(user.getEmail());

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        emailSH.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                nameTv.setText("");
                emailTv.setText("");
                return false;
            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!userIdTv.getText().toString().equals("") && !emailTv.getText().toString().equals("") && !nameTv.getText().toString().equals("")){

                    userid.add(userIdTv.getText().toString());
                    Toast.makeText(getContext(), "Added Succsesfuly" , Toast.LENGTH_SHORT).show();
                }
            }
        });


        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idToAll = null;
                for (String idTo : userid) idToAll += idTo;

                if (!idToAll.equals(null)){

                    if (!emailTv.getText().equals("")){
                        Intent intent = new Intent(getContext() , GroupChatActivity.class);
                        intent.putExtra("GroupId" , user.getUid()+""+idToAll);
                        intent.putStringArrayListExtra("usersId" , userid);
                        startActivity(intent);
                    }
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

}
