package com.example.swlab.xpress420;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email_et;
    private EditText password_et;
    private Button signin_btn;
    private TextView signup_tv;
    private TextView forgot_password_tv;
    private Button signin_google_btn;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            Intent LoggedinIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(LoggedinIntent);
        }

        progressDialog = new ProgressDialog(this);

        email_et = (EditText) findViewById(R.id.email);
        password_et = (EditText) findViewById(R.id.password);
        signin_btn = (Button) findViewById(R.id.sign_in_btn);
        signup_tv = (TextView) findViewById(R.id.signup_tv);
        forgot_password_tv = (TextView) findViewById(R.id.forgot_password_tv);


        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });



        signup_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open signup activity
                Intent SignupIntent = new Intent(LoginActivity.this, activity_signup.class);
                startActivity(SignupIntent);
            }
        });

        forgot_password_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                intent.putExtra("email",email_et.getText().toString());
                startActivity(intent);
            }
        });

    }


    private void Login(){
        String email = email_et.getText().toString().trim();
        String password = password_et.getText().toString().trim();

        if(email.length() == 0 || password.length() == 0){
            Toast.makeText(this,"Please enter email and password",Toast.LENGTH_SHORT).show();
        }
        else{
            progressDialog.setMessage("Signing in, please wait...");

            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if(task.isSuccessful()){
                        //logged in successfully
                        finish();
                        Toast.makeText(LoginActivity.this,"Logged in successfully",Toast.LENGTH_LONG).show();
                        Intent LoginIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(LoginIntent);
                    }
                    else{
                        Toast.makeText(getBaseContext(),"Invalid email or password",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            progressDialog.show();
        }

    }

    @Override
    public void onClick(View view) {

    }
}
