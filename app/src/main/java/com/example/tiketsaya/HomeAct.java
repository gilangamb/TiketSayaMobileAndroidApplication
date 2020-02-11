package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeAct extends AppCompatActivity {

    LinearLayout btn_pisa, btn_monas, btn_pagoda,
            btn_tample, btn_sphinx, btn_torri;
    CircleView btn_my_profile;
    ImageView photo_home_user;
    TextView nama_lengkap, bio, user_balance;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        btn_pisa = findViewById(R.id.btn_pisa);
        btn_monas = findViewById(R.id.btn_monas);
        btn_pagoda = findViewById(R.id.btn_pagoda);
        btn_sphinx = findViewById(R.id.btn_sphinx);
        btn_torri = findViewById(R.id.btn_torri);
        btn_tample = findViewById(R.id.btn_tample);

        btn_my_profile = findViewById(R.id.btn_my_profile);

        photo_home_user = findViewById(R.id.photo_home_user);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);
        user_balance = findViewById(R.id.user_balance);

        reference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(username_key_new);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                user_balance.setText("US$" + dataSnapshot.child("user_balance").getValue().toString());
                Picasso.with(HomeAct.this)
                        .load(dataSnapshot.child("url_photo_profile")
                                .getValue().toString()).centerCrop().fit().into(photo_home_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomyprofile = new Intent(HomeAct.this,MyProfileAct.class);
                startActivity(gotomyprofile);
            }
        });

        btn_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodetail = new Intent(HomeAct.this,TicketDetailAct.class);
                gotodetail.putExtra("jenis_tiket","Pisa");
                startActivity(gotodetail);
            }
        });

        btn_torri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodetail = new Intent(HomeAct.this,TicketDetailAct.class);
                gotodetail.putExtra("jenis_tiket","Torri");
                startActivity(gotodetail);
            }
        });

        btn_pagoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodetail = new Intent(HomeAct.this,TicketDetailAct.class);
                gotodetail.putExtra("jenis_tiket","Pagoda");
                startActivity(gotodetail);
            }
        });

        btn_tample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodetail = new Intent(HomeAct.this,TicketDetailAct.class);
                gotodetail.putExtra("jenis_tiket","Tample");
                startActivity(gotodetail);
            }
        });

        btn_sphinx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodetail = new Intent(HomeAct.this,TicketDetailAct.class);
                gotodetail.putExtra("jenis_tiket","Sphinx");
                startActivity(gotodetail);
            }
        });

        btn_monas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodetail = new Intent(HomeAct.this,TicketDetailAct.class);
                gotodetail.putExtra("jenis_tiket","Monas");
                startActivity(gotodetail);
            }
        });

        btn_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodetail = new Intent(HomeAct.this,TicketDetailAct.class);
                gotodetail.putExtra("jenis_tiket","Pisa");
                startActivity(gotodetail);
            }
        });
    }

    public  void  getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
