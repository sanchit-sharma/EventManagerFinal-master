package com.example.android.qwerty;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RemoveEventActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseDevelopers;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_event);

        final String event_key = getIntent().getExtras().getString("eventId");


        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        final String user_id = mCurrentUser.getUid().toString();
        mDatabaseDevelopers = FirebaseDatabase.getInstance().getReference().child("developers");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("event").child(event_key);
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("event").child(event_key).child("userId");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists())
                    return;
                String uid = dataSnapshot.child("userId").getValue().toString();
                if(user_id.equals(uid)){
                    new AlertDialog.Builder(RemoveEventActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Remove Event")
                            .setMessage("Are you sure you want to Remove this event?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mDatabase.removeValue();
                                    Toast.makeText(RemoveEventActivity.this,"Event Succesfully Removed",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(RemoveEventActivity.this,MainActivity.class));
                                    finish();

                                }

                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(RemoveEventActivity.this,MainActivity.class));
                                    finish();
                                }
                            })
                            .show();


                }
                else {
                    Toast.makeText(RemoveEventActivity.this, "You cannot remove events created by other administrators", Toast.LENGTH_SHORT).show();
                    finish();}



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
