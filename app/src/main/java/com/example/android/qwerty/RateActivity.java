package com.example.android.qwerty;

import android.content.Intent;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.SmileRating;

public class RateActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseRating;
    private DatabaseReference mUserRating;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private Button mSubmitButton;
    private DatabaseReference mDatabase;
    private TextView mTextview;
    boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubmitButton = findViewById(R.id.submitbutton);

        final String event_key = getIntent().getExtras().getString("eventId");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        final String uid = mCurrentUser.getUid().toString();
        setContentView(R.layout.activity_rate);
        //mDatabase = FirebaseDatabase.getInstance().getReference().child("event");
        mDatabaseRating = FirebaseDatabase.getInstance().getReference().child("event").child(event_key).child("rating");
        mUserRating = FirebaseDatabase.getInstance().getReference().child("ratingBar").child(uid);
        final SmileRating mrating = (SmileRating) findViewById(R.id.rate);
        mrating.setNameForSmile(SmileRating.TERRIBLE, "");
        mrating.setNameForSmile(SmileRating.BAD, "");
        mrating.setNameForSmile(SmileRating.OKAY, "");
        mrating.setNameForSmile(3, "");
        mrating.setNameForSmile(4, "");
        mrating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                switch (smiley) {
                    case SmileRating.BAD:
                        mrating.setNameForSmile(SmileRating.TERRIBLE, "");
                        mrating.setNameForSmile(SmileRating.BAD, "Not Interested");
                        mrating.setNameForSmile(SmileRating.OKAY, "");
                        mrating.setNameForSmile(3, "");
                        mrating.setNameForSmile(4, "");

                        break;
                    case SmileRating.GOOD:
                        mrating.setNameForSmile(SmileRating.TERRIBLE, "");
                        mrating.setNameForSmile(SmileRating.BAD, "");
                        mrating.setNameForSmile(SmileRating.OKAY, "");
                        mrating.setNameForSmile(3, "Interested");
                        mrating.setNameForSmile(4, "");
                        break;
                    case SmileRating.GREAT:
                        mrating.setNameForSmile(SmileRating.TERRIBLE, "");
                        mrating.setNameForSmile(SmileRating.BAD, "");
                        mrating.setNameForSmile(SmileRating.OKAY, "");
                        mrating.setNameForSmile(3, "");
                        mrating.setNameForSmile(4, "Absolutely Yes");
                        break;
                    case SmileRating.OKAY:
                        mrating.setNameForSmile(SmileRating.TERRIBLE, "");
                        mrating.setNameForSmile(SmileRating.BAD, "");
                        mrating.setNameForSmile(SmileRating.OKAY, "Somewhat Interested");
                        mrating.setNameForSmile(3, "");
                        mrating.setNameForSmile(4, "");
                        break;
                    case SmileRating.TERRIBLE:
                        mrating.setNameForSmile(SmileRating.TERRIBLE, "Absolutely Not");
                        mrating.setNameForSmile(SmileRating.BAD, "");
                        mrating.setNameForSmile(SmileRating.OKAY, "");
                        mrating.setNameForSmile(3, "");
                        mrating.setNameForSmile(4, "");
                        break;
                    case SmileRating.NONE:
                        mrating.setNameForSmile(SmileRating.TERRIBLE, "");
                        mrating.setNameForSmile(SmileRating.BAD, "");
                        mrating.setNameForSmile(SmileRating.OKAY, "");
                        mrating.setNameForSmile(3, "");
                        mrating.setNameForSmile(4, "");
                        break;


                }
            }
        });





        /*mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserRating.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()){
                            //coding for first entry
                            //mSubmitButton.setOnClickListener(new View.OnClickListener() {
                            //@Override
                            // public void onClick(View view) {
                            mUserRating.child(event_key).setValue(newRating);
                            mDatabaseRating.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int originalRating  = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                                    originalRating = originalRating+newRating;
                                    mDatabaseRating.setValue(String.valueOf(originalRating));
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                    //});

                                }
                            });

                        }
                        else{

                            for(DataSnapshot childsnapshot:dataSnapshot.getChildren()){
                                if(childsnapshot.toString().equals(event_key)){
                                    final int rating = Integer.parseInt(String .valueOf(childsnapshot.getValue()));
                                    mDatabaseRating.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            int originalRating = Integer.parseInt(String .valueOf(dataSnapshot.getValue()));
                                            originalRating = originalRating-rating;
                                            mDatabaseRating.setValue(String.valueOf(originalRating));
                                            final int finalOriginalRating = originalRating;
                                            mUserRating.child(event_key).setValue(newRating);
                                            mDatabaseRating.setValue(String.valueOf(finalOriginalRating +rating));
                                            /*mSubmitButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    mUserRating.child(event_key).setValue(newRating);
                                                    mDatabaseRating.setValue(String.valueOf(finalOriginalRating +rating));
                                                }
                                            });*/

        // }

        //@Override
        // public void onCancelled(DatabaseError databaseError) {

        //  }
        //  });


        //  }
        //  else{
        //   mUserRating.child(event_key).setValue(newRating);
        //  mDatabaseRating.addValueEventListener(new ValueEventListener() {
        //     @Override
        //    public void onDataChange(DataSnapshot dataSnapshot) {
        // int originalRating  = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
        //     originalRating = originalRating+newRating;
        //  mDatabaseRating.setValue(String.valueOf(originalRating));
        //}

        //@Override
        //public void onCancelled(DatabaseError databaseError) {

        // }
        // });

                                    /*mSubmitButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            mUserRating.child(event_key).setValue(newRating);
                                            mDatabaseRating.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    int originalRating  = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                                                    originalRating = originalRating+newRating;
                                                    mDatabaseRating.setValue(String.valueOf(originalRating));
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                        }
                                    });*///}
        //startActivity(new Intent(RateActivity.this,DescriptionActivity.class));
        //Toast.makeText(RateActivity.this,"rated",Toast.LENGTH_SHORT).show();

        // }}
        // }

        // @Override
        // public void onCancelled(DatabaseError databaseError) {

        // }
        //});

        // }
        // });*/

        /*mUserRating.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    //coding for first entry
                    //mSubmitButton.setOnClickListener(new View.OnClickListener() {
                        //@Override
                       // public void onClick(View view) {
                            mUserRating.child(event_key).setValue(newRating);
                            mDatabaseRating.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int originalRating  = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                                    originalRating = originalRating+newRating;
                                    mDatabaseRating.setValue(String.valueOf(originalRating));
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                //});

                        }
                    });

                }
                else{

                for(DataSnapshot childsnapshot:dataSnapshot.getChildren()){
                    if(childsnapshot.toString().equals(event_key)){
                        final int rating = Integer.parseInt(String .valueOf(childsnapshot.getValue()));
                        mDatabaseRating.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int originalRating = Integer.parseInt(String .valueOf(dataSnapshot.getValue()));
                                 originalRating = originalRating-rating;
                                mDatabaseRating.setValue(String.valueOf(originalRating));
                                final int finalOriginalRating = originalRating;
                                mSubmitButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mUserRating.child(event_key).setValue(newRating);
                                        mDatabaseRating.setValue(String.valueOf(finalOriginalRating +rating));
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }
                    else{

                       mSubmitButton.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               mUserRating.child(event_key).setValue(newRating);
                               mDatabaseRating.addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                       int originalRating  = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                                       originalRating = originalRating+newRating;
                                       mDatabaseRating.setValue(String.valueOf(originalRating));
                                   }

                                   @Override
                                   public void onCancelled(DatabaseError databaseError) {

                                   }
                               });

                           }
                       });}
                       startActivity(new Intent(RateActivity.this,DescriptionActivity.class));

                }}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


    }




}
