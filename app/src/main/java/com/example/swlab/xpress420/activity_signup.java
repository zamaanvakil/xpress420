package com.example.swlab.xpress420;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activity_signup extends AppCompatActivity {

    private EditText email_et;
    private EditText password_et;
    private Button signup_btn;
    private TextView signin_tv;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        email_et = (EditText) findViewById(R.id.email);
        password_et = (EditText) findViewById(R.id.password);
        signup_btn = (Button) findViewById(R.id.sign_up_btn);
        signin_tv = (TextView) findViewById(R.id.signin_tv);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        signin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(activity_signup.this, LoginActivity.class);
                startActivity(LoginIntent);
            }
        });
    }

    private void registerUser() {
        String email = email_et.getText().toString().trim();
        String password = password_et.getText().toString().trim();

        //FIX EMPTY EditText BUG

        if ((email.length() == 0) || (password.length() == 0)) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Registering, please wait...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        //logged in successfully
                        Toast.makeText(getBaseContext(), "Registered successfully", Toast.LENGTH_LONG);
                        Intent signupIntent = new Intent(activity_signup.this, MainActivity.class);
                        startActivity(signupIntent);
                    } else {
                        Toast.makeText(getBaseContext(), "Could not register. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }

    }
}
