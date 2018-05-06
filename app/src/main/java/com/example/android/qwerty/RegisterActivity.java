package com.example.android.qwerty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private Button mRegisterButton;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mName = findViewById(R.id.nameField);
        mEmail = findViewById(R.id.emailField);
        mPassword = findViewById(R.id.passwordField);
        mRegisterButton = findViewById(R.id.registerBtn);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("users");
        
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });
    }

    private void startRegister() {
        final String name = mName.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"empty fields",Toast.LENGTH_LONG).show();
        }
        else{

            progressDialog.setMessage("registering...");
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {


                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = mDatabaseUsers.child(user_id);

                        current_user_db.child("name").setValue(name);
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,"registered",Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,"registration failed",Toast.LENGTH_LONG).show();

                    }
                }
            });

        }

    }
}
