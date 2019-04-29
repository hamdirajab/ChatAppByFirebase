package com.app4.hamdi.chat;

import android.content.Intent;
import android.database.Cursor;
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

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        final EditText usernameET = findViewById(R.id.editText);
        final EditText passwordET = findViewById(R.id.editText2);

        Button signinBtn = findViewById(R.id.button2);
        Button signupBtn = findViewById(R.id.button);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!usernameET.getText().toString().equals("") && !passwordET.getText().toString().equals("")){

                    String nameET = usernameET.getText().toString();
                    String passwordeET =  passwordET.getText().toString();
                    signin(nameET , passwordeET);

                }

            }
        });


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext() , SignUpActivity.class);
                startActivity(intent);

            }
        });
    }

    private void signin(String email , String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user){

        Intent intent = new Intent(getApplicationContext() , DisplayAndSelectTypeActivity.class);
        startActivity(intent);

    }
}