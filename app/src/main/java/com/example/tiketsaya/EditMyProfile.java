package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditMyProfile extends AppCompatActivity {

    LinearLayout btn_back;
    EditText xemail_address, xbio, xpassword, xnama_lengkap, xusername;
    ImageView photo_profile;
    Button btn_save_profile, btn_plus;

    DatabaseReference reference;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    Uri photo_location;
    Integer photo_max = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile);

        getUsernameLocal();

        btn_back = findViewById(R.id.btn_back);
        btn_save_profile = findViewById(R.id.btn_save_profile);
        btn_plus = findViewById(R.id.btn_plus);
        photo_profile = findViewById(R.id.photo_profile);
        xemail_address = findViewById(R.id.xemail_address);
        xbio = findViewById(R.id.xbio);
        xpassword = findViewById(R.id.xpassword);
        xnama_lengkap = findViewById(R.id.xnama_lengkap);
        xusername = findViewById(R.id.xusername);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        storage = FirebaseStorage.getInstance().getReference().child("Photousers").child(username_key_new);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xnama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                xbio.setText(dataSnapshot.child("bio").getValue().toString());
                xusername.setText(dataSnapshot.child("username").getValue().toString());
                xpassword.setText(dataSnapshot.child("password").getValue().toString());
                xemail_address.setText(dataSnapshot.child("email_address").getValue().toString());
                Picasso.with(EditMyProfile.this).load(dataSnapshot.child("url_photo_profile")
                        .getValue().toString()).centerCrop().fit().into(photo_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ubah state menjadi loading
                btn_save_profile.setEnabled(false);
                btn_save_profile.setText("Loading ...");

                if (xnama_lengkap.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Lengkapi data profil anda!",Toast.LENGTH_SHORT).show();
                    btn_save_profile.setEnabled(true);
                    btn_save_profile.setText("Save Profile");
                }
                else if (xpassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Lengkapi data profil anda!",Toast.LENGTH_SHORT).show();
                    btn_save_profile.setEnabled(true);
                    btn_save_profile.setText("Save Profile");
                }
                else if (xusername.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Lengkapi data profil anda!",Toast.LENGTH_SHORT).show();
                    btn_save_profile.setEnabled(true);
                    btn_save_profile.setText("Save Profile");
                }
                else if (xbio.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Lengkapi data profil anda!",Toast.LENGTH_SHORT).show();
                    btn_save_profile.setEnabled(true);
                    btn_save_profile.setText("Save Profile");

                }else if (xemail_address.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Lengkapi data profil anda!",Toast.LENGTH_SHORT).show();
                    btn_save_profile.setEnabled(true);
                    btn_save_profile.setText("Save Profile");
                }

                else {
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            dataSnapshot.getRef().child("nama_lengkap").setValue(xnama_lengkap.getText().toString());
                            dataSnapshot.getRef().child("password").setValue(xpassword.getText().toString());
                            dataSnapshot.getRef().child("username").setValue(xusername.getText().toString());
                            dataSnapshot.getRef().child("bio").setValue(xbio.getText().toString());
                            dataSnapshot.getRef().child("email_address").setValue(xemail_address.getText().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    // validasi untuk file (apakah ada?)
                    if (photo_location != null){
                        final StorageReference storageReference1 = storage.child(System.currentTimeMillis() + "." +
                                getFileExtension(photo_location));
                        storageReference1.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String uri_photo = uri.toString();
                                        if (uri_photo.isEmpty()){
                                            Toast.makeText(getApplicationContext(),"Pilih foto profil anda", Toast.LENGTH_SHORT).show();
                                            //after clicked disable
                                            btn_save_profile.setEnabled(true);
                                            btn_save_profile.setText("Save Profile");
                                        }
                                        else {
                                            reference.getRef().child("url_photo_profile").setValue(uri_photo);
                                        }
                                    }
                                });
                            }
                        });
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Pilih foto profil anda", Toast.LENGTH_SHORT).show();
                        //after clicked disable
                        btn_save_profile.setEnabled(true);
                        btn_save_profile.setText("Save Profile");
                    }

                    Intent gotosuccess = new Intent(EditMyProfile.this, MyProfileAct.class);
                    startActivity(gotosuccess);
                    finish();
                }
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public  void  getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }

    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto() {
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(photo_profile);
        }
    }
}
