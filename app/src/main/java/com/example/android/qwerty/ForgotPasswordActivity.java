package com.example.android.qwerty;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText mRecoveryEmail;
    private Button mButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mRecoveryEmail = findViewById(R.id.recoveryEmail);
        mButton = findViewById(R.id.passwordResetBtn);
        mAuth = FirebaseAuth.getInstance();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mRecoveryEmail.getText().toString();
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(ForgotPasswordActivity.this,"password reset link sent",Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                        }
                    }
                });
            }
        });
    }
}
