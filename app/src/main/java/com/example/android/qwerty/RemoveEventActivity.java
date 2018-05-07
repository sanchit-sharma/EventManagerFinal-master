package com.example.android.qwerty;

import android.content.Intent;
import android.provider.ContactsContract;
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

                    mDatabase.removeValue();
                    Toast.makeText(RemoveEventActivity.this,"activity succesfully removed",Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(RemoveEventActivity.this,MainActivity.class));
                }
                else {
                    Toast.makeText(RemoveEventActivity.this, "you did not create this event", Toast.LENGTH_SHORT).show();
                    finish();}



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*String uid = mDatabaseUsers.toString();
        if(uid.equals(user_id))
        {
            mDatabase.removeValue();
            Toast.makeText(RemoveEventActivity.this,"activity succesfully removed",Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        else {
            Toast.makeText(RemoveEventActivity.this, "you did not create this event", Toast.LENGTH_SHORT).show();
            finish();
        }*/


       /*mDatabase.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                   String userId = snapshot.child("userId").getValue().toString();
                   if(user_id.equals(userId)){
                       String key = snapshot.getKey();
                       mDatabase.event_key.removeValue();
                       Toast.makeText(RemoveEventActivity.this,"activity succesfully removed",Toast.LENGTH_LONG).show();
                       finish();
                       startActivity(new Intent(getApplicationContext(),MainActivity.class));
                   }
                   finish();




               }


           }

           @Override
           public void onCancelled(DatabaseError databaseError) {



           }
       });*/




    }
}
