package com.example.android.qwerty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

public class PostActivity extends AppCompatActivity {
    FirebaseStorage storage;
    StorageReference storageReference;
    public static final int IMAGE_GALLERY_REQUEST = 20;
    private static final int PERMISSIONS_REQUEST_READ_STORAGE = 100;
    private Button mFirebaseBtn;
    private EditText mFirebaseName;
    private EditText mFirebaseSocietyName;
    private EditText mFirebaseDesc;
    private EditText mFirebaseRegistrationLink;
    private ImageView imgPicture;
    private Uri imageUri = null;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUsers;


    public void onImageGalleryClicked(View v)
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();

        Uri data = Uri.parse(pictureDirectoryPath);

        photoPickerIntent.setDataAndType(data, "image/*");

        startActivityForResult(photoPickerIntent,IMAGE_GALLERY_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            if(requestCode == IMAGE_GALLERY_REQUEST)
            {

                imageUri = data.getData();

                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(imageUri);

                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    imgPicture.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this,"Unable to open the image",Toast.LENGTH_LONG).show();
                }


            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mFirebaseBtn = findViewById(R.id.postBtn);
        mFirebaseName = findViewById(R.id.name);
        mFirebaseDesc = findViewById(R.id.textDesc);
        mFirebaseRegistrationLink=findViewById(R.id.regis);
        mFirebaseSocietyName = findViewById(R.id.society);
        mProgress = new ProgressDialog(this);
        imgPicture = findViewById(R.id.imgPicture);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("event");
        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("users").child(mCurrentUser.getUid());


        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSIONS_REQUEST_READ_STORAGE);



        mFirebaseBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                final String name= mFirebaseName.getText().toString().trim();
                final String societyName= mFirebaseSocietyName.getText().toString().trim();
                final String description = mFirebaseDesc.getText().toString();
                final String registration=mFirebaseRegistrationLink.getText().toString();
                mProgress.setMessage("Posting...");
                mProgress.show();


                if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(societyName)&&!TextUtils.isEmpty(description)&&imageUri!=null) {


                    StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
                    ref.putFile(imageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                   final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    final DatabaseReference newPost = mDatabase.push();


                                    mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            newPost.child("title").setValue(name);
                                            newPost.child("society").setValue(societyName);
                                            newPost.child("description").setValue(description);
                                            newPost.child("image").setValue(downloadUrl.toString());
                                            newPost.child("registrationLink").setValue(registration);
                                            newPost.child("userId").setValue(mCurrentUser.getUid().toString());
                                            startActivity(new Intent(PostActivity.this, MainActivity.class));
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });


                                    mProgress.dismiss();
                                    Toast.makeText(getApplicationContext(), "Uploaded",
                                            Toast.LENGTH_SHORT).show();
                                    finish();







                                }
                            });
                }
                else
                    Toast.makeText(PostActivity.this, "enter all the fields", Toast.LENGTH_LONG).show();
            }
        });


    }
    }

