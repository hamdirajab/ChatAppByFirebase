package com.app4.hamdi.chat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    String userId = null;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        final DatabaseReference dbRef = database.getReference();


        final EditText usernameTv = findViewById(R.id.editText5);
        final EditText emailTv = findViewById(R.id.editText4);
        final EditText passwordTv = findViewById(R.id.editText2);

        Button signupBtn = findViewById(R.id.button);
        Button cancelBtn = findViewById(R.id.button2);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!emailTv.getText().toString().equals("") && !passwordTv.getText().toString().equals("")){

                    signup(emailTv.getText().toString() , passwordTv.getText().toString());

                    if (flag == true){

                        User user = new User();
                        user.setId(userId);
                        user.setUsername(usernameTv.getText().toString());
                        user.setEmail(emailTv.getText().toString());
                        dbRef.child("users").push().setValue(user);

                        Intent intent = new Intent(getApplicationContext() , DisplayAndSelectTypeActivity.class);
                        startActivity(intent);
                    }

                }
            }
        });
    }
    private void signup(String email , String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            flag = updateUI(user);

                        } else {

                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean updateUI(FirebaseUser user){
        userId = user.getUid();
        return true;
    }

}
