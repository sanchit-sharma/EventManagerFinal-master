package com.example.android.qwerty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hsalf.smilerating.SmileRating;

public class RateActivity extends AppCompatActivity {
    private DatabaseReference mDatabseRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        mDatabseRating = FirebaseDatabase.getInstance().getReference().child("event");
        final SmileRating mrating=(SmileRating)findViewById(R.id.rate);
        mrating.setNameForSmile(SmileRating.TERRIBLE,"");
        mrating.setNameForSmile(SmileRating.BAD,"");
        mrating.setNameForSmile(SmileRating.OKAY,"");
        mrating.setNameForSmile(3,"");
        mrating.setNameForSmile(4,"");
        mrating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                switch(smiley){
                    case SmileRating.BAD:
                        mrating.setNameForSmile(SmileRating.TERRIBLE,"");
                        mrating.setNameForSmile(SmileRating.BAD,"Not Interested");
                        mrating.setNameForSmile(SmileRating.OKAY,"");
                        mrating.setNameForSmile(3,"");
                        mrating.setNameForSmile(4,"");
                        break;
                    case SmileRating.GOOD:
                        mrating.setNameForSmile(SmileRating.TERRIBLE,"");
                        mrating.setNameForSmile(SmileRating.BAD,"");
                        mrating.setNameForSmile(SmileRating.OKAY,"");
                        mrating.setNameForSmile(3,"Interested");
                        mrating.setNameForSmile(4,"");
                        break;
                    case SmileRating.GREAT:
                        mrating.setNameForSmile(SmileRating.TERRIBLE,"");
                        mrating.setNameForSmile(SmileRating.BAD,"");
                        mrating.setNameForSmile(SmileRating.OKAY,"");
                        mrating.setNameForSmile(3,"");
                        mrating.setNameForSmile(4,"Absolutely Yes");
                        break;
                    case SmileRating.OKAY:
                        mrating.setNameForSmile(SmileRating.TERRIBLE,"");
                        mrating.setNameForSmile(SmileRating.BAD,"");
                        mrating.setNameForSmile(SmileRating.OKAY,"Somewhat Interested");
                        mrating.setNameForSmile(3,"");
                        mrating.setNameForSmile(4,"");
                        break;
                    case SmileRating.TERRIBLE:
                        mrating.setNameForSmile(SmileRating.TERRIBLE,"Absolutely Not");
                        mrating.setNameForSmile(SmileRating.BAD,"");
                        mrating.setNameForSmile(SmileRating.OKAY,"");
                        mrating.setNameForSmile(3,"");
                        mrating.setNameForSmile(4,"");
                        break;
                    case SmileRating.NONE:
                        mrating.setNameForSmile(SmileRating.TERRIBLE,"");
                        mrating.setNameForSmile(SmileRating.BAD,"");
                        mrating.setNameForSmile(SmileRating.OKAY,"");
                        mrating.setNameForSmile(3,"");
                        mrating.setNameForSmile(4,"");
                        break;


                }
            }
        });
       mrating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
           @Override
           public void onRatingSelected(int level, boolean reselected) {

           }
       });


    }
}
