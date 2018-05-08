package com.example.android.qwerty;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.hsalf.smilerating.SmileRating;
import com.squareup.picasso.Picasso;

import jp.wasabeef.blurry.Blurry;

public class DescriptionActivity extends AppCompatActivity {
    ImageView mFrontPoster;
    ImageButton mImageButton;
    TextView mSocietyName,mDescription,toolbar,mRateText;
    String title,society,description,image,registration;
    Button mregis,mRate;
    private Button mRemoveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Toolbar toolbar=findViewById(R.id.toolbar1);
        mRate=(Button)findViewById(R.id.rate_button);
        mRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_rate=new Intent(DescriptionActivity.this,RateActivity.class);
                startActivity(intent_rate);
            }
        });
        setSupportActionBar(toolbar);

        final Intent intent=getIntent();
        if(intent.hasExtra("title")) {
        title=intent.getStringExtra("title");
        }
        if(intent.hasExtra("description")){
            description=intent.getStringExtra("description");

        }
        if(intent.hasExtra("society")){
            society=intent.getStringExtra("society");
        }
        if(intent.hasExtra("image")){
            image=intent.getStringExtra("image");
        }
        if(intent.hasExtra("registration")){
            registration=intent.getStringExtra("registration");
        }
        mFrontPoster=(ImageView)findViewById(R.id.poster_front);
        mImageButton=(ImageButton)findViewById(R.id.image_button);
        mSocietyName=(TextView)findViewById(R.id.society_name);
        mDescription=(TextView)findViewById(R.id.description);
        mregis=(Button)findViewById(R.id.regis);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(DescriptionActivity.this,EventPhoto.class);
                intent2.putExtra("image",image);
                startActivity(intent2);
            }
        });
        toolbar.setTitle(title);
        toolbar.setSubtitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mDescription.setText(description);
        mSocietyName.setText(society);
        Picasso.get().load(image).into(mFrontPoster);
        mregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!registration.startsWith("http://") && !registration.startsWith("https://"))
                    registration = "http://" + registration;
                Uri webpage=Uri.parse(registration);

                Intent intent1=new Intent(Intent.ACTION_VIEW).setData(webpage);
                if(intent1.resolveActivity(getPackageManager())!=null){
                    startActivity(intent1);}
                }

        });




    }
}
