package com.example.android.qwerty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class EventPhoto extends AppCompatActivity {
    String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_photo);
        ImageView mEventPhoto=(ImageView)findViewById(R.id.eventphoto);
        final Intent intent=getIntent();
        if(intent.hasExtra("image")){
            image=intent.getStringExtra("image");
        }
        Picasso.get().load(image).into(mEventPhoto);

    }
}
