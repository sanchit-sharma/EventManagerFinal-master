package com.example.android.qwerty;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.stephentuso.welcome.WelcomeHelper;

import java.io.File;

import jp.wasabeef.blurry.Blurry;

public class MainActivity extends AppCompatActivity {
    WelcomeHelper welcomeScreen;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_add){
            startActivity(new Intent(MainActivity.this,PostActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private RecyclerView mEventList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button mAddEvent;
    private Button mLogOut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcomeScreen=new WelcomeHelper(this,MyWelcomeActivity.class);
        welcomeScreen.forceShow();
        welcomeScreen.show(savedInstanceState);
        mEventList= findViewById(R.id.event_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mEventList.setHasFixedSize(true);
        mEventList.setLayoutManager(mLayoutManager);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("event");
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        mAddEvent = findViewById(R.id.addEvent);
        mLogOut  = findViewById(R.id.logOutBtn);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);


                }
            }
        };
        mAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,DeveloperLoginActivity.class));
            }
        });
        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });

    }
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);


        FirebaseRecyclerAdapter<EventsManager, EventViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<EventsManager, EventViewHolder>(
                EventsManager.class,
                R.layout.card_row,
                EventViewHolder.class,
                mDatabase
        ) {

            @Override
            protected void populateViewHolder(final EventViewHolder viewHolder, EventsManager model, int position) {
                String title=model.getTitle();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setSociety(model.getSociety());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setRegister(getApplicationContext(),model.getRegistrationLink()
                        ,model.getTitle(),model.getDescription(),model.getSociety(),model.getImage());

            }


        };

        mEventList.setAdapter(firebaseRecyclerAdapter);
    }
    public static class EventViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public EventViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }


        public void setTitle(String title) {
            TextView event_title = mView.findViewById(R.id.event_title);
            event_title.setText(title);


        }

        public void setDescription(String desc) {
            TextView event_desc = mView.findViewById(R.id.description);
            event_desc.setText(desc);
        }

        public void setSociety(String society) {
            TextView event_society = mView.findViewById(R.id.society);
            event_society.setText(society);
        }

        public void setImage(Context ctx, String image) {
            ImageView event_image = mView.findViewById(R.id.event_image);
            Picasso.get().load(image).into(event_image);
        }

        public void setRegister(final Context ctx, final String register, final String title, final String desc, final String society, final String image) {
            Button mAboutButton=mView.findViewById(R.id.desc_button);
            mAboutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(view.getContext(),DescriptionActivity.class);
                    intent.putExtra("title",title);
                    intent.putExtra("description",desc);
                    intent.putExtra("society",society);
                    intent.putExtra("image",image);
                    intent.putExtra("registration",register);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }






    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        welcomeScreen.onSaveInstanceState(outState);

    }


}


