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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeveloperLoginActivity extends AppCompatActivity {
    private EditText mEmail;
    private EditText mPassword;
    private Button mButton;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabaseDeveloper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_login);

        mEmail = findViewById(R.id.developerLoginId);
        mPassword = findViewById(R.id.developerLoginPassword);
        mButton = findViewById(R.id.developerLoginBtn);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        mDatabaseDeveloper = FirebaseDatabase.getInstance().getReference().child("developers");

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoggingIn();
            }
        });
    }

    private void startLoggingIn() {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password))
            Toast.makeText(getApplicationContext(),"Empty Fields",Toast.LENGTH_LONG).show();
        else{
            progressDialog.setMessage("Logging In...");
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        checkUserExist();


                    }
                    else{

                        Toast.makeText(DeveloperLoginActivity.this,"You are not a Verified Administrator",Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();


                }
            });
        }
    }

    private void checkUserExist() {
        final String user_id = mAuth.getCurrentUser().getUid();

        mDatabaseDeveloper.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id))
                {
                    Intent postIntent = new Intent(DeveloperLoginActivity.this,PostActivity.class);
                    postIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(postIntent);
                }
                else
                    Toast.makeText(DeveloperLoginActivity.this,"You are not a Verified Administrator",Toast.LENGTH_LONG).show();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
