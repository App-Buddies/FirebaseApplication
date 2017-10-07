package com.example.admin.firebaseapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText mail, pass;
    Button login, signin;
    String userEmail, passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            finish();
        }
        mail = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.login);
        signin = findViewById(R.id.signin);
        userEmail = mail.getText().toString().trim();
        passWord = pass.getText().toString().trim();

    }

    public void loginCall(View view) {
        loginNow(userEmail, passWord);
    }

    private void loginNow(String userName, String passWord) {
        if (!userName.isEmpty()&&!passWord.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(userName, passWord).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        updateUi();
                    } else {
                        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("error", e.toString());
                }
            });

        }
    }


    public void signinCall(View view) {
        signinNow(userEmail, passWord);

    }

    private void signinNow(String userName, String passWord) {
        if (!userName.isEmpty()&&!passWord.isEmpty()) {
            mAuth.signInWithEmailAndPassword(userName, passWord).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //finish();
                        updateUi();
                    } else {
                        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    private void updateUi() {
        startActivity(new Intent(this, ProfileActivity.class));
    }


}
